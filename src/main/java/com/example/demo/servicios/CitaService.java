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

        Cliente cliente = clienteRepository.findById(Math.toIntExact(cita.getCliente().getCedula()))
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

    /*public Cita actualizarCitaConDetalles(Long idCita, Cita citaActualizada) {
        // Obtener la cita existente
        Cita citaExistente = citaRepository.findById(Math.toIntExact(idCita))
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        // Actualizar la fecha y hora de la cita si se proporcionan en la cita actualizada
        if (citaActualizada.getFecha() != null) {
            citaExistente.setFecha(citaActualizada.getFecha());
        }
        if (citaActualizada.getHora() != null) {
            citaExistente.setHora(citaActualizada.getHora());
        }

        // Actualizar los detalles de la cita (servicios y empleados)
        if (citaActualizada.getDetallesCita() != null && !citaActualizada.getDetallesCita().isEmpty()) {
            List<DetalleCita> nuevosDetalles = new ArrayList<>();
            for (DetalleCita detalleActualizado : citaActualizada.getDetallesCita()) {
                DetalleCita detalleExistente = null;
                // Buscar si el detalle ya existe en la cita actual
                for (DetalleCita detalle : citaExistente.getDetallesCita()) {
                    if (detalle.getId().equals(detalleActualizado.getId())) {
                        detalleExistente = detalle;
                        break;
                    }
                }
                if (detalleExistente != null) {
                    // Actualizar el detalle existente con los datos de la cita actualizada
                    if (detalleActualizado.getServicio() != null) {
                        detalleExistente.setServicio(detalleActualizado.getServicio());
                    }
                    if (detalleActualizado.getEmpleado() != null) {
                        detalleExistente.setEmpleado(detalleActualizado.getEmpleado());
                    }
                    // Agregar el detalle actualizado a la lista de detalles nuevos
                    nuevosDetalles.add(detalleExistente);
                } else {
                    // Si el detalle no existe en la cita actual, agregar el detalle actualizado a la lista de detalles nuevos
                    nuevosDetalles.add(detalleActualizado);
                }
            }
            // Establecer los nuevos detalles en la cita existente
            citaExistente.setDetallesCita(nuevosDetalles);
        }

        // Guardar la cita actualizada en la base de datos
        return citaRepository.save(citaExistente);
    }*/

    /*public Cita actualizarCita(Long idCita, @RequestBody Cliente clienteActualizado) {
        Cita citaExistente = citaRepository.findById(Math.toIntExact(idCita))
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        // Actualizar el cliente de la cita
        citaExistente.setCliente(clienteActualizado);

        // Guardar la cita actualizada en la base de datos
        return citaRepository.save(citaExistente);
    }
*/
    public Cita actualizarDetallesDeCita(Long idCita, Cita citaActualizada) {
        // Obtener la cita existente
        Cita citaExistente = citaRepository.findById(Math.toIntExact(idCita))
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        // Actualizar los detalles de la cita
        for (ServicioCita detalleActualizado : citaActualizada.getServiciosCita()) {
            for (ServicioCita detalleExistente : citaExistente.getServiciosCita()) {
                if (detalleActualizado.getId().equals(detalleExistente.getId())) {
                    detalleExistente.setServicio(detalleActualizado.getServicio());
                    detalleExistente.setEmpleado(detalleActualizado.getEmpleado());
                    break;
                }
            }
        }

        // Guardar la cita actualizada en la base de datos
        return citaRepository.save(citaExistente);
    }

}

