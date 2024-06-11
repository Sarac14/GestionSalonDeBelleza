package com.example.demo.controller;

import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.Entidades.ProductoProveedorId;
import com.example.demo.Entidades.Proveedor;
import com.example.demo.repositorios.ProductoProveedorRepository;
import com.example.demo.repositorios.ProveedorRepository;
import com.example.demo.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoProveedorRepository proveedorProductoRepository;

    @GetMapping("/")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nuevoProducto")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        Optional<Producto> optionalProducto = productoService.getProductoById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setNombre(productoDetails.getNombre());
            producto.setPrecioCompra(productoDetails.getPrecioCompra());
            return ResponseEntity.ok(productoService.saveProducto(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (productoService.getProductoById(id).isPresent()) {
            productoService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    Para asigarnar proveedores a un proveedor:
    Put: http://localhost:8080/productos/{idProducto}/proveedores
    JSON: [1,2] ---> los id de los proveedores

     */
    @PutMapping("/{productoId}/proveedores")
    public ResponseEntity<Producto> asignarProveedoresAProducto(@PathVariable Long productoId, @RequestBody List<Long> proveedoresIds) {
        Producto producto = productoService.getProductoById(productoId).orElseThrow(() -> new ExpressionException("Producto no encontrado"));
        List<Proveedor> proveedores = proveedorRepository.findAllById(proveedoresIds);

        for (Proveedor proveedor : proveedores) {
            ProductoProveedor proveedorProducto = new ProductoProveedor();
            ProductoProveedorId id = new ProductoProveedorId(producto.getId(), proveedor.getId());
            proveedorProducto.setId(id);
            proveedorProducto.setProducto(producto);
            proveedorProducto.setProveedor(proveedor);
            proveedorProductoRepository.save(proveedorProducto);
        }

        return ResponseEntity.ok(producto);
    }

    @GetMapping("/{productoId}/proveedores")
    public ResponseEntity<List<Proveedor>> getProveedoresByProducto(@PathVariable Long productoId) {
        Optional<Producto> producto = productoService.getProductoById(productoId);
        if (!producto.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductoProveedor> productoProveedores = proveedorProductoRepository.findByProductoId(productoId);
        List<Proveedor> proveedores = productoProveedores.stream()
                .map(ProductoProveedor::getProveedor)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }
}