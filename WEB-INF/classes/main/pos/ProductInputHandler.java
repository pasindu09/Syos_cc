package main.pos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Map;

import main.product.Product;
import main.product.ProductRepository;

public class ProductInputHandler {
    private volatile Map<Integer, Product> productMap;
    private final ProductRepository productRepository;
    private final ScheduledExecutorService scheduler;

    // Constructor to initialize the product map and start periodic refreshing
    public ProductInputHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productMap = new ConcurrentHashMap<>(); // Using ConcurrentHashMap for thread safety
        loadProductMap(); // Initial load of the product map
        scheduler = Executors.newScheduledThreadPool(1); // Scheduler to refresh the map periodically
        startPeriodicRefresh();
    }

    // Method to load products from the repository into the product map
    private void loadProductMap() {
        Map<Integer, Product> updatedMap = new ConcurrentHashMap<>();
        List<Product> products = productRepository.getAllProducts();
        for (Product product : products) {
            updatedMap.put(product.getId(), product);
        }
        productMap = updatedMap; // Atomically replace the old map with the new one
    }

    // Start the scheduler to refresh the product map every 5 minutes
    private void startPeriodicRefresh() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Refreshing product map...");
            loadProductMap(); // Refresh the product map
        }, 0, 5, TimeUnit.MINUTES); // Adjust the period as needed
    }

    // Handle product input by passing in productId and quantity
    public Product handleProductInput(int productId, Integer quantity) throws IllegalArgumentException {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity. Please provide a valid numeric quantity.");
        }

        Product product = productMap.get(productId);

        if (product != null) {
            return product; // Return the product if it exists
        } else {
            throw new IllegalArgumentException("Invalid product ID. No such product exists.");
        }
    }

    // Method to gracefully shut down the scheduler if needed
    public void shutdownScheduler() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
