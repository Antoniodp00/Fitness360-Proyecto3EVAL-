package com.dam.adp.fitness360proyecto3eval.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Clase base que representa a un usuario en el sistema.
 * Contiene información común a todos los usuarios como identificador, nombre de usuario,
 * datos personales (nombre, apellidos, correo, teléfono, fecha de nacimiento, sexo),
 * credenciales de acceso y datos de auditoría.
 */
public class Usuario {
    protected int id;
    protected String nombreUsuario;
    protected String nombre;
    protected String apellidos;
    protected String correo;
    protected String password;
    protected String telefono;
    protected Date fechaNacimiento;
    protected Sexo sexo;
    protected Estado estado;
    protected Date createdAt;
    protected Date updatedAt;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de Usuario sin inicializar sus atributos.
     */
    public Usuario() {}

    /**
     * Constructor completo para crear un usuario con todos los atributos.
     * 
     * @param id Identificador único del usuario
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico del usuario
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del usuario
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param sexo Sexo o género del usuario
     * @param estado Estado del usuario en el sistema
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Usuario(int id, String nombreUsuario, String nombre, String apellidos, String correo,
                   String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                   Date createdAt, Date updatedAt) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor completo para crear un usuario con todos los atributos.
     *
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico del usuario
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del usuario
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param sexo Sexo o género del usuario
     * @param estado Estado del usuario en el sistema
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     */
    public Usuario(String nombreUsuario, String nombre, String apellidos, String correo,
                   String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                   Date createdAt, Date updatedAt) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return El identificador del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     * 
     * @param id El nuevo identificador del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario para acceso al sistema.
     * 
     * @return El nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario para acceso al sistema.
     * 
     * @param nombreUsuario El nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el nombre real del usuario.
     * 
     * @return El nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre real del usuario.
     * 
     * @param nombre El nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * 
     * @return Los apellidos del usuario
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * 
     * @param apellidos Los nuevos apellidos del usuario
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return El correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param correo El nuevo correo del usuario
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña de acceso al sistema.
     * 
     * @return La contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña de acceso al sistema.
     * 
     * @param password La nueva contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     * 
     * @return El teléfono del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     * 
     * @param telefono El nuevo teléfono del usuario
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * 
     * @return La fecha de nacimiento del usuario
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     * 
     * @param fechaNacimiento La nueva fecha de nacimiento del usuario
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el sexo o género del usuario.
     * 
     * @return El sexo del usuario
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Establece el sexo o género del usuario.
     * 
     * @param sexo El nuevo sexo del usuario
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene el estado del usuario en el sistema.
     * 
     * @return El estado del usuario
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del usuario en el sistema.
     * 
     * @param estado El nuevo estado del usuario
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
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
     * Devuelve una representación en cadena de texto del usuario.
     * 
     * @return Cadena de texto con los datos principales del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", password='[PROTEGIDA]'" +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", sexo=" + sexo +
                ", estado=" + estado +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compara este usuario con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con este usuario
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id &&
                Objects.equals(nombreUsuario, usuario.nombreUsuario) &&
                Objects.equals(correo, usuario.correo);
    }

    /**
     * Calcula el código hash para este usuario.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nombreUsuario, correo);
    }
}
