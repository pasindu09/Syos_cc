package main.billgeneration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.billgeneration.bill.ProcessData;
import main.product.Product;
public class StockProcessDataForReorderLevels implements ProcessData {
    private List<StockBatch> stockBatch;
    private Map<Integer, Product> productMap;  // Map to hold product details

    public StockProcessDataForReorderLevels(List<StockBatch> stockBatch, List<Product> products) {
        this.stockBatch = stockBatch;
        this.productMap = new HashMap<>();
        for (Product product : products) {
            this.productMap.put(product.getId(), product);  // Populate the product map
        }
    }

    @Override
    public void processData() {
        System.out.println();
        System.out.println("          ----------- REORDER LEVELS OF STOCK ---------");
        System.out.println();

        Set<Integer> processedProducts = new HashSet<>();  // To avoid duplicate entries for the same product
        for (StockBatch batch : stockBatch) {
            if (batch.getQuantityInStock() < 50) {
                Product product = productMap.get(batch.getProductId());
                if (product != null && !processedProducts.contains(product.getId())) {
                    System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() + ", Items left: " + batch.getQuantityInStock());
                    processedProducts.add(product.getId());
                }
            }
        }

        if (processedProducts.isEmpty()) {
            System.out.println("No items need reordering. All stocks are above 50.");
        }
        System.out.println();
    }
}