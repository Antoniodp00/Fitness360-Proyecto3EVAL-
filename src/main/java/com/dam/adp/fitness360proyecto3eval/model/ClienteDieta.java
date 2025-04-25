package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;

/**
 * Clase que representa la relación entre un cliente y una dieta asignada.
 * Almacena información sobre qué dieta está asignada a qué cliente,
 * junto con las fechas de asignación y finalización.
 */
public class ClienteDieta {
    private UsuarioCliente cliente;
    private Dieta dieta;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de ClienteDieta sin inicializar sus atributos.
     */
    public ClienteDieta() {
    }

    /**
     * Constructor con parámetros para crear una relación entre cliente y dieta.
     * 
     * @param cliente El cliente al que se asigna la dieta
     * @param dieta La dieta asignada al cliente
     * @param fechaAsignacion Fecha en que se asigna la dieta
     * @param fechaFin Fecha en que finaliza la asignación de la dieta
     */
    public ClienteDieta(UsuarioCliente cliente, Dieta dieta, LocalDate fechaAsignacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.dieta = dieta;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
    }

// Getters y setters...

    /**
     * Obtiene el cliente asociado a esta relación.
     * 
     * @return El cliente al que se asigna la dieta
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a esta relación.
     * 
     * @param cliente El nuevo cliente al que se asignará la dieta
     */
    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la dieta asociada a esta relación.
     * 
     * @return La dieta asignada al cliente
     */
    public Dieta getDieta() {
        return dieta;
    }

    /**
     * Establece la dieta asociada a esta relación.
     * 
     * @param dieta La nueva dieta que se asignará al cliente
     */
    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    /**
     * Obtiene la fecha de asignación de la dieta.
     * 
     * @return La fecha en que se asignó la dieta al cliente
     */
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * Establece la fecha de asignación de la dieta.
     * 
     * @param fechaAsignacion La nueva fecha de asignación
     */
    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * Obtiene la fecha de finalización de la dieta.
     * 
     * @return La fecha en que finaliza la asignación de la dieta
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización de la dieta.
     * 
     * @param fechaFin La nueva fecha de finalización
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
