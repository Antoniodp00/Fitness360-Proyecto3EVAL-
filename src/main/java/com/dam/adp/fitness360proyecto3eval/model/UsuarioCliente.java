package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;

public class UsuarioCliente extends Usuario {
    private double altura;


    public UsuarioCliente(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, LocalDateTime createdAt, LocalDateTime updatedAt, double altura) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, createdAt, updatedAt);
        this.altura = altura;
    }

    public UsuarioCliente(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, double altura) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo);
        this.altura = altura;
    }

    public UsuarioCliente(double altura) {
        this.altura = altura;
    }

    public UsuarioCliente() {
    }




    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }


    // Getters y setters...
}
