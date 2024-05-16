package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.List;

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

    @ManyToMany(mappedBy = "productos")
    private List<ProductoProveedor> proveedores;

    public Producto(Long id, String marca, String nombre, String descripcion, int cantidadStock, float precioCompra, int cantidadMinima, List<ProductoProveedor> proveedores) {
        this.id = id;
        Marca = marca;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioCompra = precioCompra;
        this.cantidadMinima = cantidadMinima;
        this.proveedores = proveedores;
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

    public List<ProductoProveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProductoProveedor> proveedores) {
        this.proveedores = proveedores;
    }
}
