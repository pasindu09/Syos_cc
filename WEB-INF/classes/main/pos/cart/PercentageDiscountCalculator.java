package main.pos.cart;
import java.util.List;

import main.product.Product;
import main.pos.cart.Cart;
import main.pos.cart.DiscountCalculator;

public class PercentageDiscountCalculator implements DiscountCalculator {
    private final double discountRate; // Make discountRate final to prevent changes

    public PercentageDiscountCalculator(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        List<Product> products = cart.getProducts();
        double subtotal = 0.0;
        synchronized (products) { // Synchronize access to products list
            for (Product product : products) {
                subtotal += product.getPrice();
            }
        }
        return subtotal * discountRate;
    }
}

