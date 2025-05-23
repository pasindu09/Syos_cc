package main.pos.cart;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.product.Product;

public class CartDisplay implements CartDisplayService{
    private List<Product> products;

    public CartDisplay(Cart cart) {
        products = cart.getProducts();
    }

    @Override
     public void displayCart() {
        Map<Integer, Integer> itemIdAndQuantity = new HashMap<>();
        for (Product product : products) {
            itemIdAndQuantity.put(product.getId(), itemIdAndQuantity.getOrDefault(product.getId(), 0) + 1);
        }
    
        for (Map.Entry<Integer, Integer> entry : itemIdAndQuantity.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            Product product = products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
            if (product != null) {
                System.out.println(product.getName() + " x " + quantity + " - $" + product.getPrice() * quantity);
            }
        }
    }

    

    
}
