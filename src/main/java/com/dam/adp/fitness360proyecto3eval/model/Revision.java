package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa una revisión o evaluación física de un cliente.
 * Almacena información sobre las medidas corporales (peso, grasa, músculo, medidas),
 * observaciones, imagen, el cliente evaluado, el empleado que realiza la evaluación,
 * y datos de auditoría.
 */
public class Revision {
    private int idRevision;
    private LocalDate fecha;
    private double peso;
    private double grasa;
    private double musculo;
    private double mPecho;
    private double mCintura;
    private double mCadera;
    private String observaciones;
    private String imagen;
    private UsuarioCliente cliente;
    private UsuarioEmpleado empleado;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Revision sin inicializar sus atributos.
     */
    public Revision() {
    }

    /**
     * Constructor completo para crear una revisión con todos los atributos, incluyendo datos de auditoría.
     * 
     * @param idRevision Identificador único de la revisión
     * @param fecha Fecha en que se realizó la revisión
     * @param peso Peso del cliente en kilogramos
     * @param grasa Porcentaje de grasa corporal
     * @param musculo Porcentaje de masa muscular
     * @param mPecho Medida del pecho en centímetros
     * @param mCintura Medida de la cintura en centímetros
     * @param mCadera Medida de la cadera en centímetros
     * @param observaciones Observaciones adicionales sobre la revisión
     * @param imagen Ruta o nombre de la imagen asociada a la revisión
     * @param cliente Cliente al que se le realiza la revisión
     * @param empleado Empleado que realiza la revisión
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Revision(int idRevision, LocalDate fecha, double peso, double grasa, double musculo, double mPecho, double mCintura, double mCadera, String observaciones, String imagen, UsuarioCliente cliente, UsuarioEmpleado empleado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idRevision = idRevision;
        this.fecha = fecha;
        this.peso = peso;
        this.grasa = grasa;
        this.musculo = musculo;
        this.mPecho = mPecho;
        this.mCintura = mCintura;
        this.mCadera = mCadera;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.cliente = cliente;
        this.empleado = empleado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor para crear una revisión sin datos de auditoría.
     * 
     * @param idRevision Identificador único de la revisión
     * @param fecha Fecha en que se realizó la revisión
     * @param peso Peso del cliente en kilogramos
     * @param grasa Porcentaje de grasa corporal
     * @param musculo Porcentaje de masa muscular
     * @param mPecho Medida del pecho en centímetros
     * @param mCintura Medida de la cintura en centímetros
     * @param mCadera Medida de la cadera en centímetros
     * @param observaciones Observaciones adicionales sobre la revisión
     * @param imagen Ruta o nombre de la imagen asociada a la revisión
     * @param cliente Cliente al que se le realiza la revisión
     * @param empleado Empleado que realiza la revisión
     */
    public Revision(int idRevision, LocalDate fecha, double peso, double grasa, double musculo, double mPecho, double mCintura, double mCadera, String observaciones, String imagen, UsuarioCliente cliente, UsuarioEmpleado empleado) {
        this.idRevision = idRevision;
        this.fecha = fecha;
        this.peso = peso;
        this.grasa = grasa;
        this.musculo = musculo;
        this.mPecho = mPecho;
        this.mCintura = mCintura;
        this.mCadera = mCadera;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    // Getters y setters...

    /**
     * Obtiene el identificador único de la revisión.
     * 
     * @return El identificador de la revisión
     */
    public int getIdRevision() {
        return idRevision;
    }

    /**
     * Establece el identificador único de la revisión.
     * 
     * @param idRevision El nuevo identificador de la revisión
     */
    public void setIdRevision(int idRevision) {
        this.idRevision = idRevision;
    }

    /**
     * Obtiene la fecha en que se realizó la revisión.
     * 
     * @return La fecha de la revisión
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó la revisión.
     * 
     * @param fecha La nueva fecha de la revisión
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el peso del cliente en kilogramos.
     * 
     * @return El peso del cliente
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Establece el peso del cliente en kilogramos.
     * 
     * @param peso El nuevo peso del cliente
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene el porcentaje de grasa corporal del cliente.
     * 
     * @return El porcentaje de grasa corporal
     */
    public double getGrasa() {
        return grasa;
    }

    /**
     * Establece el porcentaje de grasa corporal del cliente.
     * 
     * @param grasa El nuevo porcentaje de grasa corporal
     */
    public void setGrasa(double grasa) {
        this.grasa = grasa;
    }

    /**
     * Obtiene el porcentaje de masa muscular del cliente.
     * 
     * @return El porcentaje de masa muscular
     */
    public double getMusculo() {
        return musculo;
    }

    /**
     * Establece el porcentaje de masa muscular del cliente.
     * 
     * @param musculo El nuevo porcentaje de masa muscular
     */
    public void setMusculo(double musculo) {
        this.musculo = musculo;
    }

    /**
     * Obtiene la medida del pecho del cliente en centímetros.
     * 
     * @return La medida del pecho
     */
    public double getmPecho() {
        return mPecho;
    }

    /**
     * Establece la medida del pecho del cliente en centímetros.
     * 
     * @param mPecho La nueva medida del pecho
     */
    public void setmPecho(double mPecho) {
        this.mPecho = mPecho;
    }

    /**
     * Obtiene la medida de la cintura del cliente en centímetros.
     * 
     * @return La medida de la cintura
     */
    public double getmCintura() {
        return mCintura;
    }

    /**
     * Establece la medida de la cintura del cliente en centímetros.
     * 
     * @param mCintura La nueva medida de la cintura
     */
    public void setmCintura(double mCintura) {
        this.mCintura = mCintura;
    }

    /**
     * Obtiene la medida de la cadera del cliente en centímetros.
     * 
     * @return La medida de la cadera
     */
    public double getmCadera() {
        return mCadera;
    }

    /**
     * Establece la medida de la cadera del cliente en centímetros.
     * 
     * @param mCadera La nueva medida de la cadera
     */
    public void setmCadera(double mCadera) {
        this.mCadera = mCadera;
    }

    /**
     * Obtiene las observaciones adicionales sobre la revisión.
     * 
     * @return Las observaciones de la revisión
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones adicionales sobre la revisión.
     * 
     * @param observaciones Las nuevas observaciones de la revisión
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene la ruta o nombre de la imagen asociada a la revisión.
     * 
     * @return La imagen de la revisión
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la ruta o nombre de la imagen asociada a la revisión.
     * 
     * @param imagen La nueva imagen de la revisión
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el cliente al que se le realiza la revisión.
     * 
     * @return El cliente de la revisión
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente al que se le realiza la revisión.
     * 
     * @param cliente El nuevo cliente de la revisión
     */
    public void setCliente(UsuarioCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el empleado que realiza la revisión.
     * 
     * @return El empleado que realiza la revisión
     */
    public UsuarioEmpleado getEmpleado() {
        return empleado;
    }

    /**
     * Establece el empleado que realiza la revisión.
     * 
     * @param empleado El nuevo empleado que realiza la revisión
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

    public String getNombreClienteCompleto() {
        return cliente.getNombre() + " " + cliente.getApellidos();
    }


    /**
     * Devuelve una representación en cadena de texto de la revisión.
     * 
     * @return Cadena de texto con los datos principales de la revisión
     */
    @Override
    public String toString() {
        return "Revision{" +
                "idRevision=" + idRevision +
                ", fecha=" + fecha +
                ", peso=" + peso +
                ", grasa=" + grasa +
                ", musculo=" + musculo +
                ", mPecho=" + mPecho +
                ", mCintura=" + mCintura +
                ", mCadera=" + mCadera +
                ", observaciones='" + observaciones + '\'' +
                ", imagen='" + imagen + '\'' +
                ", cliente=" + (cliente != null ? "ID: " + cliente.getId() : "null") +
                ", empleado=" + (empleado != null ? "ID: " + empleado.getId() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara esta revisión con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con esta revisión
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return idRevision == revision.idRevision &&
                Objects.equals(fecha, revision.fecha) &&
                Objects.equals(cliente, revision.cliente);
    }

    /**
     * Calcula el código hash para esta revisión.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(idRevision, fecha, cliente);
    }
}
