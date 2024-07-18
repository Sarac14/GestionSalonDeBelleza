package com.example.demo.repositorios;

import com.example.demo.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Servicio;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}