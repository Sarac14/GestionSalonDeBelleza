package com.example.demo.repositorios;

import com.example.demo.Entidades.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
}
