package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.exceptions.EmailInvalidoException;
import com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException;
import com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
import com.dam.adp.fitness360proyecto3eval.views.MainApplication;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class RegistroController {

    @FXML
    private RadioButton clienteRadio;

    @FXML
    private RadioButton profesionalRadio;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidosField;

    @FXML
    private TextField correoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker fechaNacimientoField;

    @FXML
    private ComboBox<Sexo> sexoComboBox;

    @FXML
    private VBox clienteFields;

    @FXML
    private TextField alturaField;

    @FXML
    private VBox profesionalFields;

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField rolField;

    @FXML
    private ComboBox<Especialidad> especialidadComboBox;

    @FXML
    private Label errorMessage;

    @FXML
    private Button registrarButton;

    @FXML
    private Button cancelarButton;

    /**
     * Inicializa el controlador después de que se haya cargado el FXML.
     */
    public void inicializar() {
        // Configurar los ComboBox
        sexoComboBox.setItems(FXCollections.observableArrayList(Sexo.values()));
        especialidadComboBox.setItems(FXCollections.observableArrayList(Especialidad.values()));

        // Configurar los listeners para los radio buttons
        clienteRadio.setOnAction(event -> alternarCamposTipoUsuario());
        profesionalRadio.setOnAction(event -> alternarCamposTipoUsuario());

        // Configurar los botones
        registrarButton.setOnAction(event -> manejarRegistro());
        cancelarButton.setOnAction(event -> manejarCancelacion());

        // Inicialmente mostrar los campos de cliente
        alternarCamposTipoUsuario();
    }

    /**
     * Alterna la visibilidad de los campos específicos según el tipo de usuario seleccionado.
     */
    private void alternarCamposTipoUsuario() {
        if (clienteRadio.isSelected()) {
            clienteFields.setVisible(true);
            clienteFields.setManaged(true);
            profesionalFields.setVisible(false);
            profesionalFields.setManaged(false);
        } else {
            clienteFields.setVisible(false);
            clienteFields.setManaged(false);
            profesionalFields.setVisible(true);
            profesionalFields.setManaged(true);
        }
    }

    /**
     * Maneja el evento de clic en el botón Registrar.
     */
    private void manejarRegistro() {
        // Limpiar mensaje de error previo
        errorMessage.setVisible(false);

        // Validar campos comunes
        if (!validarCamposComunes()) {
            return;
        }

        try {
            boolean registroExitoso = false;

            // Registrar según el tipo de usuario
            if (clienteRadio.isSelected()) {
                registroExitoso = registrarCliente();
            } else {
                registroExitoso = registrarEmpleado();
            }

            // Solo mostrar mensaje de éxito y redirigir si el registro fue exitoso
            if (registroExitoso) {
                mostrarAlerta("Registro exitoso", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                irALogin();
            }

        } catch (Exception e) {
            errorMessage.setText("Error: " + e.getMessage());
            errorMessage.setVisible(true);
        }
    }

    /**
     * Valida los campos comunes a ambos tipos de usuario.
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCamposComunes() {
        boolean valido = true;
        // Verificar que los campos no estén vacíos
        if (usernameField.getText().isEmpty() || 
            nombreField.getText().isEmpty() || 
            apellidosField.getText().isEmpty() || 
            correoField.getText().isEmpty() || 
            passwordField.getText().isEmpty() || 
            confirmPasswordField.getText().isEmpty() || 
            telefonoField.getText().isEmpty() || 
            fechaNacimientoField.getValue() == null || 
            sexoComboBox.getValue() == null) {

            errorMessage.setText("Error: Todos los campos son obligatorios");
            errorMessage.setVisible(true);
            valido = false;
        }

        // Verificar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorMessage.setText("Error: Las contraseñas no coinciden");
            errorMessage.setVisible(true);
            valido = false;
        }

        // Validar formato de correo electrónico
        if (!Utilidades.validarEmail(correoField.getText())) {
            errorMessage.setText("Error: Formato de correo electrónico inválido");
            errorMessage.setVisible(true);
            valido = false;
        }

        return valido;
    }

    /**
     * Registra un nuevo usuario cliente.
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarCliente() {
        double altura = 0;
        // Validar campo específico de cliente
        if (alturaField.getText().isEmpty()) {
            errorMessage.setText("Error: Debe ingresar la altura");
            errorMessage.setVisible(true);
            return false;
        }

        try {
            altura = Double.parseDouble(alturaField.getText());
            if (altura <= 0) {
                errorMessage.setText("Error: La altura debe ser un número positivo");
                errorMessage.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            errorMessage.setText("Error: La altura debe ser un número válido");
            errorMessage.setVisible(true);
            return false;
        }

        // Crear objeto UsuarioCliente
        UsuarioCliente cliente = new UsuarioCliente(
            usernameField.getText(),
            nombreField.getText(),
            apellidosField.getText(),
            correoField.getText(),
            HashUtil.hashPassword(passwordField.getText()), // Hashear la contraseña
            telefonoField.getText(),
            Date.from(fechaNacimientoField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
            sexoComboBox.getValue(),
            Estado.ACTIVO, // Por defecto, el usuario se crea activo
            altura
        );

        // Guardar en la base de datos
        UsuarioClienteDAO.insertCliente(cliente);
        return true;
    }

    /**
     * Registra un nuevo usuario empleado.
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarEmpleado(){
        // Validar campos específicos de empleado
        if (descripcionField.getText().isEmpty() || 
            rolField.getText().isEmpty() || 
            especialidadComboBox.getValue() == null) {
            errorMessage.setText("Error: Todos los campos son obligatorios");
            errorMessage.setVisible(true);
            return false;
        }

        // Crear objeto UsuarioEmpleado
        UsuarioEmpleado empleado = new UsuarioEmpleado(
            usernameField.getText(),
            nombreField.getText(),
            apellidosField.getText(),
            correoField.getText(),
            HashUtil.hashPassword(passwordField.getText()), // Hashear la contraseña
            telefonoField.getText(),
            Date.from(fechaNacimientoField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
            sexoComboBox.getValue(),
            Estado.ACTIVO, // Por defecto, el usuario se crea activo
            new Date(), // createdAt
            new Date(), // updatedAt
            descripcionField.getText(),
            rolField.getText(),
            especialidadComboBox.getValue()
        );

        // Guardar en la base de datos
        UsuarioEmpleadoDAO.insertEmpleado(empleado);
        return true;
    }

    /**
     * Maneja el evento de clic en el botón Cancelar.
     */
    private void manejarCancelacion() {
        irALogin();
    }

    /**
     * Navega a la pantalla de login.
     */
    private void irALogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorMessage.setText("Error al cargar la pantalla de login: " + e.getMessage());
            errorMessage.setVisible(true);
        }
    }

    /**
     * Muestra una alerta con el mensaje especificado.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
