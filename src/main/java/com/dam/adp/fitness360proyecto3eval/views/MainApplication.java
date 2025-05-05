package com.dam.adp.fitness360proyecto3eval.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicacion principal de la aplicacion Fitness360
 * Punto de entrada
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga el FXML del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));
        Parent root = fxmlLoader.load();

        // Creacion de la escena
        Scene scene = new Scene(root, 400, 500);

        // Propiedades del stage
        stage.setTitle("Fitness360 - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo main que lanza la aplicacion
     */
    public static void main(String[] args) {
        launch();
    }
}
