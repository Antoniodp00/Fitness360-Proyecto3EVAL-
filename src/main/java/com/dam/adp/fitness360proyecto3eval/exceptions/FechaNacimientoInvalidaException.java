package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que una fecha de nacimiento es inválida.
 *
 * Esta excepción se lanza cuando una fecha de nacimiento no cumple con los criterios lógicos,
 * como ser una fecha futura o ser demasiado antigua.
 */
public class FechaNacimientoInvalidaException extends ValidacionException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public FechaNacimientoInvalidaException(String message) {
        super(message);
    }
}