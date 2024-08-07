package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "cita")
public class Cita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    @JsonFormat(pattern = "H:mm")
    private LocalTime hora;

    @Column(name = "vigente")
    private boolean vigente = true;

    @Column(name = "habilitado")
    private boolean habilitado = true;

    @JsonManagedReference
    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioCita> serviciosCita = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cita-detalles")
    private List<Detalle> detalles = new ArrayList<>();

    public Cita(Cliente cliente, LocalDate fecha, LocalTime  hora) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita(Long id, Cliente cliente, LocalDate fecha, LocalTime  hora, List<ServicioCita> serviciosCita) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.serviciosCita = serviciosCita;
    }

    public Cita() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime  getHora() {
        return hora;
    }

    public void setHora(LocalTime  hora) {
        this.hora = hora;
    }

    public List<ServicioCita> getServiciosCita() {
        return serviciosCita;
    }

    public void setServiciosCita(List<ServicioCita> detallesCita) {
        this.serviciosCita = detallesCita;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

}
