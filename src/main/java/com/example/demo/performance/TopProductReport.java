package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TopProductReport {
    private Long productId;
    private String productName;
    private int salesCount;
    @Id
    private Long id;

    // Constructor, Getters y Setters
    public TopProductReport(Long productId, String productName, int salesCount) {
        this.productId = productId;
        this.productName = productName;
        this.salesCount = salesCount;
    }

    public TopProductReport() {

    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getSalesCount() { return salesCount; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
