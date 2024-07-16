package com.example.demo.controller;

import com.example.demo.performance.BillingByServiceCategory;
import com.example.demo.performance.TopService;
import com.example.demo.servicios.ServiceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes-servicios")
public class ServiceReportController {
    @Autowired
    private ServiceReportService reportService;

    @GetMapping("/categoria")
    public ResponseEntity<List<BillingByServiceCategory>> getBillingByServiceCategory(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<BillingByServiceCategory> report = reportService.getBillingByServiceCategory(startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*EJEMPLO DE JSON
    [
        {
            "category": "Haircut",
                "totalIncome": 1500.00
        },
        {
            "category": "Nail Care",
                "totalIncome": 1200.50
        }
    ]
*/
    @GetMapping("/top-services")
    public ResponseEntity<List<TopService>> getTopServices(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<TopService> report = reportService.getTopServices(startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*EJEMPLO JSON
    [
        {
            "serviceName": "Haircut",
                "appointmentCount": 50
        },
        {
            "serviceName": "Manicure",
                "appointmentCount": 35
        }
    ]

     */

}

