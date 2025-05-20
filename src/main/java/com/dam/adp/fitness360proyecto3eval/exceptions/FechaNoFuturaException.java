package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que una fecha no es futura.
 *
 * Esta excepción se lanza cuando un campo que debería contener una fecha futura
 * contiene una fecha que es anterior a la fecha actual.
 */
public class FechaNoFuturaException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public FechaNoFuturaException(String message) {
        super(message);
    }
}