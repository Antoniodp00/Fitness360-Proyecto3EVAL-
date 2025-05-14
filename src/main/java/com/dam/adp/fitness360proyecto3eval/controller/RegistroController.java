package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.exceptions.*;
import com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controlador para la pantalla de registro de usuarios.
 * Maneja el registro de nuevos clientes y profesionales (empleados) en el sistema.
 * Permite introducir los datos personales y específicos según el tipo de usuario.
 */
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
    public void initialize() {
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
                Utilidades.mostrarAlerta("Registro exitoso", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                irALogin();
            }
        } catch (UsuarioYaExisteException e) {
            // Mostrar mensaje específico para usuario ya existente
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
        StringBuilder errores = new StringBuilder();

        try {
            // Validar campos de texto
            Utilidades.validarCampoNoVacio(usernameField.getText(), "nombre de usuario");
            Utilidades.validarCampoNoVacio(nombreField.getText(), "nombre");
            Utilidades.validarCampoNoVacio(apellidosField.getText(), "apellidos");

            // Validar correo electrónico
            Utilidades.validarEmail(correoField.getText());

            // Validar contraseñas
            Utilidades.validarPasswordsCoinciden(passwordField.getText(), confirmPasswordField.getText());

            // Validar teléfono
            Utilidades.validarTelefono(telefonoField.getText(), "teléfono");

            // Validar fecha de nacimiento
            Utilidades.validarFechaNacimiento(fechaNacimientoField.getValue(), "fecha de nacimiento");

            // Validar selección de sexo
            Utilidades.validarComboBox(sexoComboBox, "sexo");

        } catch (RuntimeException e) {
            errores.append("Error: ").append(e.getMessage());
            errorMessage.setText(errores.toString());
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
        UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
        double altura = 0;

        try {
            // Validar y obtener la altura como número decimal positivo
            altura = Utilidades.validarDecimalPositivo(alturaField.getText(), "altura");
        } catch (DecimalNoPositivoException e) {
            errorMessage.setText("Error: " + e.getMessage());
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
       clienteDAO.insert(cliente);
        return true;
    }

    /**
     * Registra un nuevo usuario empleado.
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarEmpleado(){
        UsuarioEmpleadoDAO empleadoDAO = new UsuarioEmpleadoDAO();
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
        empleadoDAO.insert(empleado);
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

}
