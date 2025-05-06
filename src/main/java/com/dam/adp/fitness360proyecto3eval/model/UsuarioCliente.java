package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa a un usuario con rol de cliente en el sistema.
 * Extiende la clase Usuario añadiendo información específica de los clientes
 * como la altura, necesaria para cálculos relacionados con la salud y el fitness.
 */
public class UsuarioCliente extends Usuario {
    private double altura;
    private List<ClienteRutina> rutinasAsignadas;
    private List<ClienteDieta> dietasAsignadas;
    private List<Revision> revisiones;
    private List<ClienteTarifa> tarifasContratadas;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de UsuarioCliente sin inicializar sus atributos.
     */
    public UsuarioCliente() {
        super();
    }

    /**
     * Constructor con parámetros básicos para crear un cliente.
     * 
     * @param id Identificador único del cliente
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento del cliente
     * @param sexo Sexo o género del cliente
     * @param estado Estado del cliente en el sistema
     * @param altura Altura del cliente en centímetros
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public UsuarioCliente(int id, String nombreUsuario, String nombre, String apellidos, String correo,
                          String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                          double altura, Date createdAt, Date updatedAt) {
        super(id, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.altura = altura;
    }
    /**
     * Constructor con parámetros básicos para crear un cliente.
     *
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento del cliente
     * @param sexo Sexo o género del cliente
     * @param estado Estado del cliente en el sistema
     * @param altura Altura del cliente en centímetros
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public UsuarioCliente( String nombreUsuario, String nombre, String apellidos, String correo,
                          String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                          double altura, Date createdAt, Date updatedAt) {
        super(nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.altura = altura;
    }

    /**
     * Constructor con parámetros básicos para crear un cliente.
     *
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento del cliente
     * @param sexo Sexo o género del cliente
     * @param estado Estado del cliente en el sistema
     * @param altura Altura del cliente en centímetros

     */
    public UsuarioCliente( String nombreUsuario, String nombre, String apellidos, String correo,
                           String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                           double altura) {
        super(nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado);
        this.altura = altura;
    }


    /**
     * Constructor completo para crear un cliente con todos los atributos.
     * 
     * @param id Identificador único del cliente
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento del cliente
     * @param sexo Sexo o género del cliente
     * @param estado Estado del cliente en el sistema
     * @param altura Altura del cliente en centímetros
     * @param rutinasAsignadas Lista de rutinas asignadas al cliente
     * @param dietasAsignadas Lista de dietas asignadas al cliente
     * @param revisiones Lista de revisiones del cliente
     * @param tarifasContratadas Lista de tarifas contratadas por el cliente
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public UsuarioCliente(int id, String nombreUsuario, String nombre, String apellidos, String correo,
                          String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                          double altura, List<ClienteRutina> rutinasAsignadas, List<ClienteDieta> dietasAsignadas,
                          List<Revision> revisiones, List<ClienteTarifa> tarifasContratadas,
                          Date createdAt, Date updatedAt) {
        super(id, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.altura = altura;
        this.rutinasAsignadas = rutinasAsignadas;
        this.dietasAsignadas = dietasAsignadas;
        this.revisiones = revisiones;
        this.tarifasContratadas = tarifasContratadas;
    }

    /**
     * Obtiene la altura del cliente en centímetros.
     * 
     * @return La altura del cliente
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Establece la altura del cliente en centímetros.
     * 
     * @param altura La nueva altura del cliente
     */
    public void setAltura(double altura) {
        this.altura = altura;
    }

    /**
     * Obtiene la lista de rutinas asignadas al cliente.
     * 
     * @return La lista de rutinas asignadas
     */
    public List<ClienteRutina> getRutinasAsignadas() {
        return rutinasAsignadas;
    }

    /**
     * Establece la lista de rutinas asignadas al cliente.
     * 
     * @param rutinasAsignadas La nueva lista de rutinas asignadas
     */
    public void setRutinasAsignadas(List<ClienteRutina> rutinasAsignadas) {
        this.rutinasAsignadas = rutinasAsignadas;
    }

    /**
     * Obtiene la lista de dietas asignadas al cliente.
     * 
     * @return La lista de dietas asignadas
     */
    public List<ClienteDieta> getDietasAsignadas() {
        return dietasAsignadas;
    }

    /**
     * Establece la lista de dietas asignadas al cliente.
     * 
     * @param dietasAsignadas La nueva lista de dietas asignadas
     */
    public void setDietasAsignadas(List<ClienteDieta> dietasAsignadas) {
        this.dietasAsignadas = dietasAsignadas;
    }

    /**
     * Obtiene la lista de revisiones del cliente.
     * 
     * @return La lista de revisiones
     */
    public List<Revision> getRevisiones() {
        return revisiones;
    }

    /**
     * Establece la lista de revisiones del cliente.
     * 
     * @param revisiones La nueva lista de revisiones
     */
    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }

    /**
     * Obtiene la lista de tarifas contratadas por el cliente.
     * 
     * @return La lista de tarifas contratadas
     */
    public List<ClienteTarifa> getTarifasContratadas() {
        return tarifasContratadas;
    }

    /**
     * Establece la lista de tarifas contratadas por el cliente.
     * 
     * @param tarifasContratadas La nueva lista de tarifas contratadas
     */
    public void setTarifasContratadas(List<ClienteTarifa> tarifasContratadas) {
        this.tarifasContratadas = tarifasContratadas;
    }

    /**
     * Devuelve una representación en cadena de texto del cliente.
     * 
     * @return Cadena de texto con los datos principales del cliente
     */
    @Override
    public String toString() {
        return "UsuarioCliente{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", password='" + password + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", sexo=" + sexo +
                ", estado=" + estado +
                ", altura=" + altura +
                ", rutinasAsignadas=" + (rutinasAsignadas != null ? rutinasAsignadas.size() : 0) + " rutinas" +
                ", dietasAsignadas=" + (dietasAsignadas != null ? dietasAsignadas.size() : 0) + " dietas" +
                ", revisiones=" + (revisiones != null ? revisiones.size() : 0) + " revisiones" +
                ", tarifasContratadas=" + (tarifasContratadas != null ? tarifasContratadas.size() : 0) + " tarifas" +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara este cliente con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con este cliente
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UsuarioCliente cliente = (UsuarioCliente) o;
        return Double.compare(cliente.altura, altura) == 0;
    }

    /**
     * Calcula el código hash para este cliente.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), altura);
    }
}
