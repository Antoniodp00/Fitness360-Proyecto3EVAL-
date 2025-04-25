package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;

public class ClienteDieta {
    private UsuarioCliente cliente;
    private Dieta dieta;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;

    public ClienteDieta() {
    }

    public ClienteDieta(UsuarioCliente cliente, Dieta dieta, LocalDate fechaAsignacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.dieta = dieta;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
    }

// Getters y setters...


    public UsuarioCliente getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
