package com.example.demo.Entidades;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
    private Long idClienteCita;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas;

    public Cliente(Long cedula, String nombre, String apellido, Date fechaNacimiento, String correoElectronico, Integer telefono, Long idClienteCita) {
        super(cedula, nombre, apellido, fechaNacimiento, correoElectronico, telefono);
        this.idClienteCita = idClienteCita;
    }

    public Cliente() {
        super();
    }

    public Long getIdClienteCita() {
        return idClienteCita;
    }

    public void setIdClienteCita(Long idClienteCita) {
        this.idClienteCita = idClienteCita;
    }
}


