package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TopSalesByCustomerReport {
    private Long customerId;
    private String customerName;
    private float totalSales;
    @Id
    private Long id;

    public TopSalesByCustomerReport() {
    }

    public TopSalesByCustomerReport(Long customerId, String customerName, float totalSales) {
        this.customerId = customerId;
        this.customerName = customerName;
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
