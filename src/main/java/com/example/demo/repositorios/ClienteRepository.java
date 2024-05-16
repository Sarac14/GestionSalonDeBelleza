package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entidades.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}