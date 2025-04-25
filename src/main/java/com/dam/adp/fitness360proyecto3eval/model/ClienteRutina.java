package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;

public class ClienteRutina {
    private UsuarioCliente cliente;
    private Rutina rutina;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;


    public ClienteRutina() {
    }

    public ClienteRutina(UsuarioCliente cliente, Rutina rutina, LocalDate fechaAsignacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.rutina = rutina;
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

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
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
