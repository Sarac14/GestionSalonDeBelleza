package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "productoProveedor")
public class ProductoProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "idProducto")
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;
}
