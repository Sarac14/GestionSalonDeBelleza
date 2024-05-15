package com.example.demo.Entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

    @OneToOne
    @JoinColumn(name = "detalle_id", unique = true)
    private Detalle detalle;

    @JoinColumn(name = "fecha")
    private Date fechaEmision;

    @JoinColumn(name = "descuento")
    private float descuento;

    @JoinColumn(name = "impuesto")
    private float impuesto;

    @JoinColumn(name = "metodoPago")
    private String metodoPago;

    @JoinColumn(name = "cambio")
    private float cambio;

    @JoinColumn(name = "totalPagar")
    private float totalPagar;

    @JoinColumn(name = "subTotal")
    private float subTotal;

    public Factura(Long id, Cliente cliente, Empleado empleado, Detalle detalle, Date fechaEmision, float descuento, float impuesto, String metodoPago, float cambio, float totalPagar, float subTotal) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.detalle = detalle;
        this.fechaEmision = fechaEmision;
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.metodoPago = metodoPago;
        this.cambio = cambio;
        this.totalPagar = totalPagar;
        this.subTotal = subTotal;
    }

    public Factura() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public float getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(float totalPagar) {
        this.totalPagar = totalPagar;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
}

