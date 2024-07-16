package com.example.demo.controller;

import com.example.demo.performance.EmployeePerformance;
import com.example.demo.servicios.EmployeePerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/performance")
public class EmployeePerformanceController {
    @Autowired
    private EmployeePerformanceService performanceService;

    @GetMapping("/empleado/{id}")
    public ResponseEntity<EmployeePerformance> getPerformanceByEmployeeId(
            @PathVariable Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        EmployeePerformance performance = performanceService.calculatePerformance(id, startDate, endDate);
        return new ResponseEntity<>(performance, HttpStatus.OK);
    }
    /*
    EJEMPLO DEL JSON
     {
    "employeeId": 1,
    "reportDate": "2024-07-15",
    "totalIncome": 1200.50,
    "appointmentCount": 15
    }
    */

    @GetMapping("/categoria/{category}")
    public ResponseEntity<List<EmployeePerformance>> getPerformanceByCategory(
            @PathVariable String category, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<EmployeePerformance> performances = performanceService.calculatePerformanceByCategory(category, startDate, endDate);
        return new ResponseEntity<>(performances, HttpStatus.OK);
    }
    /*
    [
    {
        "employeeId": 1,
        "reportDate": "2024-07-15",
        "totalIncome": 1200.50,
        "appointmentCount": 15
    },
    {
        "employeeId": 2,
        "reportDate": "2024-07-15",
        "totalIncome": 950.75,
        "appointmentCount": 10
    },
    {
        "employeeId": 3,
        "reportDate": "2024-07-15",
        "totalIncome": 1100.00,
        "appointmentCount": 12
    }
]

     */
}
