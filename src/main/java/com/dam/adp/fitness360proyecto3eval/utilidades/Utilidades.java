package com.dam.adp.fitness360proyecto3eval.utilidades;

import com.dam.adp.fitness360proyecto3eval.exceptions.EmailInvalidoException;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
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
     * @return true si el correo es válido.
     * @throws EmailInvalidoException si el formato del correo es inválido.
     */
    public static boolean validarEmail(String email) {
        if (!email.matches(emailRegex)) {
            throw new EmailInvalidoException("Formato de correo electrónico inválido");
        }
        return true;
    }

    /**
     * Muestra una alerta con el título, mensaje y tipo especificados.
     *
     * @param titulo  Título de la alerta
     * @param mensaje Mensaje de la alerta
     * @param tipo    Tipo de alerta (INFORMATION, WARNING, ERROR, etc.)
     */
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Formatea una fecha en formato español (dd 'de' MMMM 'de' yyyy).
     *
     * @param fecha La fecha a formatear
     * @return La fecha formateada como String
     */
    public static String formatearFechaEspanol(Date fecha) {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return format.format(fecha);
    }
}
