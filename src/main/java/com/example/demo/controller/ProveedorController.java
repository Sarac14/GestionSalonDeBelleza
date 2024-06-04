package com.example.demo.controller;

import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.Entidades.Proveedor;
import com.example.demo.repositorios.ProductoProveedorRepository;
import com.example.demo.repositorios.ProductoRepository;
import com.example.demo.servicios.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/")
    public List<Proveedor> getAllProveedores() {
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.getProveedorById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nuevoProveedor")
    public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.saveProveedor(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorDetails) {
        Optional<Proveedor> optionalProveedor = proveedorService.getProveedorById(id);
        if (optionalProveedor.isPresent()) {
            Proveedor proveedor = optionalProveedor.get();
            proveedor.setNombreEmpresa(proveedorDetails.getNombreEmpresa());
            proveedor.setNombreContacto(proveedorDetails.getNombreContacto());
            proveedor.setTelefono(proveedorDetails.getTelefono());
            proveedor.setCorreoElectronico(proveedorDetails.getCorreoElectronico());
            proveedor.setDireccion(proveedorDetails.getDireccion());
            return ResponseEntity.ok(proveedorService.saveProveedor(proveedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        if (proveedorService.getProveedorById(id).isPresent()) {
            proveedorService.deleteProveedor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}*/

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoProveedorRepository proveedorProductoRepository;

    @GetMapping("/")
    public List<Proveedor> getAllProveedores() {
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.getProveedorById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nuevoProveedor")
    public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.saveProveedor(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorDetails) {
        Optional<Proveedor> optionalProveedor = proveedorService.getProveedorById(id);
        if (optionalProveedor.isPresent()) {
            Proveedor proveedor = optionalProveedor.get();
            proveedor.setNombreContacto(proveedorDetails.getNombreContacto());
            proveedor.setDireccion(proveedorDetails.getDireccion());
            return ResponseEntity.ok(proveedorService.saveProveedor(proveedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        if (proveedorService.getProveedorById(id).isPresent()) {
            proveedorService.deleteProveedor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{proveedorId}/productos")
    public ResponseEntity<Proveedor> asignarProductosAProveedor(@PathVariable Long proveedorId, @RequestBody List<Long> productosIds) {
        Proveedor proveedor = proveedorService.getProveedorById(proveedorId).orElseThrow(() -> new ExpressionException("Proveedor no encontrado"));
        List<Producto> productos = productoRepository.findAllById(productosIds);

        for (Producto producto : productos) {
            ProductoProveedor proveedorProducto = new ProductoProveedor();
            proveedorProducto.setProveedor(proveedor);
            proveedorProducto.setProducto(producto);
            proveedorProductoRepository.save(proveedorProducto);
        }

        return ResponseEntity.ok(proveedor);
    }
}
