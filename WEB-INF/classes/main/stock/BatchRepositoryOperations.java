package main.stock;

import java.util.List;
import main.stock.StockBatch;

public interface BatchRepositoryOperations {
    List<StockBatch> getBatchesByProductId(int productId);
    void updateBatch(StockBatch batch);
}
