package com.example.demo.performance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TopService {
    private String serviceName;
    private int appointmentCount;
    @Id
    private Long id;

    // Constructor
    public TopService(String serviceName, int appointmentCount) {
        this.serviceName = serviceName;
        this.appointmentCount = appointmentCount;
    }

    public TopService() {

    }

    // Getters y Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
