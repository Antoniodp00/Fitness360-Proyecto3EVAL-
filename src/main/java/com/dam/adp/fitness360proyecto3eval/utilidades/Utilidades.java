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

    /**
     * Lee un número entero desde la consola, validando la entrada.
     *
     * @param mensaje Mensaje que se mostrará al usuario.
     * @return El número entero ingresado por el usuario.
     */
    public static int leeEntero(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            try {
              //  VistaConsola.mostrarMensaje(mensaje);
                numero = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
               // VistaConsola.mostrarMensaje("\u001B[31m❌ Error: Ingresa un número entero válido.\u001B[0m");
            }
        }
        return numero;
    }

    /**
     * Lee una cadena de texto desde la consola, asegurando que no esté vacía.
     *
     * @param mensaje Mensaje que se mostrará al usuario.
     * @return La cadena ingresada por el usuario.
     */
    public static String leeString(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (input.isBlank()) {
          //  VistaConsola.mostrarMensaje(mensaje);
            input = scanner.nextLine().trim();

            if (input.isBlank()) {
              //  VistaConsola.mostrarMensaje("\u001B[31m❌ Error: La entrada no puede estar vacía.\u001B[0m");
            }
        }
        return input;

    }
}
