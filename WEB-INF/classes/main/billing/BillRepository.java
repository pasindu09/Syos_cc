package main.billing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

import main.database.DatabaseConnection;
import main.billing.BillRepositoryOperations;
import main.billing.Bill;
import main.billing.BillSerialNumber;

public class BillRepository implements BillRepositoryOperations<Bill> {
    private DatabaseConnection dbConnection;
    int nextSerialNumber;

    public BillRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void createBill(Bill bill) {
        JSONObject json = new JSONObject();
        json.put("cartContents", bill.getCartContents());
        json.put("totalPaid", bill.getTotalPaid());
        json.put("change", bill.getChange());
        json.put("subTotal", bill.getSubTotal());
        json.put("total", bill.getTotal());
        json.put("discount", bill.getDiscount());
        String getMaxSerialNumberQuery = "SELECT MAX(serialnumber) AS max_serial FROM bill";
        String insertQuery = "INSERT INTO bill (serialnumber, bill_information, created_at) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection connection = dbConnection.connectDatabase();
                PreparedStatement getMaxSerialStmt = connection.prepareStatement(getMaxSerialNumberQuery);
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            ResultSet rs = getMaxSerialStmt.executeQuery();
            int nextSerialNumber = 1;
            if (rs.next()) {
                nextSerialNumber = rs.getInt("max_serial") + 1;
            }

            insertStmt.setInt(1, nextSerialNumber);
            insertStmt.setString(2, json.toString());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record inserted successfully with serial number " + nextSerialNumber);
            } else {
                System.out.println("Insertion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int returnBillSerialNumber() {

        String getMaxSerialNumberQuery = "SELECT MAX(serialnumber) AS max_serial FROM bill";
        try (Connection connection = dbConnection.connectDatabase();
                PreparedStatement getMaxSerialStmt = connection.prepareStatement(getMaxSerialNumberQuery);) {

            ResultSet rs = getMaxSerialStmt.executeQuery();
            nextSerialNumber = 1;
            if (rs.next()) {
                nextSerialNumber = rs.getInt("max_serial") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextSerialNumber;

    }

}
