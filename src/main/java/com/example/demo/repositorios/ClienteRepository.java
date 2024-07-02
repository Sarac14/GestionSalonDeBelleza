package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Cliente;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findFirstByCedula(Long cedula);
}