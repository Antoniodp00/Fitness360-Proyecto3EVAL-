package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un número de teléfono es inválido.
 *
 * Esta excepción se lanza cuando un campo que debería contener un número de teléfono
 * no cumple con el formato esperado (por ejemplo, no tiene 9 dígitos).
 */
public class TelefonoInvalidoException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public TelefonoInvalidoException(String message) {
        super(message);
    }
}