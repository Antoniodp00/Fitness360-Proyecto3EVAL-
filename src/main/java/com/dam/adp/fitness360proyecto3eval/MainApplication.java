package com.dam.adp.fitness360proyecto3eval;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Aplicación principal de Fitness360.
 * 
 * Esta clase es el punto de entrada de la aplicación y se encarga de inicializar
 * la interfaz gráfica de usuario. La aplicación sigue un patrón MVC (Modelo-Vista-Controlador)
 * donde:
 * - Los modelos (package model) representan los datos y la lógica de negocio
 * - Las vistas (archivos FXML) definen la interfaz de usuario
 * - Los controladores (package controller) manejan la interacción entre modelos y vistas
 * 
 * El flujo de la aplicación comienza con la pantalla de login, que permite a los usuarios
 * (clientes y empleados) autenticarse en el sistema. Dependiendo del tipo de usuario,
 * se mostrará una interfaz diferente con las funcionalidades correspondientes.
 */
public class MainApplication extends Application {

    /** Logger para registro de eventos */
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    /**
     * Método que inicia la aplicación JavaFX.
     * Carga la vista de login como pantalla inicial y configura la ventana principal.
     * 
     * @param stage El escenario principal de la aplicación
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Iniciando aplicación Fitness360");

        try {
            // Carga el FXML del login
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));
            Parent root = fxmlLoader.load();

            // Obtener el controlador y llamar a su método initialize
            com.dam.adp.fitness360proyecto3eval.controller.LoginController loginController = fxmlLoader.getController();
            loginController.initialize();
            logger.debug("Vista de login cargada correctamente y controlador inicializado");

            // Creación de la escena
            Scene scene = new Scene(root, 600, 700);

            // Propiedades del stage
            stage.setTitle("Fitness360 - Login");
            stage.setScene(scene);
            stage.show();

            logger.info("Aplicación iniciada correctamente");
        } catch (IOException e) {
            logger.error("Error al iniciar la aplicación: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Método main que lanza la aplicación JavaFX.
     * Este es el punto de entrada real de la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        logger.info("Lanzando aplicación Fitness360");
        launch(args);
    }
}
