package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un valor decimal no es positivo.
 *
 * Esta excepción se lanza cuando un campo que debería contener un número decimal positivo
 * contiene un valor que es cero o negativo.
 */
public class DecimalNoPositivoException extends RuntimeException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public DecimalNoPositivoException(String message) {
        super(message);
    }
}