package main.pos.cart;

import main.pos.cart.Cart;

public interface DiscountCalculator  {
    double calculateDiscount(Cart cart);
}
