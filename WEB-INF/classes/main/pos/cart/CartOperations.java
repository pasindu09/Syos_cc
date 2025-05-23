package main.pos.cart;
import java.util.List;
import main.product.Product;
import main.pos.cart.Cart;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;

public class CartOperations implements CartService {
    private List<Product> products;
    private DiscountCalculator discountCalculator;
    private Cart cart;

    public CartOperations(Cart cart, DiscountCalculator discountCalculator) {
        this.cart = cart;
        this.products = cart.getProducts();
        this.discountCalculator = discountCalculator;
    }

    @Override
    public void addProduct(Product product, int quantity) {
        synchronized (products) {
            for (int i = 0; i < quantity; i++) {
                products.add(product);
            }
        }
    }

    @Override
    public double calculateSubTotal() {
        double subtotal = 0.0;
        synchronized (products) {
            for (Product product : products) {
                subtotal += product.getPrice();
            }
        }
        return subtotal;
    }

    @Override
    public double calculateTotal() {
        synchronized (products) {
            double total = calculateSubTotal() - discountCalculator.calculateDiscount(cart);
            return total;
        }
    }
}
