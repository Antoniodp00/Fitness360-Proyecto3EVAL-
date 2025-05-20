package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un campo está vacío.
 *
 * Esta excepción se lanza cuando un campo requerido está vacío o solo contiene espacios en blanco.
 */
public class CampoVacioException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public CampoVacioException(String message) {
        super(message);
    }
}