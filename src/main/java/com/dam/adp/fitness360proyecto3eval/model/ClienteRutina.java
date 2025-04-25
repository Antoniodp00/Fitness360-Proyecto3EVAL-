package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa la relación entre un cliente y una rutina asignada.
 * Almacena información sobre qué rutina está asignada a qué cliente,
 * junto con las fechas de asignación y finalización, así como información de auditoría.
 */
public class ClienteRutina {
    private UsuarioCliente cliente;
    private Rutina rutina;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de ClienteRutina sin inicializar sus atributos.
     */
    public ClienteRutina() {
    }

    /**
     * Constructor con parámetros para crear una relación entre cliente y rutina sin datos de auditoría.
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

    /**
     * Constructor completo para crear una relación entre cliente y rutina con datos de auditoría.
     * 
     * @param cliente El cliente al que se asigna la rutina
     * @param rutina La rutina asignada al cliente
     * @param fechaAsignacion Fecha en que se asigna la rutina
     * @param fechaFin Fecha en que finaliza la asignación de la rutina
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public ClienteRutina(UsuarioCliente cliente, Rutina rutina, LocalDate fechaAsignacion, LocalDate fechaFin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cliente = cliente;
        this.rutina = rutina;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    /**
     * Devuelve una representación en cadena de texto de la relación cliente-rutina.
     * 
     * @return Cadena de texto con los datos principales de la relación
     */
    @Override
    public String toString() {
        return "ClienteRutina{" +
                "cliente=" + (cliente != null ? "ID: " + cliente.getId() : "null") +
                ", rutina=" + (rutina != null ? "ID: " + rutina.getIdRutina() : "null") +
                ", fechaAsignacion=" + fechaAsignacion +
                ", fechaFin=" + fechaFin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta relación cliente-rutina con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta relación
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteRutina that = (ClienteRutina) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(rutina, that.rutina) &&
                Objects.equals(fechaAsignacion, that.fechaAsignacion);
    }

    /**
     * Calcula el código hash para esta relación cliente-rutina.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cliente, rutina, fechaAsignacion);
    }
}
