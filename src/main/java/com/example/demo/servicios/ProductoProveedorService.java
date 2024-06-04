package com.example.demo.servicios;

import com.example.demo.Entidades.ProductoProveedor;
import com.example.demo.repositorios.ProductoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoProveedorService {
    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    public List<ProductoProveedor> getAllProductoProveedores() {
        return productoProveedorRepository.findAll();
    }

    public Optional<ProductoProveedor> getProductoProveedorById(Long id) {
        return productoProveedorRepository.findById(id);
    }

    public ProductoProveedor saveProductoProveedor(ProductoProveedor productoProveedor) {
        return productoProveedorRepository.save(productoProveedor);
    }

    public void deleteProductoProveedor(Long id) {
        productoProveedorRepository.deleteById(id);
    }
}
