package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa una tarifa o plan de precios en el sistema.
 * Contiene información sobre la tarifa como su identificador, nombre, precio,
 * descripción, periodo de validez, el empleado que la creó, y datos de auditoría.
 */
public class Tarifa {
    private int idTarifa;
    private String nombre;
    private double precio;
    private String descripcion;
    private Periodo periodo;
    private UsuarioEmpleado creador;
    private List<ClienteTarifa> clientesAsignados;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Tarifa sin inicializar sus atributos.
     */
    public Tarifa() {
    }

    /**
     * Constructor con parámetros básicos para crear una tarifa.
     * 
     * @param idTarifa Identificador único de la tarifa
     * @param nombre Nombre de la tarifa
     * @param precio Precio de la tarifa
     * @param descripcion Descripción detallada de la tarifa
     * @param periodo Periodo de validez de la tarifa
     */
    public Tarifa(int idTarifa, String nombre, double precio, String descripcion, Periodo periodo) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.periodo = periodo;
    }

    /**
     * Constructor completo para crear una tarifa con todos los atributos.
     * 
     * @param idTarifa Identificador único de la tarifa
     * @param nombre Nombre de la tarifa
     * @param precio Precio de la tarifa
     * @param descripcion Descripción detallada de la tarifa
     * @param periodo Periodo de validez de la tarifa
     * @param creador Empleado que creó la tarifa
     * @param clientesAsignados Lista de clientes con esta tarifa asignada
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Tarifa(int idTarifa, String nombre, double precio, String descripcion, Periodo periodo, 
                 UsuarioEmpleado creador, List<ClienteTarifa> clientesAsignados, Date createdAt, Date updatedAt) {
        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.creador = creador;
        this.clientesAsignados = clientesAsignados;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Obtiene el identificador único de la tarifa.
     * 
     * @return El identificador de la tarifa
     */
    public int getIdTarifa() {
        return idTarifa;
    }

    /**
     * Establece el identificador único de la tarifa.
     * 
     * @param idTarifa El nuevo identificador de la tarifa
     */
    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    /**
     * Obtiene el nombre de la tarifa.
     * 
     * @return El nombre de la tarifa
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la tarifa.
     * 
     * @param nombre El nuevo nombre de la tarifa
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio de la tarifa.
     * 
     * @return El precio de la tarifa
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la tarifa.
     * 
     * @param precio El nuevo precio de la tarifa
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la descripción de la tarifa.
     * 
     * @return La descripción de la tarifa
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la tarifa.
     * 
     * @param descripcion La nueva descripción de la tarifa
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el periodo de validez de la tarifa.
     * 
     * @return El periodo de la tarifa
     */
    public Periodo getPeriodo() {
        return periodo;
    }

    /**
     * Establece el periodo de validez de la tarifa.
     * 
     * @param periodo El nuevo periodo de la tarifa
     */
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    /**
     * Obtiene el empleado que creó la tarifa.
     * 
     * @return El creador de la tarifa
     */
    public UsuarioEmpleado getCreador() {
        return creador;
    }

    /**
     * Establece el empleado que creó la tarifa.
     * 
     * @param creador El nuevo creador de la tarifa
     */
    public void setCreador(UsuarioEmpleado creador) {
        this.creador = creador;
    }

    /**
     * Obtiene la lista de clientes con esta tarifa asignada.
     * 
     * @return La lista de clientes asignados
     */
    public List<ClienteTarifa> getClientesAsignados() {
        return clientesAsignados;
    }

    /**
     * Establece la lista de clientes con esta tarifa asignada.
     * 
     * @param clientesAsignados La nueva lista de clientes asignados
     */
    public void setClientesAsignados(List<ClienteTarifa> clientesAsignados) {
        this.clientesAsignados = clientesAsignados;
    }

    /**
     * Obtiene la fecha y hora de creación del registro.
     * 
     * @return La fecha y hora de creación
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
     * @return La fecha y hora de última actualización
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
     * Devuelve una representación en cadena de texto de la tarifa.
     * 
     * @return Cadena de texto con los datos principales de la tarifa
     */
    @Override
    public String toString() {
        return "Tarifa{" +
                "idTarifa=" + idTarifa +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", periodo=" + periodo +
                ", creador=" + (creador != null ? "presente" : "null") +
                ", clientesAsignados=" + (clientesAsignados != null ? clientesAsignados.size() : 0) + " clientes" +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta tarifa con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta tarifa
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarifa tarifa = (Tarifa) o;
        return idTarifa == tarifa.idTarifa &&
                Double.compare(tarifa.precio, precio) == 0 &&
                Objects.equals(nombre, tarifa.nombre) &&
                Objects.equals(descripcion, tarifa.descripcion) &&
                periodo == tarifa.periodo;
    }

    /**
     * Calcula el código hash para esta tarifa.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTarifa, nombre, precio, descripcion, periodo);
    }
}
