package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa la relación entre un cliente y una tarifa contratada.
 * Almacena información sobre qué tarifa está asignada a qué cliente,
 * el estado de la contratación, fechas de contratación, renovación y finalización,
 * así como información de auditoría (creación y actualización).
 */
public class ClienteTarifa {
    private UsuarioCliente cliente;
    private Tarifa tarifa;
    private EstadoTarifa estado;
    private LocalDate fechaContratacion;
    private LocalDate fechaRenovacion;
    private LocalDate fechaFin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de ClienteTarifa sin inicializar sus atributos.
     */
    public ClienteTarifa() {
    }

    /**
     * Constructor completo para crear una relación entre cliente y tarifa con datos de auditoría.
     * 
     * @param cliente El cliente que contrata la tarifa
     * @param tarifa La tarifa contratada por el cliente
     * @param estado El estado de la contratación
     * @param fechaContratacion Fecha en que se contrata la tarifa
     * @param fechaRenovacion Fecha en que se renueva la tarifa
     * @param fechaFin Fecha en que finaliza la contratación de la tarifa
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public ClienteTarifa(UsuarioCliente cliente, Tarifa tarifa, EstadoTarifa estado, LocalDate fechaContratacion, LocalDate fechaRenovacion, LocalDate fechaFin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cliente = cliente;
        this.tarifa = tarifa;
        this.estado = estado;
        this.fechaContratacion = fechaContratacion;
        this.fechaRenovacion = fechaRenovacion;
        this.fechaFin = fechaFin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor para crear una relación entre cliente y tarifa sin datos de auditoría.
     * 
     * @param cliente El cliente que contrata la tarifa
     * @param tarifa La tarifa contratada por el cliente
     * @param estado El estado de la contratación
     * @param fechaContratacion Fecha en que se contrata la tarifa
     * @param fechaRenovacion Fecha en que se renueva la tarifa
     * @param fechaFin Fecha en que finaliza la contratación de la tarifa
     */
    public ClienteTarifa(UsuarioCliente cliente, Tarifa tarifa, EstadoTarifa estado, LocalDate fechaContratacion, LocalDate fechaRenovacion, LocalDate fechaFin) {
        this.cliente = cliente;
        this.tarifa = tarifa;
        this.estado = estado;
        this.fechaContratacion = fechaContratacion;
        this.fechaRenovacion = fechaRenovacion;
        this.fechaFin = fechaFin;
    }

    // Getters y setters...

    /**
     * Obtiene el cliente asociado a esta relación.
     * 
     * @return El cliente que contrata la tarifa
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a esta relación.
     * 
     * @param cliente El nuevo cliente que contratará la tarifa
     */
    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la tarifa asociada a esta relación.
     * 
     * @return La tarifa contratada por el cliente
     */
    public Tarifa getTarifa() {
        return tarifa;
    }

    /**
     * Establece la tarifa asociada a esta relación.
     * 
     * @param tarifa La nueva tarifa que contratará el cliente
     */
    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * Obtiene el estado de la contratación.
     * 
     * @return El estado actual de la contratación
     */
    public EstadoTarifa getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la contratación.
     * 
     * @param estado El nuevo estado de la contratación
     */
    public void setEstado(EstadoTarifa estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha de contratación de la tarifa.
     * 
     * @return La fecha en que se contrató la tarifa
     */
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    /**
     * Establece la fecha de contratación de la tarifa.
     * 
     * @param fechaContratacion La nueva fecha de contratación
     */
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    /**
     * Obtiene la fecha de renovación de la tarifa.
     * 
     * @return La fecha en que se renovó o renovará la tarifa
     */
    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    /**
     * Establece la fecha de renovación de la tarifa.
     * 
     * @param fechaRenovacion La nueva fecha de renovación
     */
    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    /**
     * Obtiene la fecha de finalización de la tarifa.
     * 
     * @return La fecha en que finaliza o finalizará la contratación de la tarifa
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización de la tarifa.
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
     * Devuelve una representación en cadena de texto de la relación cliente-tarifa.
     * 
     * @return Cadena de texto con los datos principales de la relación
     */
    @Override
    public String toString() {
        return "ClienteTarifa{" +
                "cliente=" + (cliente != null ? "ID: " + cliente.getId() : "null") +
                ", tarifa=" + (tarifa != null ? "ID: " + tarifa.getIdTarifa() : "null") +
                ", estado=" + estado +
                ", fechaContratacion=" + fechaContratacion +
                ", fechaRenovacion=" + fechaRenovacion +
                ", fechaFin=" + fechaFin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta relación cliente-tarifa con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta relación
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteTarifa that = (ClienteTarifa) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(tarifa, that.tarifa) &&
                Objects.equals(fechaContratacion, that.fechaContratacion);
    }

    /**
     * Calcula el código hash para esta relación cliente-tarifa.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cliente, tarifa, fechaContratacion);
    }
}
