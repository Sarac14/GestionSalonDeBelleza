package com.example.demo.controller;

import com.example.demo.Entidades.ServicioCita;
import com.example.demo.servicios.ServicioCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/serviciosCita")
public class ServicioCitaController {

    @Autowired
    private ServicioCitaService servicioCitaService;

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<ServicioCita>> obtenerServiciosDeCita(@PathVariable Long citaId) {
        List<ServicioCita> serviciosCita = servicioCitaService.findByCitaId(citaId);
        if (serviciosCita.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(serviciosCita);
        }
    }
}
