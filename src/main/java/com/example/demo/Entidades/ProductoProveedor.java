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
    @JoinTable(
            name = "producto_proveedor_producto",
            joinColumns = @JoinColumn(name = "producto_proveedor_id"),
            inverseJoinColumns = @JoinColumn(name = "idProducto")
    )
    private List<Producto> productos;

    @ManyToMany(mappedBy = "productosProveedor")
    private List<Proveedor> proveedores;
}
