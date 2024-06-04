package com.example.demo.repositorios;

import com.example.demo.Entidades.ProductoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, Long> {
}
