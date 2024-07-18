package com.example.demo.controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Servicio;
import com.example.demo.Entidades.ServicioCita;
import com.example.demo.repositorios.ServicioRepository;
import com.example.demo.servicios.ClienteService;
import com.example.demo.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable("id") Long id) {
        Servicio servicio = servicioService.getServicioById(id);
        if (servicio != null) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/crearServicio")
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio createdServicio = servicioService.saveOrUpdateServicio(servicio);
        return new ResponseEntity<>(createdServicio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable("id") Long id, @RequestBody Servicio servicio) {
        Servicio existingServicio = servicioService.getServicioById(id);
        if (existingServicio != null) {
            servicio.setId(id);
            Servicio updatedServicio = servicioService.saveOrUpdateServicio(servicio);
            return new ResponseEntity<>(updatedServicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable("id") Long id) {
        Servicio existingServicio = servicioService.getServicioById(id);
        if (existingServicio != null) {
            servicioService.deleteServicio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}