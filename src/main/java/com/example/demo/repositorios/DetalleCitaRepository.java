package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Detalle;

public interface DetalleCitaRepository extends JpaRepository<Detalle, Integer> {
}