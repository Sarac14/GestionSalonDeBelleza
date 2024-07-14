package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "servicioCita")
public class ServicioCita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "horaInicio")
    private LocalTime horaInicio;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "horaFin")
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "idServicio")
    private Servicio servicio;
    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    @JsonBackReference("empleado-servicioCitas")
    private Empleado empleado;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    @JsonBackReference("servicio-cita-detalle")
    private Detalle detalle;

    public ServicioCita(Long id, Cita cita, Servicio servicio, Empleado empleado) {
        this.id = id;
        this.cita = cita;
        this.servicio = servicio;
        this.empleado = empleado;
    }

    public ServicioCita() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void calcularHoraServicio(LocalTime horaInicial) {
        Servicio servicio = this.getServicio();
        LocalTime horaFin = horaInicial.plusMinutes(servicio.getDuracion());
        this.setHoraInicio(horaInicial);
        this.setHoraFin(horaFin);
    }

    public double calcularComision() {
        double comisionRate = 0.10; // 10% de comisión por servicio
        return servicio.getPrecioBase() * comisionRate;
    }
}
