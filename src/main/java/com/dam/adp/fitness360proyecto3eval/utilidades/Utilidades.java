package com.dam.adp.fitness360proyecto3eval.utilidades;



import java.util.Scanner;

/**
 * Clase utilitaria con métodos para validaciones y entrada de datos desde la consola.
 */
public class Utilidades {
   // Expresión regular para validar correos electrónicos
    static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    /**
     * Valida si un correo electrónico tiene un formato correcto.
     *
     * @param email Correo a validar.
     * @return true si el correo es válido, false en caso contrario.
     */
    public static boolean validarEmail(String email) {
        return email.matches(emailRegex);
    }

}
