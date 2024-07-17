package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class PurchaseOrderReport {
    private Long orderId;
    private Date orderDate;
    private String supplierName;
    private float totalAmount;
    private String orderStatus;
    @Id
    private Long id;

    // Constructor, Getters y Setters
    public PurchaseOrderReport(Long orderId, Date orderDate, String supplierName, float totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.supplierName = supplierName;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public PurchaseOrderReport() {

    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
