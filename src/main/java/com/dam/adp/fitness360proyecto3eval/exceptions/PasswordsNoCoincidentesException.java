package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para indicar que las contraseñas no coinciden.
 *
 * Esta excepción se lanza cuando las contraseñas ingresadas en los campos de contraseña
 * y confirmación de contraseña no son iguales.
 */
public class PasswordsNoCoincidentesException extends ValidacionException {
    /**
     * Constructor de la excepción que permite especificar un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public PasswordsNoCoincidentesException(String message) {
        super(message);
    }
}