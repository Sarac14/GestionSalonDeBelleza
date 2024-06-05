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
    @JoinColumn(name = "orden_compra_id")
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

    public ProductoOrdenCompra(Long id, OrdenCompra ordenCompra, List<Producto> productos, int cantidad) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.productos = productos;
        this.cantidad = cantidad;
    }

    public ProductoOrdenCompra() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
