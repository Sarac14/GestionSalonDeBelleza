package com.example.demo.servicios;

import com.example.demo.Entidades.MateriaPrima;
import com.example.demo.Entidades.ProductoVenta;
import com.example.demo.repositorios.ProductoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoVentaService {

    @Autowired
    ProductoVentaRepository productoVentaRepository;

    public List<ProductoVenta> getAllProductoVenta() {
        return productoVentaRepository.findAll();
    }

    public Optional<ProductoVenta> getProductoById(Long id) {
        return productoVentaRepository.findById(id);
    }

    public ProductoVenta saveProducto(ProductoVenta producto) {
        return productoVentaRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoVentaRepository.deleteById(id);
    }

}
