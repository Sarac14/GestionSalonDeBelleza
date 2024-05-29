package com.example.demo.repositorios;

import com.example.demo.Entidades.Persona;
import com.example.demo.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona,Long> {
}
