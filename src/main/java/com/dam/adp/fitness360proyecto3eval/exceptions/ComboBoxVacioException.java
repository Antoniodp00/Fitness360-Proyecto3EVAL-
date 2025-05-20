package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un ComboBox no tiene selección.
 *
 * Esta excepción se lanza cuando un ComboBox que debería tener un valor seleccionado
 * no tiene ninguna selección.
 */
public class ComboBoxVacioException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ComboBoxVacioException(String message) {
        super(message);
    }
}