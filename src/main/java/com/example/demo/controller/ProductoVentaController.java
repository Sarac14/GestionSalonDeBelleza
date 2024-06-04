package com.example.demo.controller;

import com.example.demo.Entidades.ProductoVenta;
import com.example.demo.servicios.ProductoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productoVenta")
public class ProductoVentaController {
    @Autowired
    private ProductoVentaService productoVentaService;

    @GetMapping("/")
    public List<ProductoVenta> getAllProductoVenta() {
        return productoVentaService.getAllProductoVenta();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoVenta> getProductoById(@PathVariable Long id) {
        Optional<ProductoVenta> productoVenta = productoVentaService.getProductoById(id);
        return productoVenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nuevaProductoVenta")
    public ProductoVenta createProductoVenta(@RequestBody ProductoVenta productoVenta) {
        return productoVentaService.saveProducto(productoVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoVenta> updateProductoVenta(@PathVariable Long id, @RequestBody ProductoVenta productoVentaDetails) {
        Optional<ProductoVenta> optionalProductoVenta = productoVentaService.getProductoById(id);
        if (optionalProductoVenta.isPresent()) {
            ProductoVenta productoVenta = optionalProductoVenta.get();
            productoVenta.setMarca(productoVentaDetails.getMarca());
            productoVenta.setNombre(productoVentaDetails.getNombre());
            productoVenta.setDescripcion(productoVentaDetails.getDescripcion());
            productoVenta.setCantidadStock(productoVentaDetails.getCantidadStock());
            productoVenta.setPrecioCompra(productoVentaDetails.getPrecioCompra());
            productoVenta.setCantidadMinima(productoVentaDetails.getCantidadMinima());
            productoVenta.setPrecioVenta(productoVentaDetails.getPrecioVenta());
            productoVenta.setDescuento(productoVentaDetails.getDescuento());

            return ResponseEntity.ok(productoVentaService.saveProducto(productoVenta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoVenta(@PathVariable Long id) {
        if (productoVentaService.getProductoById(id).isPresent()) {
            productoVentaService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
