package com.example.demo.Entidades;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
    private Long idClienteCita;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas;

    public Cliente(Long cedula, String nombre, String apellido, String fechaNacimiento, String correoElectronico, int telefono, Long idClienteCita) {
        super(cedula, nombre, apellido, fechaNacimiento, correoElectronico, telefono);
        this.idClienteCita = idClienteCita;
    }

    public Cliente(Long cedula, String nombre, String apellido, String fechaNacimiento, String correoElectronico, int telefono) {
        super(cedula, nombre, apellido, fechaNacimiento, correoElectronico, telefono);
    }

    public Cliente() {
        super();
    }

    public Long getIdClienteCita() {
        return idClienteCita;
    }

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public void setIdClienteCita(Long idClienteCita) {
        this.idClienteCita = idClienteCita;
    }
}


