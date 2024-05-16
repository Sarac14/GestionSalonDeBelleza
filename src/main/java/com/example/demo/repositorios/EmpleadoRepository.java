package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
