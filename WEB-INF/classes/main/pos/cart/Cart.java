package main.pos.cart;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import main.product.Product;

public class Cart {
    private List<Product> products;

    public Cart() {
        // Wrap the ArrayList in a synchronized list
        products = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Product> getProducts() {
        return products;
    }
}
