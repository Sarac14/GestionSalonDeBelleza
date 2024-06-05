package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nombreEmpresa")
    private String nombreEmpresa;

    @Column(name= "nombreContacto")
    private String nombreContacto;

    @Column(name= "telefono")
    private String telefono;

    @Column(name= "correoElectronico")
    private String correoElectronico;

    @Column(name= "direccion")
    private String direccion;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoProveedor> productosProveedor;

    public Proveedor() {
    }

    public Proveedor(String nombreEmpresa, String nombreContacto, String telefono, String correoElectronico, String direccion, List<ProductoProveedor> productosProveedor) {
        this.nombreEmpresa = nombreEmpresa;
        this.nombreContacto = nombreContacto;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.productosProveedor = productosProveedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<ProductoProveedor> getProductosProveedor() {
        return productosProveedor;
    }

    public void setProductosProveedor(List<ProductoProveedor> productosProveedor) {
        this.productosProveedor = productosProveedor;
    }
}
