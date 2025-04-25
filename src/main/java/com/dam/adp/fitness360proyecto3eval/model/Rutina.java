package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;

/**
 * Clase que representa una rutina de ejercicios en el sistema.
 * Contiene información sobre la rutina como su identificador, nombre, descripción,
 * el empleado que la creó, el cliente al que está asignada, y datos de auditoría.
 */
public class Rutina {
    private int idRutina;
    private String nombre;
    private String descripcion;
    private UsuarioEmpleado empleado;
    private UsuarioCliente cliente;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Rutina sin inicializar sus atributos.
     */
    public Rutina() {
    }

    /**
     * Constructor completo para crear una rutina con todos los atributos, incluyendo datos de auditoría.
     * 
     * @param idRutina Identificador único de la rutina
     * @param nombre Nombre de la rutina
     * @param descripcion Descripción detallada de la rutina
     * @param empleado Empleado que creó la rutina
     * @param cliente Cliente al que está asignada la rutina
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Rutina(int idRutina, String nombre, String descripcion, UsuarioEmpleado empleado, UsuarioCliente cliente, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.cliente = cliente;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor para crear una rutina sin datos de auditoría.
     * 
     * @param idRutina Identificador único de la rutina
     * @param nombre Nombre de la rutina
     * @param descripcion Descripción detallada de la rutina
     * @param empleado Empleado que creó la rutina
     * @param cliente Cliente al que está asignada la rutina
     */
    public Rutina(int idRutina, String nombre, String descripcion, UsuarioEmpleado empleado, UsuarioCliente cliente) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.cliente = cliente;
    }

    // Getters y setters...

    /**
     * Obtiene el identificador único de la rutina.
     * 
     * @return El identificador de la rutina
     */
    public int getIdRutina() {
        return idRutina;
    }

    /**
     * Establece el identificador único de la rutina.
     * 
     * @param idRutina El nuevo identificador de la rutina
     */
    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    /**
     * Obtiene el nombre de la rutina.
     * 
     * @return El nombre de la rutina
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la rutina.
     * 
     * @param nombre El nuevo nombre de la rutina
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción detallada de la rutina.
     * 
     * @return La descripción de la rutina
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción detallada de la rutina.
     * 
     * @param descripcion La nueva descripción de la rutina
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el empleado que creó la rutina.
     * 
     * @return El empleado creador de la rutina
     */
    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    /**
     * Establece el empleado que creó la rutina.
     * 
     * @param empleado El nuevo empleado creador de la rutina
     */
    public void setEmpleado(UsuarioEmpleado empleado) {
        this.empleado = empleado;
    }

    /**
     * Obtiene el cliente al que está asignada la rutina.
     * 
     * @return El cliente asignado a la rutina
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente al que está asignada la rutina.
     * 
     * @param cliente El nuevo cliente asignado a la rutina
     */
    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la fecha y hora de creación del registro.
     * 
     * @return La fecha y hora en que se creó el registro
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Establece la fecha y hora de creación del registro.
     * 
     * @param createdAt La nueva fecha y hora de creación
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtiene la fecha y hora de última actualización del registro.
     * 
     * @return La fecha y hora en que se actualizó por última vez el registro
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Establece la fecha y hora de última actualización del registro.
     * 
     * @param updatedAt La nueva fecha y hora de última actualización
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
