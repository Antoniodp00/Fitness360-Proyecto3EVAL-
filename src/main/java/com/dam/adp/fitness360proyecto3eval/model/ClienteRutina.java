package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;

/**
 * Clase que representa la relación entre un cliente y una rutina asignada.
 * Almacena información sobre qué rutina está asignada a qué cliente,
 * junto con las fechas de asignación y finalización.
 */
public class ClienteRutina {
    private UsuarioCliente cliente;
    private Rutina rutina;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;


    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de ClienteRutina sin inicializar sus atributos.
     */
    public ClienteRutina() {
    }

    /**
     * Constructor con parámetros para crear una relación entre cliente y rutina.
     * 
     * @param cliente El cliente al que se asigna la rutina
     * @param rutina La rutina asignada al cliente
     * @param fechaAsignacion Fecha en que se asigna la rutina
     * @param fechaFin Fecha en que finaliza la asignación de la rutina
     */
    public ClienteRutina(UsuarioCliente cliente, Rutina rutina, LocalDate fechaAsignacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.rutina = rutina;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
    }




    // Getters y setters...

    /**
     * Obtiene el cliente asociado a esta relación.
     * 
     * @return El cliente al que se asigna la rutina
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a esta relación.
     * 
     * @param cliente El nuevo cliente al que se asignará la rutina
     */
    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la rutina asociada a esta relación.
     * 
     * @return La rutina asignada al cliente
     */
    public Rutina getRutina() {
        return rutina;
    }

    /**
     * Establece la rutina asociada a esta relación.
     * 
     * @param rutina La nueva rutina que se asignará al cliente
     */
    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    /**
     * Obtiene la fecha de asignación de la rutina.
     * 
     * @return La fecha en que se asignó la rutina al cliente
     */
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * Establece la fecha de asignación de la rutina.
     * 
     * @param fechaAsignacion La nueva fecha de asignación
     */
    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * Obtiene la fecha de finalización de la rutina.
     * 
     * @return La fecha en que finaliza la asignación de la rutina
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización de la rutina.
     * 
     * @param fechaFin La nueva fecha de finalización
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
