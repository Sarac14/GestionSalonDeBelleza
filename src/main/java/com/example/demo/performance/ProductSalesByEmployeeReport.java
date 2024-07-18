package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductSalesByEmployeeReport {
    private Long employeeId;
    private String employeeName;
    private String productName;
    private int quantitySold;
    private float totalSales;
    @Id
    private Long id;

    public ProductSalesByEmployeeReport(Long employeeId, String employeeName, String productName, int quantitySold, float totalSales) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
    }

    public ProductSalesByEmployeeReport() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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