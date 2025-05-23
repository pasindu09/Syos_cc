package main.product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import main.database.DatabaseConnection;
import main.database.MySqlConnection;
import main.product.ProductRepository;

public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        // Initialize the repository using MySqlConnection
        DatabaseConnection connectionInstance = new MySqlConnection();
        this.productRepository = new ProductRepository(connectionInstance);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all products using the repository
        List<Product> products = productRepository.getAllProducts();

        // Set the products as a request attribute to forward to JSP
        request.setAttribute("products", products);

        // Forward the request to the JSP page
        request.getRequestDispatcher("/templates/products.jsp").forward(request, response);
    }
}