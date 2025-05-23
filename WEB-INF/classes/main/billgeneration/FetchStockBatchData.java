package main.billgeneration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.database.DatabaseConnection;


public class FetchStockBatchData implements FetchStockBatchDataOperations {
    private DatabaseConnection dbConnection;
    List<StockBatch> StockBatch = new ArrayList<>();

    public FetchStockBatchData(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
        public List<StockBatch> getAllStockBatches() {
        String query = "SELECT batch_id, product_id, date_of_purchase, quantity_received, expiry_date, quantity_in_stock  FROM stock_batches"; // Replace 'bills' with your actual table name
        
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int batchId = resultSet.getInt("batch_id");
                int productId = resultSet.getInt("product_id");
                Date dateOfPurchase = resultSet.getDate("date_of_purchase");
                int quantityReceived = resultSet.getInt("quantity_received");
                Date expiryDate = resultSet.getDate("expiry_date");
                int quantityInStock = resultSet.getInt("quantity_in_stock");
                StockBatch stockBatch = new StockBatch(batchId, productId, dateOfPurchase, quantityReceived,expiryDate, quantityInStock);
                StockBatch.add(stockBatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StockBatch;
    }
}