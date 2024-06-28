package com.example.demo.repositorios;

import com.example.demo.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Servicio;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

}