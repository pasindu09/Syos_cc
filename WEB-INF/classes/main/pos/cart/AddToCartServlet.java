package main.pos.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import main.pos.ProductInputHandler;
import main.product.Product;
import main.product.ProductRepository;
import main.database.DatabaseConnection;
import main.database.MySqlConnection;

public class AddToCartServlet extends HttpServlet {

    private ProductInputHandler productInputHandler;
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        // Initialize database connection and product repository
        DatabaseConnection connectionInstance = new MySqlConnection();
        this.productRepository = new ProductRepository(connectionInstance);
        this.productInputHandler = new ProductInputHandler(productRepository);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get product ID and quantity from the request
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");

        try {
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            Product product = productInputHandler.handleProductInput(productId, quantity);

            // Get the user's session and retrieve or create the cart
            HttpSession session = request.getSession();
            Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

            if (cart == null) {
                cart = new HashMap<>(); // Create a new cart if none exists
                session.setAttribute("cart", cart);
            }

            if (cart.containsKey(product)) {
                cart.put(product, cart.get(product) + quantity); // Update quantity
            } else {
                cart.put(product, quantity); // Add new product to the cart
            }

            // Set the status to 200 (optional because it's the default for successful
            // requests)
            response.setStatus(HttpServletResponse.SC_OK);

            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Product '" + product.getName() + "' added successfully. Quantity: " + quantity);
        } catch (IllegalArgumentException e) {
            // Handle invalid product ID or quantity (including NumberFormatException)
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
