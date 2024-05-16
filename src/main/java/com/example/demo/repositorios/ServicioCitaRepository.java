package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Detalle;

public interface ServicioCitaRepository extends JpaRepository<Detalle, Integer> {
}