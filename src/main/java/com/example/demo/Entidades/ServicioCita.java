package com.example.demo.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_cita")
public class ServicioCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

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
}
