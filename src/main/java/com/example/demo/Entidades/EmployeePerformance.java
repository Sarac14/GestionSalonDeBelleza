package com.example.demo.Entidades;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity

public class EmployeePerformance {
    private Long employeeId;
    private LocalDate reportDate;
    private double totalIncome;
    private int appointmentCount;
    @Id
    private Long id;

    // Constructor
    public EmployeePerformance() {
    }

    public EmployeePerformance(Long employeeId, LocalDate reportDate, double totalIncome, int appointmentCount) {
        this.employeeId = employeeId;
        this.reportDate = reportDate;
        this.totalIncome = totalIncome;
        this.appointmentCount = appointmentCount;
    }

    // Getters y Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
