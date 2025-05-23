package main.stock;

import java.sql.Date;
import java.util.List;


public class StockBatch {
    private int batchId;
    private int productId;
    private Date dateOfPurchase;
    private int quantityReceived;
    private Date expiryDate;
    private int quantityInStock;

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public StockBatch(int batchId, int productId, Date dateOfPurchase, int quantityReceived, Date expiryDate, int quantityInStock) {
        this.batchId = batchId;
        this.productId = productId;
        this.dateOfPurchase = dateOfPurchase;
        this.quantityReceived = quantityReceived;
        this.expiryDate = expiryDate;
        this.quantityInStock = quantityInStock;
    }

    public int getBatchId() {
        return batchId;
    }

    public int getProductId() {
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

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
