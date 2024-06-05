package com.example.demo.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productoVenta")
public class ProductoVenta extends Producto{

    @Column(name = "precioVenta")
    private float precioVenta;

    @Column(name = "descuento")
    private float descuento;

    public ProductoVenta(String marca, String nombre, String descripcion, int cantidadStock,int cantidadPedidoAutomatico, float precioCompra, int cantidadMinima, Set<ProductoProveedor> proveedores, String unidadMedida, int cantidadPorUsos, int cantidadDeUsos) {
        super(marca, nombre, descripcion, cantidadStock, precioCompra, cantidadMinima, cantidadPedidoAutomatico, proveedores);
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
