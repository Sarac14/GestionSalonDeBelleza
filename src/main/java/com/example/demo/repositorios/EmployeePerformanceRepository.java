package com.example.demo.repositorios;

import com.example.demo.performance.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, Long> {
    List<EmployeePerformance> findByEmployeeId(Long employeeId);
}

