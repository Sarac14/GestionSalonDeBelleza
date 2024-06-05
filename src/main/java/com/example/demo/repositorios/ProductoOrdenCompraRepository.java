package com.example.demo.repositorios;

import com.example.demo.Entidades.ProductoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoOrdenCompraRepository extends JpaRepository<ProductoOrdenCompra, Long> {
}
