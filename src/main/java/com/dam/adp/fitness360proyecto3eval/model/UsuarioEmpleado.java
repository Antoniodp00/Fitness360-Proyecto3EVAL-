package com.dam.adp.fitness360proyecto3eval.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa a un usuario con rol de empleado en el sistema.
 * Extiende la clase Usuario añadiendo información específica de los empleados
 * como descripción, rol, especialidad (entrenador, dietista o ambos) y estado.
 */
public class UsuarioEmpleado extends Usuario {
    private String descripcion;
    private String rol;
    private Especialidad especialidad;
    private List<Rutina> rutinasCreadas;
    private List<Dieta> dietasCreadas;
    private List<Tarifa> tarifasAsignadas;
    private List<Revision> revisiones;

    /**
     * Constructor por defecto sin parámetros.
     * Crea una instancia de UsuarioEmpleado sin inicializar sus atributos.
     */
    public UsuarioEmpleado() {
        super();
    }

    /**
     * Constructor con parámetros básicos para crear un empleado.
     * 
     * @param id Identificador único del empleado
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del empleado
     * @param apellidos Apellidos del empleado
     * @param correo Correo electrónico del empleado
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del empleado
     * @param fechaNacimiento Fecha de nacimiento del empleado
     * @param sexo Sexo o género del empleado
     * @param estado Estado del empleado en el sistema
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     * @param descripcion Descripción o biografía del empleado
     * @param rol Rol o puesto del empleado en la empresa
     * @param especialidad Especialidad del empleado (entrenador, dietista o ambos)
     */
    public UsuarioEmpleado(int id, String nombreUsuario, String nombre, String apellidos, String correo,
                           String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                           Date createdAt, Date updatedAt, String descripcion, String rol, Especialidad especialidad) {
        super(id, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.descripcion = descripcion;
        this.rol = rol;
        this.especialidad = especialidad;
    }

    /**
     * Constructor con parámetros básicos para crear un empleado.
     *
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del empleado
     * @param apellidos Apellidos del empleado
     * @param correo Correo electrónico del empleado
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del empleado
     * @param fechaNacimiento Fecha de nacimiento del empleado
     * @param sexo Sexo o género del empleado
     * @param estado Estado del empleado en el sistema
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     * @param descripcion Descripción o biografía del empleado
     * @param rol Rol o puesto del empleado en la empresa
     * @param especialidad Especialidad del empleado (entrenador, dietista o ambos)
     */
    public UsuarioEmpleado(String nombreUsuario, String nombre, String apellidos, String correo,
                           String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                           Date createdAt, Date updatedAt, String descripcion, String rol, Especialidad especialidad) {
        super(nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.descripcion = descripcion;
        this.rol = rol;
        this.especialidad = especialidad;
    }

    /**
     * Constructor completo para crear un empleado con todos los atributos.
     * 
     * @param id Identificador único del empleado
     * @param nombreUsuario Nombre de usuario para acceso al sistema
     * @param nombre Nombre real del empleado
     * @param apellidos Apellidos del empleado
     * @param correo Correo electrónico del empleado
     * @param password Contraseña de acceso al sistema
     * @param telefono Número de teléfono del empleado
     * @param fechaNacimiento Fecha de nacimiento del empleado
     * @param sexo Sexo o género del empleado
     * @param estado Estado del empleado en el sistema
     * @param createdAt Fecha y hora de creación del registro
     * @param updatedAt Fecha y hora de última actualización del registro
     * @param descripcion Descripción o biografía del empleado
     * @param rol Rol o puesto del empleado en la empresa
     * @param especialidad Especialidad del empleado (entrenador, dietista o ambos)
     * @param rutinasCreadas Lista de rutinas creadas por el empleado
     * @param dietasCreadas Lista de dietas creadas por el empleado
     * @param tarifasAsignadas Lista de tarifas asignadas por el empleado
     * @param revisiones Lista de revisiones realizadas por el empleado
     */
    public UsuarioEmpleado(int id, String nombreUsuario, String nombre, String apellidos, String correo,
                           String password, String telefono, Date fechaNacimiento, Sexo sexo, Estado estado,
                           Date createdAt, Date updatedAt, String descripcion, String rol, Especialidad especialidad,
                           List<Rutina> rutinasCreadas, List<Dieta> dietasCreadas, List<Tarifa> tarifasAsignadas,
                           List<Revision> revisiones) {
        super(id, nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, createdAt, updatedAt);
        this.descripcion = descripcion;
        this.rol = rol;
        this.especialidad = especialidad;
        this.rutinasCreadas = rutinasCreadas;
        this.dietasCreadas = dietasCreadas;
        this.tarifasAsignadas = tarifasAsignadas;
        this.revisiones = revisiones;
    }

    /**
     * Obtiene la descripción o biografía del empleado.
     * 
     * @return La descripción del empleado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción o biografía del empleado.
     * 
     * @param descripcion La nueva descripción del empleado
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el rol o puesto del empleado en la empresa.
     * 
     * @return El rol del empleado
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol o puesto del empleado en la empresa.
     * 
     * @param rol El nuevo rol del empleado
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la especialidad del empleado (entrenador, dietista o ambos).
     * 
     * @return La especialidad del empleado
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del empleado (entrenador, dietista o ambos).
     * 
     * @param especialidad La nueva especialidad del empleado
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene la lista de rutinas creadas por el empleado.
     * 
     * @return La lista de rutinas creadas
     */
    public List<Rutina> getRutinasCreadas() {
        return rutinasCreadas;
    }

    /**
     * Establece la lista de rutinas creadas por el empleado.
     * 
     * @param rutinasCreadas La nueva lista de rutinas creadas
     */
    public void setRutinasCreadas(List<Rutina> rutinasCreadas) {
        this.rutinasCreadas = rutinasCreadas;
    }

    /**
     * Obtiene la lista de dietas creadas por el empleado.
     * 
     * @return La lista de dietas creadas
     */
    public List<Dieta> getDietasCreadas() {
        return dietasCreadas;
    }

    /**
     * Establece la lista de dietas creadas por el empleado.
     * 
     * @param dietasCreadas La nueva lista de dietas creadas
     */
    public void setDietasCreadas(List<Dieta> dietasCreadas) {
        this.dietasCreadas = dietasCreadas;
    }

    /**
     * Obtiene la lista de tarifas asignadas por el empleado.
     * 
     * @return La lista de tarifas asignadas
     */
    public List<Tarifa> getTarifasAsignadas() {
        return tarifasAsignadas;
    }

    /**
     * Establece la lista de tarifas asignadas por el empleado.
     * 
     * @param tarifasAsignadas La nueva lista de tarifas asignadas
     */
    public void setTarifasAsignadas(List<Tarifa> tarifasAsignadas) {
        this.tarifasAsignadas = tarifasAsignadas;
    }

    /**
     * Obtiene la lista de revisiones realizadas por el empleado.
     * 
     * @return La lista de revisiones
     */
    public List<Revision> getRevisiones() {
        return revisiones;
    }

    /**
     * Establece la lista de revisiones realizadas por el empleado.
     * 
     * @param revisiones La nueva lista de revisiones
     */
    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    /**
     * Devuelve una representación en cadena de texto del empleado.
     * 
     * @return Cadena de texto con los datos principales del empleado
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.apellidos + " (" + this.rol + ")";
    }

    /**
     * Compara este empleado con otro objeto para determinar si son iguales.
     * 
     * @param o El objeto a comparar con este empleado
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UsuarioEmpleado empleado = (UsuarioEmpleado) o;
        return Objects.equals(rol, empleado.rol) &&
                especialidad == empleado.especialidad;
    }

    /**
     * Calcula el código hash para este empleado.
     * 
     * @return El código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rol, especialidad);
    }
}
