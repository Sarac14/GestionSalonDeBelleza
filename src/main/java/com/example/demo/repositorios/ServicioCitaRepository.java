package com.example.demo.repositorios;

import com.example.demo.Entidades.ServicioCita;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Detalle;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicioCitaRepository extends JpaRepository<ServicioCita, Integer> {
    List<ServicioCita> findByCitaId(Long citaId);

    List<ServicioCita> findByEmpleadoId(Long empleadoId);

    List<ServicioCita> findByEmpleadoIdAndFechaBetween(Long employeeId, LocalDate startDate, LocalDate  endDate);

    List<ServicioCita> findByEmpleadoCategoriaAndFechaBetween (String categoria, LocalDate startDate, LocalDate endDate);
}
