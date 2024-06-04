package com.example.demo.Entidades;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Access(AccessType.FIELD)
public class ProductoProveedorId implements Serializable {
    private Long productoId;
    private Long proveedorId;

    public ProductoProveedorId() {
    }

    public ProductoProveedorId(Long productoId, Long proveedorId) {
        this.productoId = productoId;
        this.proveedorId = proveedorId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }
}
