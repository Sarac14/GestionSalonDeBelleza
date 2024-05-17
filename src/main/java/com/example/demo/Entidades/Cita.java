package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cita")
public class Cita implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "hora")
    private String hora;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    private List<ServicioCita> serviciosCita = new ArrayList<>();

    public Cita(Long id, Cliente cliente, String fecha, String hora) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<ServicioCita> getServiciosCita() {
        return serviciosCita;
    }

    public void setServiciosCita(List<ServicioCita> detallesCita) {
        this.serviciosCita = detallesCita;
    }
}
