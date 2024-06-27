package com.example.demo.repositorios;

import com.example.demo.Entidades.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NominaRepository extends JpaRepository<Nomina, Long> {

     Nomina findByEmpleadoId(Long id);
}
