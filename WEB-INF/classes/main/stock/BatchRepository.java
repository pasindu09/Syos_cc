package main.stock;

import java.util.ArrayList;
import java.util.List;

import main.database.DatabaseConnection;
import main.stock.StockBatch;

import java.sql.*;

public class BatchRepository implements BatchRepositoryOperations, OtherBatchRepositoryOperations{
    private DatabaseConnection dbConnection;

    public BatchRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;

    }

    @Override
    public List<StockBatch> getBatchesByProductId(int productId) {
        String query = "SELECT batch_id, product_id, date_of_purchase, quantity_received, expiry_date, quantity_in_stock "
                +
                "FROM stock_batches WHERE product_id = ? ORDER BY expiry_date ASC, date_of_purchase ASC";

        List<StockBatch> stockBatchList = new ArrayList<>();

        try (Connection connection = dbConnection.connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int batchId = resultSet.getInt("batch_id");
                    Date dateOfPurchase = resultSet.getDate("date_of_purchase");
                    int quantityReceived = resultSet.getInt("quantity_received");
                    Date expiryDate = resultSet.getDate("expiry_date");
                    int quantityInStock = resultSet.getInt("quantity_in_stock");

                    StockBatch stockBatch = new StockBatch(batchId, productId, dateOfPurchase, quantityReceived,
                            expiryDate, quantityInStock);
                    stockBatchList.add(stockBatch);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockBatchList;
    }

    @Override
    public void updateBatch(StockBatch batch) {
        String query = "UPDATE stock_batches SET quantity_in_stock = ? WHERE batch_id = ?";

        try (Connection connection = dbConnection.connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, batch.getQuantityInStock());
            preparedStatement.setInt(2, batch.getBatchId());

            preparedStatement.executeUpdate();
            System.out.println("Batch ID "+batch.getBatchId()+" Reduced to "+batch.getQuantityInStock());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   @Override
    public List<StockBatch> getAllBatches() {
        String query = "SELECT batch_id, product_id, date_of_purchase, quantity_received, expiry_date, quantity_in_stock FROM stock_batches";
        List<StockBatch> stockBatchList = new ArrayList<>();
    
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                int batchId = resultSet.getInt("batch_id");
                int productId = resultSet.getInt("product_id");
                Date dateOfPurchase = resultSet.getDate("date_of_purchase");
                int quantityReceived = resultSet.getInt("quantity_received");
                Date expiryDate = resultSet.getDate("expiry_date");
                int quantityInStock = resultSet.getInt("quantity_in_stock");
    
                StockBatch stockBatch = new StockBatch(batchId, productId, dateOfPurchase, quantityReceived, expiryDate, quantityInStock);
                stockBatchList.add(stockBatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockBatchList;
    }

    @Override
    public List<StockBatch> getBatchesBelowReorderLevel() {
        String query = "SELECT batch_id, product_id, date_of_purchase, quantity_received, expiry_date, quantity_in_stock FROM stock_batches WHERE quantity_in_stock < 50";
        List<StockBatch> stockBatchList = new ArrayList<>();
    
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                int batchId = resultSet.getInt("batch_id");
                int productId = resultSet.getInt("product_id");
                Date dateOfPurchase = resultSet.getDate("date_of_purchase");
                int quantityReceived = resultSet.getInt("quantity_received");
                Date expiryDate = resultSet.getDate("expiry_date");
                int quantityInStock = resultSet.getInt("quantity_in_stock");
    
                StockBatch stockBatch = new StockBatch(batchId, productId, dateOfPurchase, quantityReceived, expiryDate, quantityInStock);
                stockBatchList.add(stockBatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockBatchList;
    }
    

}
