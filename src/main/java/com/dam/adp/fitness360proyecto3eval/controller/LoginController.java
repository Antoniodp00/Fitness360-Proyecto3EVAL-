package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.model.Estado;
import com.dam.adp.fitness360proyecto3eval.model.Usuario;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import com.dam.adp.fitness360proyecto3eval.model.Sesion;
import com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil;
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

/**
 * Controlador para la pantalla de login
 * Maneja la autenticación de usuarios (clientes y empleados)
 */
public class LoginController {
    /**
     * Campo para ingresar la contraseña del usuario
     */
    @FXML
    public PasswordField passwordField;

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

    /**
     * Maneja el evento de clic en el botón de login
     * Verifica las credenciales del usuario y navega a la pantalla principal si son correctas
     *
     * @param actionEvent El evento de acción que desencadenó este método
     */
    public void alHacerClicEnIniciarSesion(ActionEvent actionEvent) {
        UsuarioEmpleadoDAO empleadoDAO = new UsuarioEmpleadoDAO();
        UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
        String username = usernameField.getText();
        String password = passwordField.getText();

        String mensajeError = null;
        Usuario usuarioAutenticado = null;

        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            mensajeError = "Por favor, complete todos los campos";
        } else {
            // Buscar y autenticar como cliente
            UsuarioCliente cliente = clienteDAO.findByUserName(username);

            if (cliente != null && autenticarUsuario(cliente, password)) {
                usuarioAutenticado = cliente;
            } else {
                // Buscar y autenticar como empleado
                UsuarioEmpleado empleado = empleadoDAO.findByUserName(username);
                if (empleado != null && autenticarUsuario(empleado, password)) {
                    usuarioAutenticado = empleado;
                } else {
                    mensajeError = "Nombre de usuario o contraseña incorrectos";
                }
            }
        }

        // Ejecutar navegación o mostrar error según el resultado
        if (usuarioAutenticado != null) {
            // Guardar el usuario autenticado en la sesión
            Sesion.getInstance().setUsuarioAutenticado(usuarioAutenticado);

            if (usuarioAutenticado instanceof UsuarioCliente) {
                navegarAPantallaPrincipalCliente(actionEvent);
            } else {
                navegarAPantallaPrincipalEmpleado(actionEvent);
            }
        } else {
            mostrarError(mensajeError);
        }
    }


    /**
     * Maneja el evento de clic en el botón de registrarse
     * Navega hacia la ventana de registro
     *
     * @param actionEvent El evento de acción que desencadenó este método
     */
    public void alHacerClicEnRegistrarse(ActionEvent actionEvent) {
        navegarAPantallaRegistro(actionEvent);
    }

    /**
     * Verifica si las credenciales del usuario son correctas
     *
     * @param usuario  El usuario a autenticar
     * @param password La contraseña ingresada
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    private boolean autenticarUsuario(Usuario usuario, String password) {
        // Verificar que el usuario esté activo
        if (usuario.getEstado() != Estado.ACTIVO) {
            mostrarError("Usuario inactivo. Contacte al administrador.");
            return false;
        }
        // Verificar la contraseña
        return HashUtil.verificarPassword(password, usuario.getPassword());
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
        try {
            // Cargar la vista principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/main-view-cliente.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            MainViewClienteController controller = loader.getController();

            // El usuario autenticado ya está en la sesión, no es necesario pasarlo al controlador
            // controller.setClienteAutenticado(usuario);

            // Configurar la nueva escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Panel Principal");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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
        try {
            // Cargar la vista principal de empleado
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/main-view-empleado.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            MainViewEmpleadoController controller = loader.getController();

            // El usuario autenticado ya está en la sesión, no es necesario pasarlo al controlador
            // controller.setEmpleadoAutenticado(usuario);

            // Configurar la nueva escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Panel de Empleado");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-view.fxml"));
            Parent root = loader.load();

            RegistroController controller = loader.getController();
            controller.initialize();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 800);
            stage.setTitle("Fitness360 - Registro");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarError("Error al cargar la pantalla registro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
