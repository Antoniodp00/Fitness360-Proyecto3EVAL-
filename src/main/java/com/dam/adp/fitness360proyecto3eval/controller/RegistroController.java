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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para la pantalla de registro de usuarios.
 * Maneja el registro de nuevos clientes y profesionales (empleados) en el sistema.
 * Permite introducir los datos personales y específicos según el tipo de usuario.
 */
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

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

    private final UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
    private final UsuarioEmpleadoDAO empleadoDAO = new UsuarioEmpleadoDAO();

    /**
     * Inicializa el controlador después de que se haya cargado el FXML.
     */
    public void initialize() {
        logger.debug("Inicializando RegistroController");
        // Configurar los ComboBox
        logger.debug("Configurando ComboBox de sexo y especialidad");
        sexoComboBox.setItems(FXCollections.observableArrayList(Sexo.values()));
        especialidadComboBox.setItems(FXCollections.observableArrayList(Especialidad.values()));

        // Configurar los listeners para los radio buttons
        logger.debug("Configurando listeners para radio buttons");
        clienteRadio.setOnAction(event -> alternarCamposTipoUsuario());
        profesionalRadio.setOnAction(event -> alternarCamposTipoUsuario());

        // Configurar los botones
        logger.debug("Configurando eventos de botones");
        registrarButton.setOnAction(event -> manejarRegistro());
        cancelarButton.setOnAction(event -> manejarCancelacion());

        // Inicialmente mostrar los campos de cliente
        alternarCamposTipoUsuario();
        logger.debug("RegistroController inicializado correctamente");
    }

    /**
     * Alterna la visibilidad de los campos específicos según el tipo de usuario seleccionado.
     */
    private void alternarCamposTipoUsuario() {
        logger.debug("Alternando campos de tipo usuario");
        if (clienteRadio.isSelected()) {
            logger.debug("Tipo seleccionado: Cliente");
            clienteFields.setVisible(true);
            clienteFields.setManaged(true);
            profesionalFields.setVisible(false);
            profesionalFields.setManaged(false);
        } else {
            logger.debug("Tipo seleccionado: Profesional");
            clienteFields.setVisible(false);
            clienteFields.setManaged(false);
            profesionalFields.setVisible(true);
            profesionalFields.setManaged(true);
        }
        logger.debug("Campos de interfaz actualizados según el tipo de usuario");
    }

    /**
     * Maneja el evento de clic en el botón Registrar.
     */
    private void manejarRegistro() {
        logger.debug("Iniciando proceso de registro de usuario");
        // Limpiar mensaje de error previo
        errorMessage.setVisible(false);

        // Validar campos comunes
        if (validarCamposComunes()) {
            logger.warn("Validación de campos comunes correcta");
            try {
                boolean registroExitoso = false;

                // Registrar según el tipo de usuario
                if (clienteRadio.isSelected()) {
                    logger.info("Registrando nuevo cliente");
                    registroExitoso = registrarCliente();
                } else {
                    logger.info("Registrando nuevo profesional");
                    registroExitoso = registrarEmpleado();
                }

                // Solo mostrar mensaje de éxito y redirigir si el registro fue exitoso
                if (registroExitoso) {
                    logger.info("Registro de usuario completado con éxito");
                    Utilidades.mostrarAlerta("Registro exitoso", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                    irALogin();
                } else {
                    logger.warn("El registro de usuario no fue exitoso");
                }
            } catch (ValidacionException e) {
                // Mostrar mensaje específico para usuario ya existente
                logger.error("Error al registrar usuario: {}", e.getMessage(), e);
                errorMessage.setText("Error: " + e.getMessage());
                errorMessage.setVisible(true);
            }
        }else {
            logger.warn("Validación de campos comunes fallida");
        }
    }

    /**
     * Valida los campos comunes a ambos tipos de usuario.
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCamposComunes() {
        logger.debug("Validando campos comunes del formulario de registro");
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar campos de texto
            logger.debug("Validando campos de texto");
            Utilidades.validarCampoNoVacio(usernameField.getText(), "nombre de usuario");
            Utilidades.validarCampoNoVacio(nombreField.getText(), "nombre");
            Utilidades.validarCampoNoVacio(apellidosField.getText(), "apellidos");

            // Validar correo electrónico
            logger.debug("Validando correo electrónico");
            Utilidades.validarEmail(correoField.getText());

            // Validar contraseñas
            logger.debug("Validando contraseñas");
            Utilidades.validarPasswordsCoinciden(passwordField.getText(), confirmPasswordField.getText());

            // Validar teléfono
            logger.debug("Validando teléfono");
            Utilidades.validarTelefono(telefonoField.getText(), "teléfono");

            // Validar fecha de nacimiento
            logger.debug("Validando fecha de nacimiento");
            Utilidades.validarFechaNacimiento(fechaNacimientoField.getValue(), "fecha de nacimiento");

            // Validar selección de sexo
            logger.debug("Validando selección de sexo");
            Utilidades.validarComboBox(sexoComboBox, "sexo");

            logger.debug("Validación de campos comunes exitosa");
        } catch (ValidacionException e) {
            logger.warn("Validación fallida: {}", e.getMessage());
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
        logger.debug("Iniciando registro de nuevo cliente");
        double altura = 0;
        boolean registroExitoso = true;

        try {
            // Validar y obtener la altura como número decimal positivo
            logger.debug("Validando altura como decimal positivo");
            altura = Utilidades.validarDecimalPositivo(alturaField.getText(), "altura");

            // Crear objeto UsuarioCliente
            logger.debug("Creando objeto de cliente con los datos del formulario");
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
            logger.debug("Insertando cliente en la base de datos");
            clienteDAO.insert(cliente);
            logger.info("Cliente registrado correctamente: {}", cliente.getNombre());
        } catch (DecimalNoPositivoException e) {
            logger.warn("Validación fallida: {}", e.getMessage());
            errorMessage.setText("Error: " + e.getMessage());
            errorMessage.setVisible(true);
            registroExitoso = false;
        }

        return registroExitoso;
    }

    /**
     * Registra un nuevo usuario empleado.
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarEmpleado(){
        logger.debug("Iniciando registro de nuevo empleado");
        boolean registroExitoso = true;

        // Validar campos específicos de empleado
        logger.debug("Validando campos específicos de empleado");
        if (descripcionField.getText().isEmpty() || 
            rolField.getText().isEmpty() || 
            especialidadComboBox.getValue() == null) {
            logger.warn("Validación fallida: campos obligatorios vacíos");
            errorMessage.setText("Error: Todos los campos son obligatorios");
            errorMessage.setVisible(true);
            registroExitoso = false;
        } else {
            // Crear objeto UsuarioEmpleado
            logger.debug("Creando objeto de empleado con los datos del formulario");
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
            logger.debug("Insertando empleado en la base de datos");
            empleadoDAO.insert(empleado);
            logger.info("Empleado registrado correctamente: {}", empleado.getNombre());
        }

        return registroExitoso;
    }

    /**
     * Maneja el evento de clic en el botón Cancelar.
     */
    private void manejarCancelacion() {
        logger.debug("Cancelando registro de usuario");
        irALogin();
    }

    /**
     * Navega a la pantalla de login.
     */
    private void irALogin() {
        logger.debug("Navegando a la pantalla de login");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            logger.info("Navegación a pantalla de login completada");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de login: {}", e.getMessage(), e);
            errorMessage.setText("Error al cargar la pantalla de login: " + e.getMessage());
            errorMessage.setVisible(true);
        }
    }

}
