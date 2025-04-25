package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteTarifa {
    private UsuarioCliente cliente;
    private Tarifa tarifa;
    private String estado;
    private LocalDate fechaContratacion;
    private LocalDate fechaRenovacion;
    private LocalDate fechaFin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClienteTarifa() {
    }

    public ClienteTarifa(UsuarioCliente cliente, Tarifa tarifa, String estado, LocalDate fechaContratacion, LocalDate fechaRenovacion, LocalDate fechaFin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cliente = cliente;
        this.tarifa = tarifa;
        this.estado = estado;
        this.fechaContratacion = fechaContratacion;
        this.fechaRenovacion = fechaRenovacion;
        this.fechaFin = fechaFin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ClienteTarifa(UsuarioCliente cliente, Tarifa tarifa, String estado, LocalDate fechaContratacion, LocalDate fechaRenovacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.tarifa = tarifa;
        this.estado = estado;
        this.fechaContratacion = fechaContratacion;
        this.fechaRenovacion = fechaRenovacion;
        this.fechaFin = fechaFin;
    }

    // Getters y setters...


    public UsuarioCliente getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
