package com.example.demo.controller;

import com.example.demo.performance.AlertProductReport;
import com.example.demo.performance.CurrentInventoryReport;
import com.example.demo.performance.PurchaseOrderReport;
import com.example.demo.performance.TopProductReport;
import com.example.demo.servicios.InventoryReportService;
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
@RequestMapping("/reportes-inventario")
public class InventoryReportController {
    @Autowired
    private InventoryReportService inventoryReportService;

    @GetMapping("/current-inventory")
    public ResponseEntity<List<CurrentInventoryReport>> getCurrentInventoryReport() {
        List<CurrentInventoryReport> report = inventoryReportService.getCurrentInventoryReport();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*EJEMPLO DE JSON
    [
        {
            "productId": 1,
                "productName": "Shampoo",
                "currentStock": 100,
                "minimumStock": 20
        },
        {
            "productId": 2,
                "productName": "Conditioner",
                "currentStock": 50,
                "minimumStock": 10
        }
    ]

     */


    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductReport>> getTopProductsReport(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<TopProductReport> report = inventoryReportService.getTopProductsReport(startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*
    [
        {
            "productId": 1,
                "productName": "Shampoo",
                "salesCount": 150
        },
        {
            "productId": 2,
                "productName": "Conditioner",
                "salesCount": 100
        }
    ]

     */


    @GetMapping("/ordenes-compra")
    public ResponseEntity<List<PurchaseOrderReport>> getPurchaseOrderReport(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<PurchaseOrderReport> report = inventoryReportService.getPurchaseOrderReport(startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*
    [
        {
            "orderId": 1,
                "orderDate": "2024-01-15",
                "supplierName": "Supplier A",
                "totalAmount": 500.00,
                "orderStatus": "Delivered"
        },
        {
            "orderId": 2,
                "orderDate": "2024-02-10",
                "supplierName": "Supplier B",
                "totalAmount": 300.00,
                "orderStatus": "Pending"
        }
    ]

     */


    @GetMapping("/alert-products")
    public ResponseEntity<List<AlertProductReport>> getAlertProductReport() {
        List<AlertProductReport> report = inventoryReportService.getAlertProductReport();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    /*
    [
        {
            "productId": 1,
                "productName": "Shampoo",
                "currentStock": 10,
                "minimumStock": 20
        },
        {
            "productId": 2,
                "productName": "Conditioner",
                "currentStock": 5,
                "minimumStock": 10
        }
    ]

     */


}
