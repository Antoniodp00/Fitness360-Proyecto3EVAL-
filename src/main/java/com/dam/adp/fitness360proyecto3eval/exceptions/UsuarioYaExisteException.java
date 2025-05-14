package com.dam.adp.fitness360proyecto3eval.exceptions;
/**
 * Excepción personalizada para indicar que un usuario ya existe.
 *
 * Esta excepción se lanza cuando un usuario intenta realizar un registro de un usuario existente.
 */

public class UsuarioYaExisteException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
