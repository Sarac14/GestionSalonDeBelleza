package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}