package com.example.demo.servicios;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Autowired
    private DetalleRepository detalleRepository;

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

        List<ServicioCita> serviciosCita = new ArrayList<>();

        for (ServicioCita servicioCita : cita.getServiciosCita()) {
            Servicio servicio = servicioRepository.findById(Math.toIntExact(servicioCita.getServicio().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado"));
            Empleado empleado = empleadoRepository.findById(Math.toIntExact(servicioCita.getEmpleado().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

            if (!servicio.getCategoria().equals(empleado.getCategoria())) {
                throw new RuntimeException("La categoría del servicio y del empleado no coinciden para el servicio con ID: " + servicio.getId());
            }

            ServicioCita newServicioCita = new ServicioCita();
            newServicioCita.setCita(newCita);
            newServicioCita.setServicio(servicio);
            newServicioCita.setEmpleado(empleado);
            serviciosCita.add(newServicioCita);
        }

        newCita.setServiciosCita(serviciosCita);
        return citaRepository.save(newCita);
    }




    public Cita actualizarDetallesDeCita(Long idCita, Cita citaActualizada) {
        Cita citaExistente = citaRepository.findById(Math.toIntExact(idCita))
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        // Actualizar los detalles de la cita
        citaExistente.setFecha(citaActualizada.getFecha());
        citaExistente.setHora(citaActualizada.getHora());
        citaExistente.setCliente(citaActualizada.getCliente());

        List<ServicioCita> serviciosCitaExistente = citaExistente.getServiciosCita();
        serviciosCitaExistente.clear();
        serviciosCitaExistente.addAll(citaActualizada.getServiciosCita());
        serviciosCitaExistente.forEach(detalle -> detalle.setCita(citaExistente));

        // Guardar la cita actualizada en la base de datos
        return citaRepository.save(citaExistente);
    }

    public Duration calcularTiempoEspera(Long id) {
        Optional<Cita> citaOpt = citaRepository.findById(Math.toIntExact(id));
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            LocalDateTime fechaHoraCita = LocalDateTime.of(cita.getFecha(), cita.getHora());
            LocalDateTime ahora = LocalDateTime.now();
            if (fechaHoraCita.isAfter(ahora)) {
                return Duration.between(ahora, fechaHoraCita);
            }
        }
        return Duration.ZERO;
    }

    public TiempoEsperaResponse obtenerTiempoEsperaFormateado(Long id) {
        Duration duration = calcularTiempoEspera(id);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return new TiempoEsperaResponse(hours, minutes, seconds);
    }

    public static class TiempoEsperaResponse {
        private long hours;
        private long minutes;
        private long seconds;

        public TiempoEsperaResponse(long hours, long minutes, long seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        // Getters y setters
        public long getHours() {
            return hours;
        }

        public void setHours(long hours) {
            this.hours = hours;
        }

        public long getMinutes() {
            return minutes;
        }

        public void setMinutes(long minutes) {
            this.minutes = minutes;
        }

        public long getSeconds() {
            return seconds;
        }

        public void setSeconds(long seconds) {
            this.seconds = seconds;
        }


    }
}
