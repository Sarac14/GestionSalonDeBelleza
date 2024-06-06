package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;
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

    @Column(name = "horaEntrada")
    private LocalTime horaEntrada;

    @Column(name = "horaSalida")
    private LocalTime horaSalida;

    @Column(name = "horaComida")
    private LocalTime horaComida;

    @Column(name = "diaLibre")
    private String diaLibre;

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

    public Empleado(String nombre, String categoria, LocalTime horaEntrada, LocalTime horaSalida, LocalTime horaComida, String diaLibre) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horaComida = horaComida;
        this.diaLibre = diaLibre;
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

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalTime getHoraComida() {
        return horaComida;
    }

    public void setHoraComida(LocalTime horaComida) {
        this.horaComida = horaComida;
    }

    public String getDiaLibre() {
        return diaLibre;
    }

    public void setDiaLibre(String diaLibre) {
        this.diaLibre = diaLibre;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
}

