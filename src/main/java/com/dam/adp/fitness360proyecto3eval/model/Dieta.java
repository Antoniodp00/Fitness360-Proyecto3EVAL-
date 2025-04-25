package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private UsuarioEmpleado creador;
    private List<ClienteDieta> clientesAsignados;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Dieta sin inicializar sus atributos.
     */
    public Dieta() {
    }

    /**
     * Constructor con parámetros básicos para crear una dieta.
     * 
     * @param idDieta Identificador único de la dieta
     * @param nombre Nombre de la dieta
     * @param descripcion Descripción detallada de la dieta
     * @param archivo Ruta o nombre del archivo asociado a la dieta
     */
    public Dieta(int idDieta, String nombre, String descripcion, String archivo) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Constructor completo para crear una dieta con todos los atributos.
     * 
     * @param idDieta Identificador único de la dieta
     * @param nombre Nombre de la dieta
     * @param descripcion Descripción detallada de la dieta
     * @param archivo Ruta o nombre del archivo asociado a la dieta
     * @param creador Empleado que creó la dieta
     * @param clientesAsignados Lista de clientes con esta dieta asignada
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Dieta(int idDieta, String nombre, String descripcion, String archivo, 
                UsuarioEmpleado creador, List<ClienteDieta> clientesAsignados, Date createdAt, Date updatedAt) {
        this.idDieta = idDieta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.creador = creador;
        this.clientesAsignados = clientesAsignados;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
     * Obtiene la descripción de la dieta.
     * 
     * @return La descripción de la dieta
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la dieta.
     * 
     * @param descripcion La nueva descripción de la dieta
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la ruta o nombre del archivo asociado a la dieta.
     * 
     * @return El archivo de la dieta
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Establece la ruta o nombre del archivo asociado a la dieta.
     * 
     * @param archivo El nuevo archivo de la dieta
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    /**
     * Obtiene el empleado que creó la dieta.
     * 
     * @return El creador de la dieta
     */
    public UsuarioEmpleado getCreador() {
        return creador;
    }

    /**
     * Establece el empleado que creó la dieta.
     * 
     * @param creador El nuevo creador de la dieta
     */
    public void setCreador(UsuarioEmpleado creador) {
        this.creador = creador;
    }

    /**
     * Obtiene la lista de clientes con esta dieta asignada.
     * 
     * @return La lista de clientes asignados
     */
    public List<ClienteDieta> getClientesAsignados() {
        return clientesAsignados;
    }

    /**
     * Establece la lista de clientes con esta dieta asignada.
     * 
     * @param clientesAsignados La nueva lista de clientes asignados
     */
    public void setClientesAsignados(List<ClienteDieta> clientesAsignados) {
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
     * Devuelve una representación en cadena de texto de la dieta.
     * 
     * @return Cadena de texto con los datos principales de la dieta
     */
    @Override
    public String toString() {
        return "Dieta{" +
                "idDieta=" + idDieta +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", archivo='" + archivo + '\'' +
                ", creador=" + (creador != null ? "presente" : "null") +
                ", clientesAsignados=" + (clientesAsignados != null ? clientesAsignados.size() : 0) + " clientes" +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta dieta con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta dieta
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dieta dieta = (Dieta) o;
        return idDieta == dieta.idDieta &&
                Objects.equals(nombre, dieta.nombre) &&
                Objects.equals(descripcion, dieta.descripcion) &&
                Objects.equals(archivo, dieta.archivo);
    }

    /**
     * Calcula el código hash para esta dieta.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(idDieta, nombre, descripcion, archivo);
    }
}
