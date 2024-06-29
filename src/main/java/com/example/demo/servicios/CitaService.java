package com.example.demo.servicios;

import com.example.demo.Entidades.*;
import com.example.demo.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    private ServicioCitaRepository servicioCitaRepository;

    @Autowired
    private NominaRepository nominaRepository;

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
        newCita.setVigente(true);

        List<ServicioCita> serviciosCita = new ArrayList<>();
        LocalTime currentHoraInicio = cita.getHora();

        for (ServicioCita servicioCita : cita.getServiciosCita()) {
            Servicio servicio = servicioRepository.findById(Math.toIntExact(servicioCita.getServicio().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado"));
            Empleado empleado = empleadoRepository.findById(Math.toIntExact(servicioCita.getEmpleado().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

            LocalTime horaInicio = currentHoraInicio;
            LocalTime horaFin = horaInicio.plusMinutes(servicio.getDuracion());

            // Verificar disponibilidad del empleado en el intervalo de tiempo del servicio
            if (!estaEmpleadoDisponible(empleado, cita.getFecha(), horaInicio, horaFin)) {
                throw new RuntimeException("El empleado con ID: " + empleado.getId() + " no está disponible de " + horaInicio + " a " + horaFin + " en la fecha solicitada.");
            }

            if (!servicio.getCategoria().equals(empleado.getCategoria())) {
                throw new RuntimeException("La categoría del servicio y del empleado no coinciden para el servicio con ID: " + servicio.getId());
            }

            ServicioCita newServicioCita = new ServicioCita();
            newServicioCita.setCita(newCita);
            newServicioCita.setServicio(servicio);
            newServicioCita.setEmpleado(empleado);
            newServicioCita.setHoraInicio(horaInicio);
            newServicioCita.setHoraFin(horaFin);

            serviciosCita.add(newServicioCita);

            // Actualizar la hora de inicio para el siguiente servicio
            currentHoraInicio = horaFin;
        }

        newCita.setServiciosCita(serviciosCita);

        Cita citaGuardada = citaRepository.save(newCita);

        for (ServicioCita servicioCita : citaGuardada.getServiciosCita()) {
            Detalle detalle = new Detalle();
            detalle.setCita(citaGuardada); // Asocia el detalle a la cita
            detalleRepository.save(detalle); // Guarda el detalle en la base de datos

            servicioCita.setDetalle(detalle); // Asocia el detalle al servicio de la cita
            servicioCita.setCita(citaGuardada); // Asocia el servicio a la cita
            detalle.getServiciosCita().add(servicioCita); // Añade el servicio de la cita al detalle

            // Acumular comisiones por ventas de productos
            VentaProducto ventaProducto = detalle.getVentaProducto();
            if (ventaProducto != null) {
                double comision = ventaProducto.calcularComision();
                actualizarNominaConComision(ventaProducto.getEmpleado(), comision);
            }

            // Acumular comisiones por servicios de cita
            double comision = servicioCita.calcularComision();
            actualizarNominaConComision(servicioCita.getEmpleado(), comision);

            servicioCitaRepository.save(servicioCita); // Guarda el servicio de la cita en la base de datos
        }

        return citaRepository.save(citaGuardada);
    }

    private boolean estaEmpleadoDisponible(Empleado empleado, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        // Verificar si el empleado tiene día libre en la fecha solicitada
        if (empleado.getDiaLibre().equalsIgnoreCase(fecha.getDayOfWeek().name())) {
            return false;
        }

        // Verificar si la hora solicitada coincide con la hora de comida
        if ((horaInicio.isAfter(empleado.getHoraComida()) && horaInicio.isBefore(empleado.getHoraComida().plusHours(1))) ||
                (horaFin.isAfter(empleado.getHoraComida()) && horaFin.isBefore(empleado.getHoraComida().plusHours(1)))) {
            return false;
        }

        // Verificar si las horas solicitadas están fuera del horario laboral
        if (horaInicio.isBefore(empleado.getHoraEntrada()) || horaFin.isAfter(empleado.getHoraSalida())) {
            return false;
        }

        // Verificar si el empleado ya tiene una cita en el intervalo de tiempo solicitado
        List<Cita> citas = citaRepository.findByEmpleadoAndFechaAndHoraBetween(empleado.getId(), fecha, horaInicio, horaFin);
        return citas.isEmpty();
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
        serviciosCitaExistente.forEach(servicioCita -> servicioCita.setCita(citaExistente));

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

    public void actualizarNominaConComision(Empleado empleado, double comision) {
        Nomina nomina = nominaRepository.findByEmpleadoId(empleado.getId());
        if (nomina == null) {
            nomina = new Nomina();
            nomina.setEmpleado(empleado);
            nomina.setSalario(new BigDecimal("15000.00"));
            nomina.setComision(new BigDecimal("0.00"));
            nomina.setSalarioTotal(new BigDecimal("0.00"));
            nomina.setFecha(LocalDate.now());
        }

        BigDecimal nuevaComision = nomina.getComision().add(BigDecimal.valueOf(comision));
        nomina.setComision(nuevaComision);
        nomina.setSalarioTotal(nomina.getSalario().add(nuevaComision));

        nominaRepository.save(nomina);
    }

    public Cita findById(Long citaId) throws ClassNotFoundException {
        return citaRepository.findById(Math.toIntExact(citaId))
                .orElseThrow(() -> new ClassNotFoundException("Cliente no encontrado con ID: " + citaId));
    }

    public TiempoEsperaResponse obtenerTiempoEsperaFormateado(Long id) {
        Duration duration = calcularTiempoEspera(id);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return new TiempoEsperaResponse(hours, minutes, seconds);
    }

    public List<Cita> getCitasByClienteCedula(Long cedula) {
        return citaRepository.findByCliente_Cedula(cedula);
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
