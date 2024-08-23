package com.example.demo.servicios;

import com.example.demo.Entidades.Empleado;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.ServicioCitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioCitaService {
    @Autowired
    private ServicioCitaRepository servicioCitaRepository;

    public List<ServicioCita> findByCitaId(Long citaId) {
        return servicioCitaRepository.findByCitaId(citaId);
    }

    public List<ServicioCita> findByEmpleadoId(Long empleadoId) {
        return servicioCitaRepository.findByEmpleadoId(empleadoId);
    }

    public List<ServicioCita> findAll() {
        return servicioCitaRepository.findAll();
    }

    public Empleado getEmpleadoByServicioCitaId(Long servicioCitaId) {
        Optional<ServicioCita> servicioCitaOptional = servicioCitaRepository.findById(Math.toIntExact(servicioCitaId));

        if (servicioCitaOptional.isPresent()) {
            ServicioCita servicioCita = servicioCitaOptional.get();
            return servicioCita.getEmpleado();
        } else {
            throw new EntityNotFoundException("ServicioCita no encontrado con id: " + servicioCitaId);
        }
    }
}
