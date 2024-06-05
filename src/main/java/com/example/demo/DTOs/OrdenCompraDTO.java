package com.example.demo.DTOs;

import java.util.Date;
import java.util.List;

public class OrdenCompraDTO {
    private Long proveedorId;
    private List<Long> productosIds;
    private List<Integer> cantidades;
    private Date fechaEntrega;

    public OrdenCompraDTO(Long proveedorId, List<Long> productosIds, List<Integer> cantidades, Date fechaEntrega) {
        this.proveedorId = proveedorId;
        this.productosIds = productosIds;
        this.cantidades = cantidades;
        this.fechaEntrega = fechaEntrega;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public List<Long> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(List<Long> productosIds) {
        this.productosIds = productosIds;
    }

    public List<Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<Integer> cantidades) {
        this.cantidades = cantidades;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
