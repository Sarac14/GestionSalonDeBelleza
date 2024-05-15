package com.example.demo.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ventaProducto")
public class VentaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idDetalle")
    private Detalle detalle;

    @OneToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;
}
