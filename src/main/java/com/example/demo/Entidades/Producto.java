package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
@Inheritance(strategy = InheritanceType.JOINED)
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "marca")
    private String Marca;

    @Column(name= "nombre")
    private String nombre;

    @Column(name= "descripcion")
    private String descripcion;

    @Column(name= "cantidadStock")
    private int cantidadStock;

    @Column(name= "precioCompra")
    private float precioCompra;

    @Column(name= "cantidadMinima")
    private int cantidadMinima;

    @Column(name = "cantidad_pedido_automatico")
    private int cantidadPedidoAutomatico;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ProductoProveedor> proveedoresProducto = new HashSet<>();

    public Producto(String marca, String nombre, String descripcion, int cantidadStock, float precioCompra, int cantidadMinima, int cantidadPedidoAutomatico, Set<ProductoProveedor> proveedoresProducto) {
        Marca = marca;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioCompra = precioCompra;
        this.cantidadMinima = cantidadMinima;
        this.cantidadPedidoAutomatico = cantidadPedidoAutomatico;
        this.proveedoresProducto = proveedoresProducto;
    }

    public Producto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public Set<ProductoProveedor> getProveedoresProducto() {
        return proveedoresProducto;
    }

    public void setProveedoresProducto(Set<ProductoProveedor> proveedores) {
        this.proveedoresProducto = proveedores;
    }

    public int getCantidadPedidoAutomatico() {
        return cantidadPedidoAutomatico;
    }

    public void setCantidadPedidoAutomatico(int cantidadPedidoAutomatico) {
        this.cantidadPedidoAutomatico = cantidadPedidoAutomatico;
    }
}
