package com.example.demo.servicios;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Servicio;
import com.example.demo.repositorios.ProveedorRepository;
import com.example.demo.repositorios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public LocalTime calcularHoraFin(LocalTime horaInicio, Integer duracion) {
        Duration duration = Duration.ofDays(duracion);
        return horaInicio.plus(duration);
    }

}
