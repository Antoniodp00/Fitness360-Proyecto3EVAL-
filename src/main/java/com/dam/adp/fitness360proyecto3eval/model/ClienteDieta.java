package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Clase que representa la relación entre un cliente y una dieta asignada.
 * Almacena información sobre qué dieta está asignada a qué cliente,
 * junto con las fechas de asignación y finalización, así como información de auditoría.
 */
public class ClienteDieta {
    private UsuarioCliente cliente;
    private Dieta dieta;
    private Date fechaAsignacion;
    private Date fechaFin;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de ClienteDieta sin inicializar sus atributos.
     */
    public ClienteDieta() {
    }

    /**
     * Constructor con parámetros para crear una relación entre cliente y dieta sin datos de auditoría.
     * 
     * @param cliente El cliente al que se asigna la dieta
     * @param dieta La dieta asignada al cliente
     * @param fechaAsignacion Fecha en que se asigna la dieta
     * @param fechaFin Fecha en que finaliza la asignación de la dieta
     */
    public ClienteDieta(UsuarioCliente cliente, Dieta dieta, Date fechaAsignacion, Date fechaFin) {
        this.cliente = cliente;
        this.dieta = dieta;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
    }

    /**
     * Constructor completo para crear una relación entre cliente y dieta con datos de auditoría.
     * 
     * @param cliente El cliente al que se asigna la dieta
     * @param dieta La dieta asignada al cliente
     * @param fechaAsignacion Fecha en que se asigna la dieta
     * @param fechaFin Fecha en que finaliza la asignación de la dieta
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public ClienteDieta(UsuarioCliente cliente, Dieta dieta, Date fechaAsignacion, Date fechaFin, Date createdAt, Date updatedAt) {
        this.cliente = cliente;
        this.dieta = dieta;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * Establece la fecha de asignación de la dieta.
     * 
     * @param fechaAsignacion La nueva fecha de asignación
     */
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * Obtiene la fecha de finalización de la dieta.
     * 
     * @return La fecha en que finaliza la asignación de la dieta
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización de la dieta.
     * 
     * @param fechaFin La nueva fecha de finalización
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene la fecha y hora de creación del registro.
     * 
     * @return La fecha y hora en que se creó el registro
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Establece la fecha y hora de creación del registro.
     * 
     * @param createdAt La nueva fecha y hora de creación
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtiene la fecha y hora de última actualización del registro.
     * 
     * @return La fecha y hora en que se actualizó por última vez el registro
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Establece la fecha y hora de última actualización del registro.
     * 
     * @param updatedAt La nueva fecha y hora de última actualización
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Devuelve una representación en cadena de texto de la relación cliente-dieta.
     * 
     * @return Cadena de texto con los datos principales de la relación
     */
    @Override
    public String toString() {
        return "ClienteDieta{" +
                "cliente=" + (cliente != null ? "ID: " + cliente.getId() : "null") +
                ", dieta=" + (dieta != null ? "ID: " + dieta.getIdDieta() : "null") +
                ", fechaAsignacion=" + fechaAsignacion +
                ", fechaFin=" + fechaFin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta relación cliente-dieta con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta relación
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDieta that = (ClienteDieta) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(dieta, that.dieta) &&
                Objects.equals(fechaAsignacion, that.fechaAsignacion);
    }

    /**
     * Calcula el código hash para esta relación cliente-dieta.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cliente, dieta, fechaAsignacion);
    }
}
