package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CurrentInventoryReport {
    private Long productId;
    private String productName;
    private int currentStock;
    private int minimumStock;
    @Id
    private Long id;

    // Constructor, Getters y Setters
    public CurrentInventoryReport(Long productId, String productName, int currentStock, int minimumStock) {
        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
    }

    public CurrentInventoryReport() {

    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getCurrentStock() { return currentStock; }
    public void setCurrentStock(int currentStock) { this.currentStock = currentStock; }
    public int getMinimumStock() { return minimumStock; }
    public void setMinimumStock(int minimumStock) { this.minimumStock = minimumStock; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}