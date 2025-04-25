package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Clase que representa a un usuario con rol de cliente en el sistema.
 * Extiende la clase Usuario añadiendo información específica de los clientes
 * como la altura, necesaria para cálculos relacionados con la salud y el fitness.
 */
public class UsuarioCliente extends Usuario {
    private double altura;


    /**
     * Constructor completo para crear un cliente con todos los atributos, incluyendo datos de auditoría.
     * 
     * @param idUsuario Identificador único del usuario
     * @param nombreUsuario Nombre de usuario para el login
     * @param nombre Nombre real del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @param telefono Número de teléfono del usuario
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param sexo Sexo del usuario (M, F, O)
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     * @param altura Altura del cliente en metros
     */
    public UsuarioCliente(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,LocalDateTime createdAt, LocalDateTime updatedAt, double altura) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado,createdAt, updatedAt);
        this.altura = altura;
    }

    /**
     * Constructor para crear un cliente sin datos de auditoría.
     * 
     * @param idUsuario Identificador único del usuario
     * @param nombreUsuario Nombre de usuario para el login
     * @param nombre Nombre real del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @param telefono Número de teléfono del usuario
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param sexo Sexo del usuario (M, F, O)
     * @param altura Altura del cliente en metros
     */
    public UsuarioCliente(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo,Estado estado, double altura) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo,estado);
        this.altura = altura;
    }

    /**
     * Constructor para crear un cliente especificando solo la altura.
     * 
     * @param altura Altura del cliente en metros
     */
    public UsuarioCliente(double altura) {
        this.altura = altura;
    }

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de UsuarioCliente sin inicializar sus atributos.
     */
    public UsuarioCliente() {
    }




    /**
     * Obtiene la altura del cliente.
     * 
     * @return La altura del cliente en metros
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Establece la altura del cliente.
     * 
     * @param altura La nueva altura del cliente en metros
     */
    public void setAltura(double altura) {
        this.altura = altura;
    }


    // Getters y setters...


    @Override
    public String toString() {
        return "UsuarioCliente{" +
                super.toString() +
                "altura=" + altura +
                '}';
    }
}
