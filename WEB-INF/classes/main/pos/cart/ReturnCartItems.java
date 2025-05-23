package main.pos.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.product.Product;
import main.pos.cart.Cart;


public class ReturnCartItems implements ReturnCartItemsService{
    private List<Product> products;

    public ReturnCartItems(Cart cart) {
        this.products = cart.getProducts();
    }

    @Override
    public List<Object[]> returnItems() {
        Map<Integer, Integer> itemIdAndQuantity = new HashMap<>();
        for (Product product : products) {
            itemIdAndQuantity.put(product.getId(), itemIdAndQuantity.getOrDefault(product.getId(), 0) + 1);
        }
    
        List<Object[]> productDetails = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : itemIdAndQuantity.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            Product product = products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
            if (product != null) {
                Object[] details = new Object[4];
                details[0] = product.getId();
                details[1] = product.getName();
                details[2] = quantity;
                details[3] = product.getPrice() * quantity;
                productDetails.add(details);
            }
        }
        
        return productDetails;
    }

}
