package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Clase que representa a un usuario con rol de empleado en el sistema.
 * Extiende la clase Usuario añadiendo información específica de los empleados
 * como descripción, rol, especialidad (entrenador, dietista o ambos) y estado.
 */
public class UsuarioEmpleado extends Usuario {
    private String descripcion;
    private String rol;
    private Especialidad especialidad;
    private Estado estado;

    /**
     * Constructor completo para crear un empleado con todos los atributos, incluyendo datos de auditoría.
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
     * @param descripcion Descripción o biografía del empleado
     * @param estado Estado del empleado (ACTIVO, INACTIVO, SUSPENDIDO)
     * @param especialidad Especialidad del empleado (ENTRENADOR, DIETISTA, AMBOS)
     * @param rol Rol del empleado en el sistema
     */
    public UsuarioEmpleado(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, LocalDateTime createdAt, LocalDateTime updatedAt, String descripcion, Estado estado, Especialidad especialidad, String rol) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, createdAt, updatedAt);
        this.descripcion = descripcion;
        this.estado = estado;
        this.especialidad = especialidad;
        this.rol = rol;
    }

    /**
     * Constructor para crear un empleado sin datos de auditoría.
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
     * @param descripcion Descripción o biografía del empleado
     * @param rol Rol del empleado en el sistema
     * @param especialidad Especialidad del empleado (ENTRENADOR, DIETISTA, AMBOS)
     * @param estado Estado del empleado (ACTIVO, INACTIVO, SUSPENDIDO)
     */
    public UsuarioEmpleado(int idUsuario, String nombreUsuario, String nombre, String apellidos, String correo, String password, String telefono, Date fechaNacimiento, Sexo sexo, String descripcion, String rol, Especialidad especialidad, Estado estado) {
        super(idUsuario, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo);
        this.descripcion = descripcion;
        this.rol = rol;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de UsuarioEmpleado sin inicializar sus atributos.
     */
    public UsuarioEmpleado() {
    }


    // Getters y setters...
    /**
     * Obtiene la descripción o biografía del empleado.
     * 
     * @return La descripción del empleado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción o biografía del empleado.
     * 
     * @param descripcion La nueva descripción del empleado
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el rol del empleado en el sistema.
     * 
     * @return El rol del empleado
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del empleado en el sistema.
     * 
     * @param rol El nuevo rol del empleado
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la especialidad del empleado.
     * 
     * @return La especialidad del empleado (ENTRENADOR, DIETISTA, AMBOS)
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del empleado.
     * 
     * @param especialidad La nueva especialidad del empleado (ENTRENADOR, DIETISTA, AMBOS)
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el estado del empleado.
     * 
     * @return El estado del empleado (ACTIVO, INACTIVO, SUSPENDIDO)
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del empleado.
     * 
     * @param estado El nuevo estado del empleado (ACTIVO, INACTIVO, SUSPENDIDO)
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }



}
