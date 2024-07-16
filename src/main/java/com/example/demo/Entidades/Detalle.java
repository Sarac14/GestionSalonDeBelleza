package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalle")
public class Detalle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private VentaProducto ventaProducto;

    @JsonIgnore
    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioCita> serviciosCita = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_cita")
    @JsonBackReference("cita-detalles")
    private Cita cita;

    @OneToOne(mappedBy = "detalle")
    @JsonBackReference("detalle-factura")
    private Factura factura;

    public Detalle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VentaProducto getVentaProducto() {
        return ventaProducto;
    }

    public void setVentaProducto(VentaProducto ventaProducto) {
        this.ventaProducto = ventaProducto;
    }

    public List<ServicioCita> getServiciosCita() {
        return serviciosCita;
    }

    public void setServiciosCita(List<ServicioCita> serviciosCita) {
        this.serviciosCita = serviciosCita;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

}
