package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

}