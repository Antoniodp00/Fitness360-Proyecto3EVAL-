package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.exceptions.ValidacionException;
import com.dam.adp.fitness360proyecto3eval.model.Estado;
import com.dam.adp.fitness360proyecto3eval.model.Usuario;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import com.dam.adp.fitness360proyecto3eval.model.Sesion;
import com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para la pantalla de login
 * Maneja la autenticación de usuarios (clientes y empleados)
 */
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /**
     * Campo para ingresar la contraseña del usuario
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Etiqueta para mostrar mensajes de error durante el proceso de login
     */
    @FXML
    private Label errorMessage;

    /**
     * Campo para ingresar el nombre de usuario
     */
    @FXML
    private TextField usernameField;

    private final UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
    private final UsuarioEmpleadoDAO empleadoDAO = new UsuarioEmpleadoDAO();

    /**
     * Inicializa el controlador después de que se haya cargado el FXML.
     * Configura los componentes de la interfaz y establece los valores iniciales.
     */
    @FXML
    public void initialize() {
        logger.debug("Inicializando LoginController");
        // Ocultar el mensaje de error inicialmente
        errorMessage.setVisible(false);
        logger.debug("LoginController inicializado correctamente");
    }

    /**
     * Maneja el evento de clic en el botón de login
     * Verifica las credenciales del usuario y navega a la pantalla principal si son correctas
     *
     * @param actionEvent El evento de acción que desencadenó este método
     */
    public void alHacerClicEnIniciarSesion(ActionEvent actionEvent) {
        logger.debug("Iniciando proceso de login");

        String username = usernameField.getText();
        String password = passwordField.getText();

        String mensajeError = null;
        Usuario usuarioAutenticado = null;
        boolean camposValidos = validarCampos();

        if (camposValidos) {
            logger.debug("Buscando usuario con nombre: {}", username);
            // Buscar y autenticar como cliente
            UsuarioCliente cliente = clienteDAO.findByUserName(username);

            if (cliente != null && autenticarUsuario(cliente, password)) {
                logger.info("Usuario cliente autenticado correctamente: {}", cliente.getNombre());
                usuarioAutenticado = cliente;
            } else {
                // Buscar y autenticar como empleado
                logger.debug("Usuario no encontrado como cliente o autenticación fallida, buscando como empleado");
                UsuarioEmpleado empleado = empleadoDAO.findByUserName(username);
                if (empleado != null && autenticarUsuario(empleado, password)) {
                    logger.info("Usuario empleado autenticado correctamente: {}", empleado.getNombre());
                    usuarioAutenticado = empleado;
                } else {
                    logger.warn("Autenticación fallida para el usuario: {}", username);
                    mensajeError = "Nombre de usuario o contraseña incorrectos";
                }
            }

            // Ejecutar navegación o mostrar error según el resultado
            if (usuarioAutenticado != null) {
                // Guardar el usuario autenticado en la sesión
                logger.debug("Guardando usuario autenticado en la sesión");
                Sesion.getInstance().setUsuarioAutenticado(usuarioAutenticado);

                if (usuarioAutenticado instanceof UsuarioCliente) {
                    logger.debug("Navegando a pantalla principal de cliente");
                    navegarAPantallaPrincipalCliente(actionEvent);
                } else {
                    logger.debug("Navegando a pantalla principal de empleado");
                    navegarAPantallaPrincipalEmpleado(actionEvent);
                }
            } else {
                logger.warn("Login fallido: {}", mensajeError);
                mostrarError(mensajeError);
            }
        } else {
            logger.warn("Validación de campos fallida");
        }
    }


    /**
     * Maneja el evento de clic en el botón de registrarse
     * Navega hacia la ventana de registro
     *
     * @param actionEvent El evento de acción que desencadenó este método
     */
    public void alHacerClicEnRegistrarse(ActionEvent actionEvent) {
        logger.debug("Usuario solicitó ir a pantalla de registro");
        navegarAPantallaRegistro(actionEvent);
    }

    /**
     * Valida que los campos del formulario de login no estén vacíos
     * 
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        logger.debug("Validando campos del formulario de login");
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar campos de texto
            logger.debug("Validando campos de texto");
            Utilidades.validarCampoNoVacio(usernameField.getText(), "nombre de usuario");
            Utilidades.validarCampoNoVacio(passwordField.getText(), "contraseña");

            logger.debug("Validación de campos exitosa");
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
     * Verifica si las credenciales del usuario son correctas
     *
     * @param usuario  El usuario a autenticar
     * @param password La contraseña ingresada
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    private boolean autenticarUsuario(Usuario usuario, String password) {
        logger.debug("Verificando credenciales para usuario");
        boolean autenticacionExitosa = true;

        // Verificar que el usuario esté activo
        if (usuario.getEstado() != Estado.ACTIVO) {
            logger.warn("Intento de login con usuario inactivo: {}", usuario.getNombre());
            mostrarError("Usuario inactivo. Contacte al administrador.");
            autenticacionExitosa = false;
        } else {
            // Verificar la contraseña
            boolean passwordCorrecta = HashUtil.verificarPassword(password, usuario.getPassword());
            if (!passwordCorrecta) {
                logger.debug("Autenticación fallida: contraseña incorrecta");
                autenticacionExitosa = false;
            }
        }

        return autenticacionExitosa;
    }

    /**
     * Muestra un mensaje de error en la interfaz
     *
     * @param mensaje El mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        errorMessage.setText(mensaje);
        errorMessage.setVisible(true);
    }

    /**
     * Navega a la pantalla principal de cliente después de un login exitoso
     *
     * @param event El evento que desencadenó la navegación
     */
    private void navegarAPantallaPrincipalCliente(ActionEvent event) {
        logger.debug("Iniciando navegación a pantalla principal de cliente");
        try {
            // Cargar la vista principal
            logger.debug("Cargando vista principal de cliente");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/main-view-cliente.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            logger.debug("Obteniendo controlador de vista principal de cliente");
            MainViewClienteController controller = loader.getController();

            // Configurar la nueva escena
            logger.debug("Configurando escena para pantalla principal de cliente");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Panel Principal");
            stage.setScene(scene);
            stage.show();
            logger.info("Navegación a pantalla principal de cliente completada");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla principal de cliente: {}", e.getMessage(), e);
            mostrarError("Error al cargar la pantalla principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla principal de empleado después de un login exitoso
     *
     * @param event El evento que desencadenó la navegación
     */
    private void navegarAPantallaPrincipalEmpleado(ActionEvent event) {
        logger.debug("Iniciando navegación a pantalla principal de empleado");
        try {
            // Cargar la vista principal de empleado
            logger.debug("Cargando vista principal de empleado");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/main-view-empleado.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            logger.debug("Obteniendo controlador de vista principal de empleado");
            MainViewEmpleadoController controller = loader.getController();

            // Configurar la nueva escena
            logger.debug("Configurando escena para pantalla principal de empleado");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Panel de Empleado");
            stage.setScene(scene);
            stage.show();
            logger.info("Navegación a pantalla principal de empleado completada");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla principal de empleado: {}", e.getMessage(), e);
            mostrarError("Error al cargar la pantalla principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla de registro de nuevos usuarios
     *
     * @param event El evento que desencadenó la navegación
     */
    private void navegarAPantallaRegistro(ActionEvent event) {
        logger.debug("Iniciando navegación a pantalla de registro");
        try {
            logger.debug("Cargando vista de registro");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-view.fxml"));
            Parent root = loader.load();

            logger.debug("Obteniendo controlador de registro e inicializándolo");
            RegistroController controller = loader.getController();
            controller.initialize();

            logger.debug("Configurando escena para pantalla de registro");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 800);
            stage.setTitle("Fitness360 - Registro");
            stage.setScene(scene);
            stage.show();
            logger.info("Navegación a pantalla de registro completada");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de registro: {}", e.getMessage(), e);
            mostrarError("Error al cargar la pantalla registro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
