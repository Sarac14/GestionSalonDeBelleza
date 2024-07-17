package com.example.demo.repositorios;

import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.VentaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VentaProductoRepository extends JpaRepository<VentaProducto, Long> {
    @Query("SELECT vp FROM VentaProducto vp WHERE vp.detalle.cita.fecha BETWEEN :startDate AND :endDate")
    List<VentaProducto> findByFechaBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
