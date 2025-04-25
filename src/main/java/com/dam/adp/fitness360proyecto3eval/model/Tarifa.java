package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;

/**
 * Clase que representa una tarifa o plan de precios en el sistema.
 * Contiene información sobre la tarifa como su identificador, nombre, precio,
 * descripción, periodo de validez, el empleado que la creó, y datos de auditoría.
 */
public class Tarifa {
    private int idTarifa;
    private String nombre;
    private double precio;
    private String descripcion;
    private Periodo periodo;
    private UsuarioEmpleado empleado;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Tarifa(int idTarifa, String nombre, double precio, String descripcion, Periodo periodo, UsuarioEmpleado empleado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.empleado = empleado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Tarifa(int idTarifa, String nombre, double precio, String descripcion, Periodo periodo, UsuarioEmpleado empleado) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.empleado = empleado;
    }

    public Tarifa() {
    }

// Getters y setters...


    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(UsuarioEmpleado empleado) {
        this.empleado = empleado;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
