package com.dam.adp.fitness360proyecto3eval.model;

/**
 * Clase Singleton para gestionar la sesión del usuario en la aplicación.
 * Mantiene el estado de autenticación y el usuario actual.
 */
public class Sesion {
    // Instancia única de la clase (patrón Singleton)
    private static Sesion instance;

    // Usuario autenticado actualmente
    private Usuario usuarioAutenticado;

    /**
     * Constructor privado para evitar instanciación directa (patrón Singleton)
     */
    private Sesion() {
        // Constructor privado para implementar el patrón Singleton
    }

    /**
     * Obtiene la instancia única de la sesión (patrón Singleton)
     * @return La instancia única de Session
     */
    public static Sesion getInstance() {
        if (instance == null) {
            instance = new Sesion();
        }
        return instance;
    }

    /**
     * Establece el usuario autenticado en la sesión
     * @param usuario El usuario autenticado
     */
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    /**
     * Obtiene el usuario autenticado actualmente
     * @return El usuario autenticado
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Verifica si el usuario autenticado es un cliente
     * @return true si es un cliente, false en caso contrario
     */
    public boolean isCliente() {
        return usuarioAutenticado instanceof UsuarioCliente;
    }

    /**
     * Verifica si el usuario autenticado es un empleado
     * @return true si es un empleado, false en caso contrario
     */
    public boolean isEmpleado() {
        return usuarioAutenticado instanceof UsuarioEmpleado;
    }

    /**
     * Obtiene el usuario autenticado como cliente
     * @return El usuario cliente autenticado o null si no es un cliente
     */
    public UsuarioCliente getClienteAutenticado() {
        if (isCliente()) {
            return (UsuarioCliente) usuarioAutenticado;
        }
        return null;
    }

    /**
     * Obtiene el usuario autenticado como empleado
     * @return El usuario empleado autenticado o null si no es un empleado
     */
    public UsuarioEmpleado getEmpleadoAutenticado() {
        if (isEmpleado()) {
            return (UsuarioEmpleado) usuarioAutenticado;
        }
        return null;
    }

    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        usuarioAutenticado = null;
    }
}
