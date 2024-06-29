package com.example.demo.repositorios;

import com.example.demo.Entidades.ServicioCita;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Detalle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioCitaRepository extends JpaRepository<ServicioCita, Integer> {
    List<ServicioCita> findByCitaId(Long citaId);
}