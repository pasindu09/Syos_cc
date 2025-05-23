package main.billgeneration;

import java.util.Date;

public class StockBatch {
    private int batchId; // Corresponds to batch_id
    private int productId; // Corresponds to product_id, Integer to allow nulls
    private Date dateOfPurchase; // Corresponds to date_of_purchase
    private int quantityReceived; // Corresponds to quantity_received
    private Date expiryDate; // Corresponds to expiry_date
    private int quantityInStock; // Corresponds to quantity_in_stock

    // Constructor
    public StockBatch(int batchId, int productId, Date dateOfPurchase, int quantityReceived, Date expiryDate, int quantityInStock) {
        this.batchId = batchId;
        this.productId = productId;
        this.dateOfPurchase = dateOfPurchase;
        this.quantityReceived = quantityReceived;
        this.expiryDate = expiryDate;
        this.quantityInStock = quantityInStock;
    }

    // Getter methods
    public int getBatchId() {
        return batchId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    @Override
    public String toString() {
        return "StockBatch [batchId=" + batchId + ", productId=" + productId + ", dateOfPurchase=" + dateOfPurchase
                + ", quantityReceived=" + quantityReceived + ", expiryDate=" + expiryDate + ", quantityInStock="
                + quantityInStock + "]";
    }
}

