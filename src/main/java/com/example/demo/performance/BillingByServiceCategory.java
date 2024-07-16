package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BillingByServiceCategory {
    private String category;
    private double totalIncome;
    @Id
    private Long id;

    // Constructor
    public BillingByServiceCategory(String category, double totalIncome) {
        this.category = category;
        this.totalIncome = totalIncome;
    }

    public BillingByServiceCategory() {

    }

    // Getters y Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
