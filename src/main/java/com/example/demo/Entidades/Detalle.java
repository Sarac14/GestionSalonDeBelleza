package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "detalle")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private VentaProducto ventaProducto;

    @OneToMany(mappedBy = "detalle")
    private List<ServicioCita> serviciosCita;

    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;

    public Detalle() {
    }
}
