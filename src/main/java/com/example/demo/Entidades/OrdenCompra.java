package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordenCompra")
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechaOrden")
    private Date fechaOrden;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @Column(name = "totalOrdenCompra")
    private float totalOrdenCompra;

    @Column(name = "fechaEntrega")
    private Date fechaEntrega;

    @Column(name = "estadoOrdenCompra")
    private String estadoOrdenCompra;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<ProductoOrdenCompra> productos;

    public OrdenCompra(Long id, Date fechaOrden, Proveedor proveedor, float totalOrdenCompra, Date fechaEntrega, String estadoOrdenCompra) {
        this.id = id;
        this.fechaOrden = fechaOrden;
        this.proveedor = proveedor;
        this.totalOrdenCompra = totalOrdenCompra;
        this.fechaEntrega = fechaEntrega;
        this.estadoOrdenCompra = estadoOrdenCompra;
    }

    public OrdenCompra() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public float getTotalOrdenCompra() {
        return totalOrdenCompra;
    }

    public void setTotalOrdenCompra(float totalOrdenCompra) {
        this.totalOrdenCompra = totalOrdenCompra;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstadoOrdenCompra() {
        return estadoOrdenCompra;
    }

    public void setEstadoOrdenCompra(String estadoOrdenCompra) {
        this.estadoOrdenCompra = estadoOrdenCompra;
    }
}
