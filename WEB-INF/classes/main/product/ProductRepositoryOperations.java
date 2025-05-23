package main.product;

import java.util.List;
import main.product.Product;

public interface ProductRepositoryOperations {

    List<Product> getAllProducts();

    Product getProductById(int productId);
}