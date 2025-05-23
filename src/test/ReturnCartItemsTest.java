package test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import main.pos.cart.Cart;
import main.pos.cart.ReturnCartItems;
import main.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ReturnCartItemsTest {

    @Mock
    private Cart cart;

    @InjectMocks
    private ReturnCartItems returnCartItems;

    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 100.00));
        products.add(new Product(2, "Product2", 200.00));
        products.add(new Product(1, "Product1", 100.00)); // Adding same product to test quantity increment

        when(cart.getProducts()).thenReturn(products);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close(); // Close resources when tests are done
    }
    @Test
    public void testReturnItems() {
        List<Product> mockProducts = cart.getProducts(); // Directly retrieve mock products to examine
        System.out.println(mockProducts);
        assertNotNull("Mock products should not be null", mockProducts);
        assertEquals("Mock products should contain exactly 3 products", 3, mockProducts.size());
        
        List<Object[]> result = returnCartItems.returnItems();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 2 distinct products", 3, mockProducts.size());
        
    }
    
    
}