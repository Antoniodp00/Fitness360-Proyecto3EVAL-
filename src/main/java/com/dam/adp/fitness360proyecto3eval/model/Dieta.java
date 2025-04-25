package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;

/**
 * Clase que representa un plan de dieta en el sistema.
 * Contiene información sobre la dieta como su identificador, nombre, descripción,
 * archivo asociado, el empleado que la creó, y datos de auditoría.
 */
public class Dieta {
    private int idDieta;
    private String nombre;
    private String descripcion;
    private String archivo;
    private UsuarioEmpleado empleado;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Dieta sin inicializar sus atributos.
     */
    public Dieta() {
    }

    /**
     * Constructor completo para crear una dieta con todos los atributos, incluyendo datos de auditoría.
     * 
     * @param idDieta Identificador único de la dieta
     * @param nombre Nombre de la dieta
     * @param descripcion Descripción detallada de la dieta
     * @param archivo Ruta o nombre del archivo que contiene la dieta
     * @param empleado Empleado que creó la dieta
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Dieta(int idDieta, String nombre, String descripcion, String archivo, UsuarioEmpleado empleado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.empleado = empleado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor para crear una dieta sin datos de auditoría.
     * 
     * @param idDieta Identificador único de la dieta
     * @param nombre Nombre de la dieta
     * @param descripcion Descripción detallada de la dieta
     * @param archivo Ruta o nombre del archivo que contiene la dieta
     * @param empleado Empleado que creó la dieta
     */
    public Dieta(int idDieta, String nombre, String descripcion, String archivo, UsuarioEmpleado empleado) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.empleado = empleado;
    }

    // Getters y setters...

    /**
     * Obtiene el identificador único de la dieta.
     * 
     * @return El identificador de la dieta
     */
    public int getIdDieta() {
        return idDieta;
    }

    /**
     * Establece el identificador único de la dieta.
     * 
     * @param idDieta El nuevo identificador de la dieta
     */
    public void setIdDieta(int idDieta) {
        this.idDieta = idDieta;
    }

    /**
     * Obtiene el nombre de la dieta.
     * 
     * @return El nombre de la dieta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la dieta.
     * 
     * @param nombre El nuevo nombre de la dieta
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción detallada de la dieta.
     * 
     * @return La descripción de la dieta
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción detallada de la dieta.
     * 
     * @param descripcion La nueva descripción de la dieta
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la ruta o nombre del archivo que contiene la dieta.
     * 
     * @return El archivo de la dieta
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Establece la ruta o nombre del archivo que contiene la dieta.
     * 
     * @param archivo El nuevo archivo de la dieta
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    /**
     * Obtiene el empleado que creó la dieta.
     * 
     * @return El empleado creador de la dieta
     */
    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    /**
     * Establece el empleado que creó la dieta.
     * 
     * @param empleado El nuevo empleado creador de la dieta
     */
    public void setEmpleado(UsuarioEmpleado empleado) {
        this.empleado = empleado;
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
