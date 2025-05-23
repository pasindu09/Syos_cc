package main.stock;

import java.util.List;
import main.stock.StockBatch;

public interface OtherBatchRepositoryOperations {
    List<StockBatch> getBatchesBelowReorderLevel();

    List<StockBatch> getAllBatches();
}
