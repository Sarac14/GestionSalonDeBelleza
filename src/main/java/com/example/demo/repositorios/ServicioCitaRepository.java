package com.example.demo.repositorios;

import com.example.demo.Entidades.ServicioCita;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Detalle;

public interface ServicioCitaRepository extends JpaRepository<ServicioCita, Integer> {
}