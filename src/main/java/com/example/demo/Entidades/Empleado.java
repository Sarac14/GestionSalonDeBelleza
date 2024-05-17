package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado extends Persona implements Serializable {
    @Column(name = "genero")
    private String genero;

    @Column(name = "direccion")
    private String direccion;
    @Column(name = "categoria")
    private String categoria;
    @OneToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas;

    public Empleado(int cedula, String nombre, String apellido, String fechaNacimiento, String correoElectronico, int telefono, String genero, String direccion, Nomina nomina, String Categoria) {
        super(cedula, nombre, apellido, fechaNacimiento, correoElectronico, telefono);
        this.genero = genero;
        this.direccion = direccion;
        this.nomina = nomina;
        this.categoria = categoria;
    }

    public Empleado() {

    }

    public Empleado(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

