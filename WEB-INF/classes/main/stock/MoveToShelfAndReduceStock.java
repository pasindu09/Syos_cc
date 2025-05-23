package main.stock;
import java.util.List;

import main.database.DatabaseConnection;
import main.stock.StockBatch;


public class MoveToShelfAndReduceStock {
    List<Object[]> billedItems;
    private BatchRepositoryOperations batchRepositoryOperations;
    private DatabaseConnection dbConnection;
    public MoveToShelfAndReduceStock(List<Object[]> billedItems, BatchRepositoryOperations batchRepositoryOperations, DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.batchRepositoryOperations = batchRepositoryOperations;
        this.billedItems = billedItems;
    }

    public void moveToShelfAndReduceStock() {
        for (Object[] item : billedItems) {
            int productId = (int) item[0];
            int productQuantity = (int) item[2];

            List<StockBatch> batches = batchRepositoryOperations.getBatchesByProductId(productId);
            for (StockBatch batch : batches) {
                if (productQuantity <= 0) break;
                int batchQuantity = batch.getQuantityInStock();
                if (batchQuantity >= productQuantity) {
                    batch.setQuantityInStock(batchQuantity - productQuantity);
                    batchRepositoryOperations.updateBatch(batch);
                    productQuantity = 0;
                } else {
                    batch.setQuantityInStock(0);
                    batchRepositoryOperations.updateBatch(batch);
                    productQuantity -= batchQuantity;
                }
            }

            if (productQuantity > 0) {
                throw new IllegalStateException("Insufficient stock for product: " + productId);
            }
    }
    }

   
    
}
