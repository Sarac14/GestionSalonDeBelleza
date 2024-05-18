package com.example.demo.controller;

import com.example.demo.Entidades.Cita;
import com.example.demo.repositorios.CitaRepository;
import com.example.demo.servicios.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.time.Duration;


@RestController
@RequestMapping("/cita")
public class CitaController {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaService citaService;

    //Crear cita
    @PostMapping("/nuevaCita")
    public Cita crearCita(@RequestBody Cita cita) {
        return citaService.crearCitaConValidacion(cita);
    }

    //Listar citas
    @GetMapping("/")
    public List<Cita> obtenerCitas() {
        System.out.println("ENTRE LISTAR TODOS");
        return citaRepository.findAll();
    }

    // Leer una cita por ID
    @GetMapping("/{id}")
    public Optional<Cita> obtenerCitaPorId(@PathVariable Long id) {
        return citaRepository.findById(Math.toIntExact(id));
    }

    // Eliminar cita
    @DeleteMapping("/{id}")
    public void eliminarCita(@PathVariable Long id) {
        citaRepository.deleteById(Math.toIntExact(id));
    }

    // Actualizar una cita
//    @PutMapping("/modificar/{id}")
//    public Cita actualizarDetallesDeCita(@PathVariable Long id, @RequestBody Cita citaActualizada) {
//        return citaService.actualizarDetallesDeCita(id, citaActualizada);
//    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Cita> actualizarDetallesDeCita(@PathVariable Long id, @RequestBody Cita citaActualizada) {
        Cita cita = citaService.actualizarDetallesDeCita(id, citaActualizada);
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/tiempoEspera/{id}")
    public ResponseEntity<CitaService.TiempoEsperaResponse> obtenerTiempoEspera(@PathVariable Long id) {
        CitaService.TiempoEsperaResponse tiempoEspera = citaService.obtenerTiempoEsperaFormateado(id);
        return ResponseEntity.ok(tiempoEspera);
    }




}
