package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productoVenta")
public class ProductoVenta extends Producto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "precioVenta")
    private float precioVenta;

    @Column(name = "descuento")
    private float descuento;

    @JsonIgnore
    @OneToMany(mappedBy = "productoVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VentaProducto> ventasProducto = new ArrayList<>();

    public ProductoVenta(String marca, String nombre, String descripcion, int cantidadStock,int cantidadPedidoAutomatico, float precioCompra, int cantidadMinima, Set<ProductoProveedor> proveedores, String unidadMedida, int cantidadPorUsos, int cantidadDeUsos) {
        super(marca, nombre, descripcion, cantidadStock, precioCompra, cantidadMinima, cantidadPedidoAutomatico, proveedores);
        this.precioVenta = precioVenta;
        this.descuento = descuento;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public List<VentaProducto> getVentasProducto() {
        return ventasProducto;
    }

    public void setVentasProducto(List<VentaProducto> ventasProducto) {
        this.ventasProducto = ventasProducto;
    }
}
