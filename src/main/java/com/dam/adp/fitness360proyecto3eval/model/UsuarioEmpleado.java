package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UsuarioEmpleado extends Usuario {
    private String descripcion;
    private String rol;
    private Especialidad especialidad;
    private Estado estado;

    public UsuarioEmpleado(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, LocalDateTime createdAt, LocalDateTime updatedAt, String descripcion, Estado estado, Especialidad especialidad, String rol) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, createdAt, updatedAt);
        this.descripcion = descripcion;
        this.estado = estado;
        this.especialidad = especialidad;
        this.rol = rol;
    }

    public UsuarioEmpleado(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, String descripcion, String rol, Especialidad especialidad, Estado estado) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo);
        this.descripcion = descripcion;
        this.rol = rol;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public UsuarioEmpleado() {
    }


    // Getters y setters...
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }



}