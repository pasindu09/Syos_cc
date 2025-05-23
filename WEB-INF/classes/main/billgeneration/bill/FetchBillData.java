package main.billgeneration.bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.database.DatabaseConnection;


public class FetchBillData implements FetchBillDataOperations {
    private DatabaseConnection dbConnection;
    List<FetchedBill> fetchedBill = new ArrayList<>();

    public FetchBillData(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
        public List<FetchedBill> getAllBills() {
        String query = "SELECT id, serialnumber, bill_information, created_at FROM bill"; // Replace 'bills' with your actual table name
        
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String serialNumber = resultSet.getString("serialnumber");
                String billInformation = resultSet.getString("bill_information");
                String createdAt = resultSet.getString("created_at");
                FetchedBill bill = new FetchedBill(id, serialNumber, billInformation, createdAt);
                fetchedBill.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchedBill;
    }

    @Override
    public List<FetchedBill> getAllBillsByDate(String date) {
        String query = "SELECT id, serialnumber, bill_information, created_at FROM bill WHERE DATE(created_at) = ?";
    
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, date);  // Set the date parameter
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String serialNumber = resultSet.getString("serialnumber");
                String billInformation = resultSet.getString("bill_information");
                String createdAt = resultSet.getString("created_at");
                FetchedBill bill = new FetchedBill(id, serialNumber, billInformation, createdAt);
                fetchedBill.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchedBill;
    }
    
}