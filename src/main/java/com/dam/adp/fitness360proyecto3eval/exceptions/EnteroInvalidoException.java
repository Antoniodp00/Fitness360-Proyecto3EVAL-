package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un valor no es un número entero válido.
 *
 * Esta excepción se lanza cuando un campo que debería contener un número entero
 * contiene un valor que no puede ser convertido a entero.
 */
public class EnteroInvalidoException extends ValidacionException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public EnteroInvalidoException(String message) {
        super(message);
    }
}