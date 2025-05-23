package test;


import main.pos.cart.Cart;
import main.pos.cart.PercentageDiscountCalculator;
import main.product.Product;

import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PercentageDiscountCalculatorTest {

    @Mock
    private Cart cart;

    private PercentageDiscountCalculator calculator = new PercentageDiscountCalculator(0.1);  // 10% discount

    @Test
    public void testCalculateDiscount() {
        Product product1 = new Product(1,"Apple", 1.0);
        Product product2 = new Product(2,"Banana", 1.5);
        when(cart.getProducts()).thenReturn(Arrays.asList(product1, product2));

        double discount = calculator.calculateDiscount(cart);

        assertEquals(0.25, discount, 0.001);  // 0.25 is 10% of 2.5 (total of product prices)
    }

    @Test
    public void testCalculateDiscountWithEmptyCart() {
        // Mock the behavior of cart to return an empty list
        when(cart.getProducts()).thenReturn(Arrays.asList());

        // Calculate discount
        double discount = calculator.calculateDiscount(cart);

        // Verify that the discount is 0 for an empty cart
        assertEquals(0.0, discount, 0.001);
    }
}
