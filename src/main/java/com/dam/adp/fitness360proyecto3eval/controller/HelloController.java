package com.dam.adp.fitness360proyecto3eval.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void alHacerClicEnSaludo() {
        welcomeText.setText("Welcome to Fitness360!");
    }
}
