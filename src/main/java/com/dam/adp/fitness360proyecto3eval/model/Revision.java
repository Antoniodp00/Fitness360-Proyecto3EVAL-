package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Revision {
    private int idRevision;
    private LocalDate fecha;
    private double peso;
    private double grasa;
    private double musculo;
    private double mPecho;
    private double mCintura;
    private double mCadera;
    private String observaciones;
    private String imagen;
    private UsuarioCliente cliente;
    private UsuarioEmpleado empleado;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Revision() {
    }

    public Revision(int idRevision, LocalDate fecha, double peso, double grasa, double musculo, double mPecho, double mCintura, double mCadera, String observaciones, String imagen, UsuarioCliente cliente, UsuarioEmpleado empleado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idRevision = idRevision;
        this.fecha = fecha;
        this.peso = peso;
        this.grasa = grasa;
        this.musculo = musculo;
        this.mPecho = mPecho;
        this.mCintura = mCintura;
        this.mCadera = mCadera;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.cliente = cliente;
        this.empleado = empleado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Revision(int idRevision, LocalDate fecha, double peso, double grasa, double musculo, double mPecho, double mCintura, double mCadera, String observaciones, String imagen, UsuarioCliente cliente, UsuarioEmpleado empleado) {
        this.idRevision = idRevision;
        this.fecha = fecha;
        this.peso = peso;
        this.grasa = grasa;
        this.musculo = musculo;
        this.mPecho = mPecho;
        this.mCintura = mCintura;
        this.mCadera = mCadera;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    // Getters y setters...


    public int getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(int idRevision) {
        this.idRevision = idRevision;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getGrasa() {
        return grasa;
    }

    public void setGrasa(double grasa) {
        this.grasa = grasa;
    }

    public double getMusculo() {
        return musculo;
    }

    public void setMusculo(double musculo) {
        this.musculo = musculo;
    }

    public double getmPecho() {
        return mPecho;
    }

    public void setmPecho(double mPecho) {
        this.mPecho = mPecho;
    }

    public double getmCintura() {
        return mCintura;
    }

    public void setmCintura(double mCintura) {
        this.mCintura = mCintura;
    }

    public double getmCadera() {
        return mCadera;
    }

    public void setmCadera(double mCadera) {
        this.mCadera = mCadera;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public UsuarioCliente getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
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
