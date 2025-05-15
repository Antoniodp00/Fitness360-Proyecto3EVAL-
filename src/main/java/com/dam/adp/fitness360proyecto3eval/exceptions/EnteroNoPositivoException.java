package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un valor entero no es positivo.
 *
 * Esta excepción se lanza cuando un campo que debería contener un número entero positivo
 * contiene un valor que es cero o negativo.
 */
public class EnteroNoPositivoException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public EnteroNoPositivoException(String message) {
        super(message);
    }
}