package com.example.demo.controller;

import com.example.demo.performance.ProductSalesByEmployeeReport;
import com.example.demo.performance.SalesByCustomerReport;
import com.example.demo.performance.TopSalesByCustomerReport;
import com.example.demo.servicios.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes-ventas")
public class SalesReportController {
    @Autowired
    private SalesReportService salesReportService;

    @GetMapping("/productos-por-empleado/{empleadoId}")
    public List<ProductSalesByEmployeeReport> getProductSalesByEmployee(
            @PathVariable Long empleadoId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return salesReportService.getProductSalesByEmployee(empleadoId, startDate, endDate);
    }
    /*
    [
        {
            "employeeId": 1,
                "employeeName": "John Doe",
                "productName": "Shampoo",
                "quantitySold": 5,
                "totalSales": 50.00
        },
        {
            "employeeId": 1,
                "employeeName": "John Doe",
                "productName": "Conditioner",
                "quantitySold": 3,
                "totalSales": 30.00
        }
    ]

     */



    @GetMapping("/ventas-cliente/{clienteId}")
    public List<SalesByCustomerReport> getSalesByCustomer(
            @PathVariable Long clienteId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return salesReportService.getSalesByCustomer(clienteId, startDate, endDate);
    }

    /*
    [
        {
            "customerId": 1,
            "customerName": "Jane Smith",
            "productName": "Shampoo",
            "quantitySold": 2,
            "totalSales": 20.00
        },
        {
            "customerId": 1,
            "customerName": "Jane Smith",
            "productName": "Conditioner",
            "quantitySold": 1,
            "totalSales": 10.00
        }
    ]

     */

    @GetMapping("/top-ventas-cliente")
    public List<TopSalesByCustomerReport> getTopSalesByCustomer(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return salesReportService.getTopSalesByCustomer(startDate, endDate);
    }
    /*
    [
        {
            "customerId": 1,
            "customerName": "Jane Smith",
            "totalSales": 200.00
        },
        {
            "customerId": 2,
            "customerName": "John Doe",
            "totalSales": 180.00
        }
        // ... hasta 20 entradas
    ]

     */
}
