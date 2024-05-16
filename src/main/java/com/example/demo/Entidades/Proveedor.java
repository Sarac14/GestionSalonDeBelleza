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

    @ManyToMany
    @JoinTable(
            name = "producto_proveedor_proveedor",
            joinColumns = @JoinColumn(name = "proveedor_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_proveedor_id")
    )
    private List<ProductoProveedor> productosProveedor;
}
