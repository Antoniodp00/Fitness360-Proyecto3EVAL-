package com.dam.adp.fitness360proyecto3eval.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador para la vista de ejemplo Hello.
 * Maneja la interacción básica con la interfaz de usuario de bienvenida.
 */
public class HelloController {
    /**
     * Etiqueta que muestra el texto de bienvenida en la interfaz.
     */
    @FXML
    private Label welcomeText;

    /**
     * Método que se ejecuta cuando se hace clic en el botón de saludo.
     * Actualiza el texto de bienvenida en la interfaz.
     */
    @FXML
    protected void alHacerClicEnSaludo() {
        welcomeText.setText("Welcome to Fitness360!");
    }
}
