package com.example.demo.controller;

import com.example.demo.Entidades.Empleado;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/")
    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    @GetMapping("/servicios/{id}")
    public List<ServicioCita> obtenerServiciosEmpleado(@PathVariable Long id) {
        Empleado empleado = empleadoRepository.findById(Math.toIntExact(id)).orElseThrow();
        List<ServicioCita> serviciosEmpleado = empleado.getServicioCitas();
        return serviciosEmpleado;
    }
}
