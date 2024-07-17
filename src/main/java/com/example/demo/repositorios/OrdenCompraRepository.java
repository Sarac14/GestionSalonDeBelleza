package com.example.demo.repositorios;

import com.example.demo.Entidades.OrdenCompra;
import com.example.demo.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    List<OrdenCompra> findByFechaOrdenBetween(LocalDate startDate, LocalDate endDate);
    //boolean existsByProductoAndEstadoOrdenCompra(Producto producto, String estado);
}
