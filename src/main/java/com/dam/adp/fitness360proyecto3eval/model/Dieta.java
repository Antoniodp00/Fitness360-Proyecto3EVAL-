package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;

public class Dieta {
    private int idDieta;
    private String nombre;
    private String descripcion;
    private String archivo;
    private UsuarioEmpleado empleado;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Dieta() {
    }

    public Dieta(int idDieta, String nombre, String descripcion, String archivo, UsuarioEmpleado empleado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.empleado = empleado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Dieta(int idDieta, String nombre, String descripcion, String archivo, UsuarioEmpleado empleado) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.empleado = empleado;
    }

    // Getters y setters...


    public int getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(int idDieta) {
        this.idDieta = idDieta;
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

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(UsuarioEmpleado empleado) {
        this.empleado = empleado;
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
