package com.example.demo.controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Servicio;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.ServicioRepository;
import com.example.demo.servicios.ClienteService;
import com.example.demo.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public List<Servicio> obtenerServicios() {
        return servicioRepository.findAll();
    }

}