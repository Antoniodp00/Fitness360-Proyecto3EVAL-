package com.dam.adp.fitness360proyecto3eval.model;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Clase para representar un cliente con una propiedad de selección.
 * Se utiliza principalmente en las interfaces de asignación de dietas, rutinas o tarifas
 * para permitir seleccionar clientes mediante checkboxes en tablas.
 */
public class ClienteSeleccionable {
    /** El cliente que se puede seleccionar */
    private final UsuarioCliente cliente;

    /** Propiedad observable que indica si el cliente está seleccionado */
    private final SimpleBooleanProperty seleccionado;

    /** Indica si el cliente ya tenía asignado el elemento (dieta, rutina, etc.) */
    private final boolean yaAsignado;

    /**
     * Constructor que crea un cliente seleccionable.
     * 
     * @param cliente El cliente que se puede seleccionar
     * @param yaAsignado Indica si el cliente ya tiene asignado el elemento (dieta, rutina, etc.)
     *                   Este valor también inicializa el estado de selección
     */
    public ClienteSeleccionable(UsuarioCliente cliente, boolean yaAsignado) {
        this.cliente = cliente;
        this.yaAsignado = yaAsignado;
        this.seleccionado = new SimpleBooleanProperty(yaAsignado);
    }

    /**
     * Obtiene el objeto cliente asociado.
     * 
     * @return El objeto UsuarioCliente
     */
    public UsuarioCliente getCliente() {
        return cliente;
    }

    /**
     * Obtiene el nombre del cliente.
     * 
     * @return El nombre del cliente
     */
    public String getNombre() {
        return cliente.getNombre();
    }

    /**
     * Obtiene los apellidos del cliente.
     * 
     * @return Los apellidos del cliente
     */
    public String getApellidos() {
        return cliente.getApellidos();
    }

    /**
     * Obtiene el email del cliente.
     * 
     * @return El email del cliente
     */
    public String getEmail() {
        return cliente.getCorreo();
    }

    /**
     * Verifica si el cliente está seleccionado.
     * 
     * @return true si el cliente está seleccionado, false en caso contrario
     */
    public boolean isSeleccionado() {
        return seleccionado.get();
    }

    /**
     * Establece el estado de selección del cliente.
     * 
     * @param seleccionado true para seleccionar el cliente, false para deseleccionarlo
     */
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado.set(seleccionado);
    }

    /**
     * Obtiene la propiedad observable de selección.
     * Útil para enlazar con componentes JavaFX como CheckBoxes.
     * 
     * @return La propiedad SimpleBooleanProperty que representa el estado de selección
     */
    public SimpleBooleanProperty seleccionadoProperty() {
        return seleccionado;
    }

    /**
     * Verifica si el cliente ya tenía asignado el elemento (dieta, rutina, etc.).
     * 
     * @return true si el cliente ya tenía la asignación, false en caso contrario
     */
    public boolean isYaAsignado() {
        return yaAsignado;
    }
}
