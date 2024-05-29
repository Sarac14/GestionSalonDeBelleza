package com.example.demo.Entidades;

import jakarta.persistence.*;


@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    public Rol(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rol() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
