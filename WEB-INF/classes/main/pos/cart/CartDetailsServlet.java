package main.pos.cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import main.pos.PaymentProcessor;
import main.product.Product;
import main.pos.cart.Cart;
import main.pos.cart.CartOperations;
import main.pos.cart.DiscountCalculator;
import main.pos.cart.PercentageDiscountCalculator;
import main.pos.cart.CartService;
import main.pos.cart.PaymentDetails;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class CartDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Ensure thread safety when accessing session cart
        synchronized (session) {
            // Retrieve the cart from the session
            Map<Product, Integer> sessionCart = (Map<Product, Integer>) session.getAttribute("cart");

            // Initialize a cart object
            Cart cart = new Cart(); 

            // Create discount calculator and cart operations
            DiscountCalculator discountCalculator = new PercentageDiscountCalculator(0.02);
            CartService cartOperations = new CartOperations(cart, discountCalculator);
            List<Map<String, Object>> items = new ArrayList<>();

            // Add all products from sessionCart to Cart
            if (sessionCart != null) {
                for (Map.Entry<Product, Integer> entry : sessionCart.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    cartOperations.addProduct(product, quantity);

                    // Create a map for the individual product details
                    Map<String, Object> item = new HashMap<>();
                    item.put("productId", product.getId());
                    item.put("productName", product.getName());
                    item.put("price", product.getPrice());
                    item.put("quantity", quantity);

                    // Add the item map to the list
                    items.add(item);
                }
            }

            // Process the payment to get cart details
            PaymentProcessor paymentProcessor = new PaymentProcessor(cartOperations, discountCalculator, cart);
            PaymentDetails paymentDetails = paymentProcessor.handlePaymentProcess();

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("subTotalDue", paymentDetails.getSubTotalDue());
            responseMap.put("totalDue", paymentDetails.getTotalDue());
            responseMap.put("discount", paymentDetails.getDiscount());
            responseMap.put("items", items); // Add the items list

            // Convert the payment details into JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);

            // Set response type to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Write the JSON data to the response
            response.getWriter().write(jsonResponse);
        }
    }
}
