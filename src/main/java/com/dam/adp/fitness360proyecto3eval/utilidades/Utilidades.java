package com.dam.adp.fitness360proyecto3eval.utilidades;

import com.dam.adp.fitness360proyecto3eval.exceptions.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    // Expresión regular para validar números enteros
    private static final String integerRegex = "^-?\\d+$";
    // Expresión regular para validar números decimales
    private static final String decimalRegex = "^-?\\d+(\\.\\d+)?$";
    // Expresión regular para validar números de teléfono (9 dígitos)
    private static final String phoneRegex = "^\\d{9}$";

    private static final Logger logger = LoggerFactory.getLogger(Utilidades.class);

    /**
     * Valida si un correo electrónico tiene un formato correcto.
     *
     * @param email Correo a validar.
     * @return true si el correo es válido.
     * @throws EmailInvalidoException si el formato del correo es inválido.
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Validación fallida: El correo electrónico no puede estar vacío");
            throw new EmailInvalidoException("El correo electrónico no puede estar vacío");
        }
        if (!email.matches(emailRegex)) {
            logger.error("Validación fallida: Formato de correo electrónico inválido: {}", email);
            throw new EmailInvalidoException("Formato de correo electrónico inválido");
        }
        logger.debug("Correo electrónico validado correctamente: {}", email);
        return true;
    }

    /**
     * Valida si un campo de texto está vacío.
     *
     * @param texto Texto a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si el texto no está vacío.
     * @throws CampoVacioException si el texto está vacío.
     */
    public static boolean validarCampoNoVacio(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            logger.error("Validación fallida: El campo {} no puede estar vacío", nombreCampo);
            throw new CampoVacioException("El campo " + nombreCampo + " no puede estar vacío");
        }
        logger.debug("Campo {} validado correctamente", nombreCampo);
        return true;
    }

    /**
     * Valida si un valor es un número entero válido.
     *
     * @param valor Valor a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return El valor convertido a entero si es válido.
     * @throws EnteroInvalidoException si el valor no es un entero válido.
     */
    public static int validarEntero(String valor, String nombreCampo) {
        validarCampoNoVacio(valor, nombreCampo);
        if (!valor.matches(integerRegex)) {
            throw new EnteroInvalidoException("El campo " + nombreCampo + " debe ser un número entero");
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new EnteroInvalidoException("El campo " + nombreCampo + " debe ser un número entero válido");
        }
    }

    /**
     * Valida si un valor es un número entero positivo válido.
     *
     * @param valor Valor a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return El valor convertido a entero si es válido.
     * @throws EnteroNoPositivoException si el valor no es un entero positivo válido.
     */
    public static int validarEnteroPositivo(String valor, String nombreCampo) {
        int numero = validarEntero(valor, nombreCampo);
        if (numero <= 0) {
            throw new EnteroNoPositivoException("El campo " + nombreCampo + " debe ser un número entero positivo");
        }
        return numero;
    }

    /**
     * Valida si un valor es un número decimal válido.
     *
     * @param valor Valor a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return El valor convertido a decimal si es válido.
     * @throws DecimalInvalidoException si el valor no es un decimal válido.
     */
    public static double validarDecimal(String valor, String nombreCampo) {
        validarCampoNoVacio(valor, nombreCampo);
        if (!valor.matches(decimalRegex)) {
            throw new DecimalInvalidoException("El campo " + nombreCampo + " debe ser un número decimal");
        }
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new DecimalInvalidoException("El campo " + nombreCampo + " debe ser un número decimal válido");
        }
    }

    /**
     * Valida si un valor es un número decimal positivo válido.
     *
     * @param valor Valor a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return El valor convertido a decimal si es válido.
     * @throws DecimalNoPositivoException si el valor no es un decimal positivo válido.
     */
    public static double validarDecimalPositivo(String valor, String nombreCampo) {
        double numero = validarDecimal(valor, nombreCampo);
        if (numero <= 0) {
            throw new DecimalNoPositivoException("El campo " + nombreCampo + " debe ser un número decimal positivo");
        }
        return numero;
    }

    /**
     * Valida si un número de teléfono tiene un formato correcto (9 dígitos).
     *
     * @param telefono Teléfono a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si el teléfono es válido.
     * @throws TelefonoInvalidoException si el formato del teléfono es inválido.
     */
    public static boolean validarTelefono(String telefono, String nombreCampo) {
        validarCampoNoVacio(telefono, nombreCampo);
        if (!telefono.matches(phoneRegex)) {
            throw new TelefonoInvalidoException("El campo " + nombreCampo + " debe ser un número de teléfono válido (9 dígitos)");
        }
        return true;
    }

    /**
     * Valida si una fecha no es nula.
     *
     * @param fecha Fecha a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si la fecha no es nula.
     * @throws FechaInvalidaException si la fecha es nula.
     */
    public static boolean validarFecha(LocalDate fecha, String nombreCampo) {
        if (fecha == null) {
            throw new FechaInvalidaException("El campo " + nombreCampo + " debe tener una fecha seleccionada");
        }
        return true;
    }

    /**
     * Valida si una fecha es posterior o igual a la fecha actual.
     *
     * @param fecha Fecha a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si la fecha es posterior o igual a la fecha actual.
     * @throws FechaNoFuturaException si la fecha es anterior a la fecha actual.
     */
    public static boolean validarFechaFutura(LocalDate fecha, String nombreCampo) {
        validarFecha(fecha, nombreCampo);
        if (fecha.isBefore(LocalDate.now())) {
            throw new FechaNoFuturaException("El campo " + nombreCampo + " debe ser una fecha futura o actual");
        }
        return true;
    }

    /**
     * Valida si un ComboBox tiene un valor seleccionado.
     *
     * @param comboBox ComboBox a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si el ComboBox tiene un valor seleccionado.
     * @throws ComboBoxVacioException si el ComboBox no tiene un valor seleccionado.
     */
    public static boolean validarComboBox(ComboBox<?> comboBox, String nombreCampo) {
        if (comboBox.getValue() == null) {
            throw new ComboBoxVacioException("Debe seleccionar un valor para " + nombreCampo);
        }
        return true;
    }

    /**
     * Valida si dos contraseñas coinciden.
     *
     * @param password Primera contraseña.
     * @param confirmPassword Confirmación de la contraseña.
     * @return true si las contraseñas coinciden.
     * @throws PasswordsNoCoincidentesException si las contraseñas no coinciden.
     */
    public static boolean validarPasswordsCoinciden(String password, String confirmPassword) {
        validarCampoNoVacio(password, "contraseña");
        validarCampoNoVacio(confirmPassword, "confirmación de contraseña");
        if (!password.equals(confirmPassword)) {
            throw new PasswordsNoCoincidentesException("Las contraseñas no coinciden");
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
        logger.info("Mostrando alerta: Tipo={}, Título={}, Mensaje={}", tipo, titulo, mensaje);
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

    /**
     * Valida si una fecha de nacimiento es lógica.
     * Comprueba que la fecha no sea nula, no esté en el futuro y no sea demasiado antigua.
     *
     * @param fechaNacimiento Fecha de nacimiento a validar.
     * @param nombreCampo Nombre del campo para el mensaje de error.
     * @return true si la fecha de nacimiento es lógica.
     * @throws FechaNacimientoInvalidaException si la fecha no cumple con los criterios.
     */
    public static boolean validarFechaNacimiento(LocalDate fechaNacimiento, String nombreCampo) {
        validarFecha(fechaNacimiento, nombreCampo);

        LocalDate hoy = LocalDate.now();
        logger.debug("Validando fecha de nacimiento: {}, campo: {}", fechaNacimiento, nombreCampo);

        // Verificar que la fecha no sea demasiado antigua (más de 120 años)
        LocalDate fechaMinima = hoy.minusYears(120);
        if (fechaNacimiento.isBefore(fechaMinima)) {
            logger.error("Fecha de nacimiento demasiado antigua: {}", fechaNacimiento);
            throw new FechaNacimientoInvalidaException("El campo " + nombreCampo + " no puede ser una fecha demasiado antigua");
        }

        // Verificar que no sea demasiado reciente (por ejemplo, menor de 10 años)
        LocalDate fechaMaxima = hoy.minusYears(10);
        if (fechaNacimiento.isAfter(fechaMaxima)) {
            logger.error("Fecha de nacimiento indica usuario demasiado joven: {}", fechaNacimiento);
            throw new FechaNacimientoInvalidaException("El campo " + nombreCampo + " indica que el usuario es demasiado joven (debe tener al menos 10 años)");
        }

        logger.debug("Fecha de nacimiento validada correctamente: {}", fechaNacimiento);
        return true;
    }
}
