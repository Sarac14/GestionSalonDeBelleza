package com.example.demo.Entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
    private Long idClienteCita;

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


