package com.example.demo.servicios;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.CitaRepository;
import com.example.demo.repositorios.ClienteRepository;
import com.example.demo.repositorios.EmpleadoRepository;
import com.example.demo.repositorios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Cita crearCitaConValidacion(Cita cita) {
        System.out.println("Creando nueva cita...");
        System.out.println("Cita recibida: " + cita);

        Cliente cliente = clienteRepository.findById(Math.toIntExact(cita.getCliente().getId()))
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        // Crear la cita
        Cita newCita = new Cita();
        newCita.setFecha(cita.getFecha());
        newCita.setHora(cita.getHora());
        newCita.setCliente(cliente);

        List<ServicioCita> detallesCita = new ArrayList<>();

        for (ServicioCita servicioCita : cita.getServiciosCita()) {
            Servicio servicio = servicioCita.getServicio();
            Empleado empleado = servicioCita.getEmpleado();

            if (!servicio.getCategoria().equals(empleado.getCategoria())) {
                throw new RuntimeException("La categoría del servicio y del empleado no coinciden para el servicio con ID: " + servicio.getId());
            }
        }
        // Crear y asociar los detalles de la cita (servicios y empleados)
        for (ServicioCita servicioCita : cita.getServiciosCita()) {
            Servicio servicio = servicioRepository.findById(Math.toIntExact(servicioCita.getServicio().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado"));
            Empleado empleado = empleadoRepository.findById(Math.toIntExact(servicioCita.getEmpleado().getCedula()))
                    .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

            servicioCita.setCita(newCita);
            servicioCita.setServicio(servicio);
            servicioCita.setEmpleado(empleado);

            detallesCita.add(servicioCita);
        }
        newCita.setServiciosCita(detallesCita);

        return citaRepository.save(newCita);
    }


    public Cita actualizarDetallesDeCita(Long idCita, Cita citaActualizada) {
        Cita citaExistente = citaRepository.findById(Math.toIntExact(idCita))
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        // Actualizar los detalles de la cita
        citaExistente.setFecha(citaActualizada.getFecha());
        citaExistente.setHora(citaActualizada.getHora());
        citaExistente.setCliente(citaActualizada.getCliente());

        List<ServicioCita> detallesExistentes = citaExistente.getServiciosCita();
        detallesExistentes.clear();
        detallesExistentes.addAll(citaActualizada.getServiciosCita());
        detallesExistentes.forEach(detalle -> detalle.setCita(citaExistente));

        // Guardar la cita actualizada en la base de datos
        return citaRepository.save(citaExistente);
    }
}

