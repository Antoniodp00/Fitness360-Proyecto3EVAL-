package com.dam.adp.fitness360proyecto3eval.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for Fitness360
 * This class serves as the entry point for the JavaFX application
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the login FXML file from the resources
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));
        Parent root = fxmlLoader.load();

        // Create a scene with the loaded FXML
        Scene scene = new Scene(root, 400, 500);

        // Set the stage properties
        stage.setTitle("Fitness360 - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
