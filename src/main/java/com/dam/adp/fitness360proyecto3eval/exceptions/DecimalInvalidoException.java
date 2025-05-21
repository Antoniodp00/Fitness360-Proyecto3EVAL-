package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un valor no es un número decimal válido.
 *
 * Esta excepción se lanza cuando un campo que debería contener un número decimal
 * contiene un valor que no puede ser convertido a decimal.
 */
public class DecimalInvalidoException extends ValidacionException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public DecimalInvalidoException(String message) {
        super(message);
    }
}