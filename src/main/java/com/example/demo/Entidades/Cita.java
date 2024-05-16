package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private Date hora;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    private List<ServicioCita> serviciosCita = new ArrayList<>();

    public Cita(Long id, Cliente cliente, Date fecha, Date hora) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public List<ServicioCita> getServiciosCita() {
        return serviciosCita;
    }

    public void setServiciosCita(List<ServicioCita> detallesCita) {
        this.serviciosCita = detallesCita;
    }
}
