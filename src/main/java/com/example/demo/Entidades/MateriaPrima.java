package com.example.demo.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "materiaPrima")
public class MateriaPrima extends Producto{

    @Column(name = "unidadMedida")
    private String unidadMedida;

    @Column(name = "cantidadPorUsos")
    private int cantidadPorUsos;

    @Column(name = "cantidadDeUsos")
    private int cantidadDeUsos;

    public MateriaPrima(Long id, String marca, String nombre, String descripcion, int cantidadStock, float precioCompra, int cantidadMinima, List<ProductoProveedor> proveedores, String unidadMedida, int cantidadPorUsos, int cantidadDeUsos) {
        super(id, marca, nombre, descripcion, cantidadStock, precioCompra, cantidadMinima, proveedores);
        this.unidadMedida = unidadMedida;
        this.cantidadPorUsos = cantidadPorUsos;
        this.cantidadDeUsos = cantidadDeUsos;
    }

    public MateriaPrima(String unidadMedida, int cantidadPorUsos, int cantidadDeUsos) {
        this.unidadMedida = unidadMedida;
        this.cantidadPorUsos = cantidadPorUsos;
        this.cantidadDeUsos = cantidadDeUsos;
    }

    public MateriaPrima() {

    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getCantidadPorUsos() {
        return cantidadPorUsos;
    }

    public void setCantidadPorUsos(int cantidadPorUsos) {
        this.cantidadPorUsos = cantidadPorUsos;
    }

    public int getCantidadDeUsos() {
        return cantidadDeUsos;
    }

    public void setCantidadDeUsos(int cantidadDeUsos) {
        this.cantidadDeUsos = cantidadDeUsos;
    }
}

