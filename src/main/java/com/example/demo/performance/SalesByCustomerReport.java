package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SalesByCustomerReport {
    private Long customerId;
    private String customerName;
    private String productName;
    private int quantitySold;
    private float totalSales;
    @Id
    private Long id;

    public SalesByCustomerReport() {
    }

    public SalesByCustomerReport(Long customerId, String customerName, String productName, int quantitySold, float totalSales) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}