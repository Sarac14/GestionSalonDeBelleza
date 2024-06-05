package com.example.demo.DTOs;

public class ProductoProveedorDTO {
    private Long productoId;
    private Long proveedorId;

    public ProductoProveedorDTO(Long productoId, Long proveedorId) {
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
