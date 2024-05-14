package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "empleado")
public class Empleado extends Persona {
    @Column(name = "genero")
    private String genero;

    @Column(name = "direccion")
    private String direccion;

    @OneToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    public Empleado(Long cedula, String nombre, String apellido, Date fechaNacimiento, String correoElectronico, Integer telefono, String genero, String direccion, Nomina nomina) {
        super(cedula, nombre, apellido, fechaNacimiento, correoElectronico, telefono);
        this.genero = genero;
        this.direccion = direccion;
        this.nomina = nomina;
    }

    public Empleado() {

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
}

