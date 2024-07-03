package com.example.demo.servicios;

import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.Entidades.Proveedor;
import com.example.demo.repositorios.ProductoRepository;
import com.example.demo.repositorios.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto saveProducto(Producto producto) {
        Set<ProductoProveedor> proveedoresProducto = new HashSet<>();

        for (ProductoProveedor productoProveedor : producto.getProveedoresProducto()) {
            Proveedor proveedor = proveedorRepository.findById(productoProveedor.getProveedor().getId())
                    .orElseThrow(() -> new ProviderNotFoundException("Proveedor not found"));
            productoProveedor.setProveedor(proveedor);
            productoProveedor.setProducto(producto);
            proveedoresProducto.add(productoProveedor);
        }

        producto.setProveedoresProducto(proveedoresProducto);

        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}