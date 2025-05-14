package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que una fecha es inválida.
 *
 * Esta excepción se lanza cuando un campo que debería contener una fecha
 * contiene un valor nulo o un formato de fecha inválido.
 */
public class FechaInvalidaException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public FechaInvalidaException(String message) {
        super(message);
    }
}