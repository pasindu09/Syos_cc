package main.product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.database.DatabaseConnection;
import main.product.Product;
import main.product.ProductRepositoryOperations;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository implements ProductRepositoryOperations {
    private DatabaseConnection dbConnection;

    public ProductRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT id, product_name, product_price FROM product";
        List<Product> products = new ArrayList<>();

        try (Connection connection = dbConnection.connectDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");

                Product product = new Product(id, name, price);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product getProductById(int productId) {
        String query = "SELECT id, product_name, product_price FROM product WHERE id = ?";
        Product product = null;
        try (Connection connection = dbConnection.connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String productName = resultSet.getString("product_name");
                    double productPrice = resultSet.getDouble("product_price");
                    product = new Product(id, productName, productPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}
