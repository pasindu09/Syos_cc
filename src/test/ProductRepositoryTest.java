package test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import main.database.DatabaseConnection;
import main.product.Product;
import main.product.ProductRepository;

import java.sql.*;
import java.util.List;
import java.sql.SQLException;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class ProductRepositoryTest {

    @Mock
    private DatabaseConnection dbConnection;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProductRepository productRepository;

    @Before
    public void setUp() throws SQLException {
        when(dbConnection.connectDatabase()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        when(statement.executeQuery("SELECT id, product_name, product_price FROM product")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false); // Simulate two products
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("product_name")).thenReturn("Product 1", "Product 2");
        when(resultSet.getDouble("product_price")).thenReturn(10.0, 20.0);

        List<Product> products = productRepository.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());
        assertEquals(10.0, products.get(0).getPrice(), 0.001);
        assertEquals(20.0, products.get(1).getPrice(), 0.001);
    }

    @Test
    public void testGetProductById() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("product_name")).thenReturn("Product 1");
        when(resultSet.getDouble("product_price")).thenReturn(10.0);

        Product product = productRepository.getProductById(1);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Product 1", product.getName());
        assertEquals(10.0, product.getPrice(), 0.001);
    }
}

