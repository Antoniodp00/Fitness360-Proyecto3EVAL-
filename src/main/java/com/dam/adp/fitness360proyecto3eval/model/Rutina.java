package com.dam.adp.fitness360proyecto3eval.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa una rutina de ejercicios en el sistema.
 * Contiene información sobre la rutina como su identificador, nombre, descripción,
 * el empleado que la creó, el cliente al que está asignada, y datos de auditoría.
 */
public class Rutina {
    private int idRutina;
    private String nombre;
    private String descripcion;
    private UsuarioCliente creadorCliente;
    private UsuarioEmpleado creadorEmpleado;
    private List<ClienteRutina> clientesAsignados;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Rutina sin inicializar sus atributos.
     */
    public Rutina() {
    }

    /**
     * Constructor con parámetros básicos para crear una rutina.
     * 
     * @param idRutina Identificador único de la rutina
     * @param nombre Nombre de la rutina
     * @param descripcion Descripción detallada de la rutina
     */
    public Rutina(int idRutina, String nombre, String descripcion) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Constructor con parámetros para crear una rutina con cliente y creador.
     * 
     * @param idRutina Identificador único de la rutina
     * @param nombre Nombre de la rutina
     * @param descripcion Descripción detallada de la rutina
     * @param creadorCliente Cliente al que está asignada la rutina
     * @param creadorEmpleado Empleado que creó la rutina
     */
    public Rutina(int idRutina, String nombre, String descripcion, UsuarioCliente creadorCliente, UsuarioEmpleado creadorEmpleado) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadorCliente = creadorCliente;
        this.creadorEmpleado = creadorEmpleado;
    }

    /**
     * Constructor completo para crear una rutina con todos los atributos.
     * 
     * @param idRutina Identificador único de la rutina
     * @param nombre Nombre de la rutina
     * @param descripcion Descripción detallada de la rutina
     * @param creadorCliente Cliente al que está asignada la rutina
     * @param creadorEmpleado Empleado que creó la rutina
     * @param clientesAsignados Lista de clientes con esta rutina asignada
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Rutina(int idRutina, String nombre, String descripcion, UsuarioCliente creadorCliente,
                  UsuarioEmpleado creadorEmpleado, List<ClienteRutina> clientesAsignados, Date createdAt, Date updatedAt) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadorCliente = creadorCliente;
        this.creadorEmpleado = creadorEmpleado;
        this.clientesAsignados = clientesAsignados;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
     * Obtiene la descripción de la rutina.
     * 
     * @return La descripción de la rutina
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la rutina.
     * 
     * @param descripcion La nueva descripción de la rutina
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el cliente al que está asignada la rutina.
     * 
     * @return El cliente de la rutina
     */
    public UsuarioCliente getCreadorCliente() {
        return creadorCliente;
    }

    /**
     * Establece el cliente al que está asignada la rutina.
     * 
     * @param creadorCliente El nuevo cliente de la rutina
     */
    public void setCreadorCliente(UsuarioCliente creadorCliente) {
        this.creadorCliente = creadorCliente;
    }

    /**
     * Obtiene el empleado que creó la rutina.
     * 
     * @return El creador de la rutina
     */
    public UsuarioEmpleado getCreadorEmpleado() {
        return creadorEmpleado;
    }

    /**
     * Establece el empleado que creó la rutina.
     * 
     * @param creadorEmpleado El nuevo creador de la rutina
     */
    public void setCreadorEmpleado(UsuarioEmpleado creadorEmpleado) {
        this.creadorEmpleado = creadorEmpleado;
    }

    /**
     * Obtiene la lista de clientes con esta rutina asignada.
     * 
     * @return La lista de clientes asignados
     */
    public List<ClienteRutina> getClientesAsignados() {
        return clientesAsignados;
    }

    /**
     * Establece la lista de clientes con esta rutina asignada.
     * 
     * @param clientesAsignados La nueva lista de clientes asignados
     */
    public void setClientesAsignados(List<ClienteRutina> clientesAsignados) {
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
     * Devuelve una representación en cadena de texto de la rutina.
     * 
     * @return Cadena de texto con los datos principales de la rutina
     */
    @Override
    public String toString() {
        return "Rutina{" +
                "idRutina=" + idRutina +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cliente=" + (creadorCliente != null ? "presente" : "null") +
                ", creador=" + (creadorEmpleado != null ? "presente" : "null") +
                ", clientesAsignados=" + (clientesAsignados != null ? clientesAsignados.size() : 0) + " clientes" +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta rutina con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta rutina
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rutina rutina = (Rutina) o;
        return idRutina == rutina.idRutina &&
                Objects.equals(nombre, rutina.nombre) &&
                Objects.equals(descripcion, rutina.descripcion);
    }

    /**
     * Calcula el código hash para esta rutina.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(idRutina, nombre, descripcion);
    }
}
