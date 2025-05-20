package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que un correo electrónico es inválido.
 *
 * Esta excepción se lanza cuando se detecta un formato incorrecto en la dirección de correo electrónico
 * proporcionada por el usuario.
 */
public class EmailInvalidoException extends RuntimeException {

    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public EmailInvalidoException(String message) {
        super(message);
    }
}
