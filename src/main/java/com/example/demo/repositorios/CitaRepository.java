package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Cita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
   /* @Query("SELECT c FROM Cita c JOIN c.serviciosCita sc WHERE sc.empleado.id = :empleadoId AND c.fecha = :fecha AND c.hora = :hora")
    List<Cita> findByEmpleadoAndFechaAndHora(@Param("empleadoId") Long empleadoId, @Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);*/
   @Query("SELECT c FROM Cita c JOIN c.serviciosCita sc WHERE sc.empleado.id = :empleadoId AND c.fecha = :fecha AND sc.horaInicio = :horaIni AND sc.horaFin = :horaFin")
    List<Cita> findByEmpleadoAndFechaAndHoraBetween(@Param("empleadoId") Long empleadoId, @Param("fecha") LocalDate fecha, @Param("horaIni") LocalTime horaInicio, @Param("horaFin") LocalTime horaFin);

    List<Cita> findByCliente_Cedula(Long cedula);
}