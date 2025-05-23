package main.billing;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import main.database.DatabaseConnection;
import main.database.MySqlConnection;
import main.pos.PointOfSale;
import main.pos.cart.Cart;
import main.pos.cart.CartOperations;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;
import main.pos.cart.PercentageDiscountCalculator;
import main.product.Product;
import main.pos.PaymentProcessor;
import main.pos.PayedAmount;
import main.pos.ChangeAmount;
import main.pos.cart.ReturnCartItemsService;
import main.pos.cart.ReturnCartItems;
import main.billing.SaveBill;



public class BillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Synchronize on session to ensure thread-safe access
        HttpSession session = request.getSession();
        synchronized (session) {
            // Parse the incoming JSON request payload
            Gson gson = new Gson();
            Map<String, Object> requestData = gson.fromJson(request.getReader(), Map.class);

            // Extract cart items from the request
            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) requestData.get("cartItems");

            // Extract total paid and change from the request
            double totalPaid = Double.parseDouble(requestData.get("totalPaid").toString());
            double change = Double.parseDouble(requestData.get("change").toString());

            // Initialize PayedAmount and ChangeAmount using setter methods
            PayedAmount payedAmount = new PayedAmount();
            payedAmount.setAmountPaid(totalPaid);

            ChangeAmount changeAmount = new ChangeAmount();
            changeAmount.setChangeAmount(change);

            // Initialize the cart and add products
            Cart cart = new Cart();
            if (cartItems != null) {
                for (Map<String, Object> item : cartItems) {
                    String name = (String) item.get("productName");
                    double price = ((Double) item.get("price")).doubleValue();
                    int quantity = ((Double) item.get("quantity")).intValue();

                    int id = cart.getProducts().size() + 1;
                    Product product = new Product(id, name, price);
                    for (int i = 0; i < quantity; i++) {
                        cart.getProducts().add(product);  // Add product to cart based on quantity
                    }
                }
            }

            // Continue with the rest of your bill calculation and response
            DiscountCalculator discountCalculator = new PercentageDiscountCalculator(0.02);
            CartService cartService = new CartOperations(cart, discountCalculator);
            PaymentProcessor paymentProcessor = new PaymentProcessor(cartService, discountCalculator, cart);
            DatabaseConnection dbConnection = new MySqlConnection();
            PointOfSale pos = new PointOfSale(payedAmount, changeAmount, discountCalculator, cartService, cart, dbConnection);
            Cart carts = new Cart();


            for (Map<String, Object> item : cartItems) {
                int productId = ((Double) item.get("productId")).intValue(); // Convert to int
                String productName = (String) item.get("productName");
                double price = ((Double) item.get("price")).doubleValue();
                int quantity = ((Double) item.get("quantity")).intValue(); // Convert to int
    
                // Create a new product instance
                Product product = new Product(productId, productName, price);
    
                // Add the product to the cart as many times as specified by the quantity
                for (int i = 0; i < quantity; i++) {
                    carts.getProducts().add(product);
                }
            }
            
            ReturnCartItemsService returnCartItemsService = new ReturnCartItems(carts);
            List<Object[]> cartContents = returnCartItemsService.returnItems();
            SaveBill billReciept = new SaveBill(dbConnection, cartContents); 
            billReciept.saveBillReciept();           
            // Get the bill data
            Map<String, Object> billData = pos.posTerminal();

            // Send the response back
            String jsonResponse = gson.toJson(billData);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }
}
