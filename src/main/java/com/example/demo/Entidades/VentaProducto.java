package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ventaProducto")
public class VentaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "detalle_id")
    @JsonBackReference("venta-producto-detalle")
    private Detalle detalle;

    @OneToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idProductoVenta")
    private ProductoVenta productoVenta;

    @Column(name = "cantidad")
    private int cantidad;


    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    public VentaProducto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ProductoVenta getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVenta productoVenta) {
        this.productoVenta = productoVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularComision() {
        double comisionRate = 0.05; // 5% de comisión por venta de producto
        return productoVenta.getPrecioVenta() * comisionRate;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
