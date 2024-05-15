package com.example.demo.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private VentaProducto ventaProducto;

    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idServicioCita")
    private ServicioCita servicioCita;

}
