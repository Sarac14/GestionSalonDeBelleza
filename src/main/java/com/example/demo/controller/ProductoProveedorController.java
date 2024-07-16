package com.example.demo.controller;

import com.example.demo.DTOs.ProductoProveedorDTO;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.Entidades.ProductoProveedorId;
import com.example.demo.Entidades.Proveedor;
import com.example.demo.repositorios.ProductoProveedorRepository;
import com.example.demo.repositorios.ProductoRepository;
import com.example.demo.repositorios.ProveedorRepository;
import com.example.demo.servicios.ProductoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productoProveedor")
public class ProductoProveedorController {
    @Autowired
    private ProductoProveedorService productoProveedorService;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping("/")
    public List<ProductoProveedor> getAllProductosProveedores() {
        return productoProveedorService.getAllProductoProveedores();
    }

    @PostMapping("/crear")
    public ResponseEntity<ProductoProveedor> crearProductoProveedor(@RequestBody ProductoProveedorDTO request) {
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        ProductoProveedor productoProveedor = new ProductoProveedor();
        productoProveedor.setId(new ProductoProveedorId(producto.getId(), proveedor.getId()));
        productoProveedor.setProducto(producto);
        productoProveedor.setProveedor(proveedor);

        return ResponseEntity.status(HttpStatus.CREATED).body(productoProveedorRepository.save(productoProveedor));
    }
}
