package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "productoOrdenCompra")
public class ProductoOrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "ordenCompra")
    private OrdenCompra ordenCompra;

    @ManyToMany
    @JoinTable(
            name = "producto_orden_compra_producto",
            joinColumns = @JoinColumn(name = "producto_orden_compra_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    @Column(name = "cantidad")
    private int cantidad;
}
