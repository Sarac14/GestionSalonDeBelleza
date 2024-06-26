package com.example.demo.servicios;

import com.example.demo.Entidades.Empleado;
import com.example.demo.repositorios.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    public Empleado findById(Long empleadoId) throws ClassNotFoundException{
        return empleadoRepository.findById(Math.toIntExact(empleadoId))
                .orElseThrow(() -> new ClassNotFoundException("Cliente no encontrado con ID: " + empleadoId));
    }

}
