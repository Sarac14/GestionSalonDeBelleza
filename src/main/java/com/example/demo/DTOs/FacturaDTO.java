package com.example.demo.DTOs;

import java.util.List;

public class FacturaDTO {

    private Long clienteId;
    private Long empleadoId;
    private Long citaId;
    private float descuento;
    private float impuesto;
    private String metodoPago;
    private List<VentaProductoDTO> ventasProductos;



    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
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

    public List<VentaProductoDTO> getVentasProductos() {
        return ventasProductos;
    }

    public void setVentasProductos(List<VentaProductoDTO> ventasProductos) {
        this.ventasProductos = ventasProductos;
    }
}
