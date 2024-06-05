package com.example.demo.controller;

import com.example.demo.DTOs.OrdenCompraDTO;
import com.example.demo.Entidades.OrdenCompra;
import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.servicios.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordenesCompra")
public class OrdenCompraController {
    @Autowired
    private OrdenCompraService ordenCompraService;

    @PostMapping("/crear")
    public OrdenCompra crearOrdenCompra(@RequestBody OrdenCompraDTO ordenCompraDTO) {
        return ordenCompraService.crearOrdenCompra(
                ordenCompraDTO.getProveedorId(),
                ordenCompraDTO.getProductosIds(),
                ordenCompraDTO.getCantidades(),
                ordenCompraDTO.getFechaEntrega());

    }

    @GetMapping("/")
    public List<OrdenCompra> getAllOrdendesCompras() {
        return OrdenCompraService.getAllOrdendesCompras();
    }
}
