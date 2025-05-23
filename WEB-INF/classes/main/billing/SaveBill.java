package main.billing;

import main.database.DatabaseConnection;
import main.stock.BatchRepository;
import main.stock.BatchRepositoryOperations;
import main.stock.MoveToShelfAndReduceStock;
import java.util.List;

public class SaveBill {
   private DatabaseConnection connectionInstance;
   private List<Object[]> cartContents;
   
   public SaveBill(DatabaseConnection connectionInstance, List<Object[]> cartContents) {
      this.connectionInstance = connectionInstance;
      this.cartContents = cartContents;
   }
 

   public void saveBillReciept() {
      BatchRepositoryOperations batchOperations = new BatchRepository(connectionInstance);
      MoveToShelfAndReduceStock ReduceStock = new MoveToShelfAndReduceStock(cartContents, batchOperations, connectionInstance);
      ReduceStock.moveToShelfAndReduceStock();
   }

}
