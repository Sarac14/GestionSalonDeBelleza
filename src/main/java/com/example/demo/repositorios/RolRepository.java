package com.example.demo.repositorios;

import com.example.demo.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Long> {

   Rol findByDescripcion(String descripcion);
}
