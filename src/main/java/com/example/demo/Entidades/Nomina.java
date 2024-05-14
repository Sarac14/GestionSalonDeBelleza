package com.example.demo.Entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "nomina")
public class Nomina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @Column(name = "salario")
    private BigDecimal salario;

    @Column(name = "comision")
    private BigDecimal comision;

    @Column(name = "salario_total")
    private BigDecimal salarioTotal;

    @Column(name = "fecha")
    private LocalDate fecha;

    public Nomina(Long id, Empleado empleado, BigDecimal salario, BigDecimal comision, BigDecimal salarioTotal, LocalDate fecha) {
        this.id = id;
        this.empleado = empleado;
        this.salario = salario;
        this.comision = comision;
        this.salarioTotal = salarioTotal;
        this.fecha = fecha;
    }

    public Nomina() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getSalarioTotal() {
        return salarioTotal;
    }

    public void setSalarioTotal(BigDecimal salarioTotal) {
        this.salarioTotal = salarioTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}