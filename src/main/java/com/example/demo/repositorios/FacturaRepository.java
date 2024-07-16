package com.example.demo.repositorios;

import com.example.demo.Entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Factura findByIdCita(Long idCita);
}
