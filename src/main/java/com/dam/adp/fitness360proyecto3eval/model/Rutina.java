package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;

public class Rutina {
    private int idRutina;
    private String nombre;
    private String descripcion;
    private UsuarioEmpleado empleado;
    private UsuarioCliente cliente;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Rutina() {
    }

    public Rutina(int idRutina, String nombre, String descripcion, UsuarioEmpleado empleado, UsuarioCliente cliente, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.cliente = cliente;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Rutina(int idRutina, String nombre, String descripcion, UsuarioEmpleado empleado, UsuarioCliente cliente) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.cliente = cliente;
    }

    // Getters y setters...


    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(UsuarioEmpleado empleado) {
        this.empleado = empleado;
    }

    public UsuarioCliente getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
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
