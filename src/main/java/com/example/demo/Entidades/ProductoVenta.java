package com.example.demo.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "productoVenta")
public class ProductoVenta extends Producto{

    @Column(name = "precioVenta")
    private float precioVenta;

    @Column(name = "descuento")
    private float descuento;

    public ProductoVenta(Long id, String marca, String nombre, String descripcion, int cantidadStock, float precioCompra, int cantidadMinima, List<com.example.demo.Entidades.ProductoProveedor> productoProveedor, float precioVenta, float descuento) {
        super(id, marca, nombre, descripcion, cantidadStock, precioCompra, cantidadMinima, productoProveedor);
        this.precioVenta = precioVenta;
        this.descuento = descuento;
    }

    public ProductoVenta() {
        super();
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
}
