package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import main.pos.cart.Cart;
import main.pos.cart.CartOperations;
import main.pos.cart.DiscountCalculator;
import main.product.Product;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CartOperationsTest {

    @Mock
    private Cart cart;
    @Mock
    private DiscountCalculator discountCalculator;
    @Mock
    private List<Product> mockProductList;

    private CartOperations cartOperations;

    @Before
    public void setUp() {
        // Ensure the cart returns a mocked product list
        when(cart.getProducts()).thenReturn(mockProductList);
        // Initialize CartOperations
        cartOperations = new CartOperations(cart, discountCalculator);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1,"TestProduct", 10.0);
        int quantity = 3;

        cartOperations.addProduct(product, quantity);

        verify(mockProductList, times(quantity)).add(product);
    }

    @Test
    public void testCalculateSubTotal() {
        Product product1 = new Product(1,"Apple", 1.0);
        Product product2 = new Product(2,"Banana", 6.0);
        List<Product> products = Arrays.asList(product1, product2);
        when(mockProductList.iterator()).thenReturn(products.iterator());

        double subtotal = cartOperations.calculateSubTotal();

        // Verify subtotal is calculated correctly
        assertEquals(7.0, subtotal, 0.001);
    }

    @Test
    public void testCalculateTotal() {
        // Assume the subtotal and a fixed discount are calculated
        when(mockProductList.iterator()).thenReturn(Arrays.asList(new Product(1,"Apple", 1.0), new Product(2,"Banana", 2.0)).iterator());
        when(discountCalculator.calculateDiscount(cart)).thenReturn(0.5);

        double total = cartOperations.calculateTotal();

        // Verify total is subtotal minus discount
        assertEquals(2.5, total, 0.001);
    }

}


