package com.example.demo.DTOs;

public class VentaProductoDTO {
    private Long productoVentaId;
    private int cantidad;

    public Long getProductoVentaId() {
        return productoVentaId;
    }

    public void setProductoVentaId(Long productoVentaId) {
        this.productoVentaId = productoVentaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
