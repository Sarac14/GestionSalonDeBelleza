package com.example.demo.controller;

import com.example.demo.Entidades.Servicio;
import com.example.demo.repositorios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/")
    public List<Servicio> obtenerServicios() {
        return servicioRepository.findAll();
    }
}