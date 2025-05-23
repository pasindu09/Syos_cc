package main.pos.cart;

import main.product.Product;


public interface CartService {
    void addProduct(Product product, int quantity);
    double calculateSubTotal();
    double calculateTotal();
}