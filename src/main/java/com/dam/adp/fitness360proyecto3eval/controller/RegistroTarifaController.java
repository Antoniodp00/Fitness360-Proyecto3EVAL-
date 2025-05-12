package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.ClienteTarifa;
import com.dam.adp.fitness360proyecto3eval.model.Periodo;
import com.dam.adp.fitness360proyecto3eval.model.Tarifa;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroTarifaController {
    public TextField nombreTarifaField;
    public TextArea descripcionTarifaField;
    public TextField precioTarifaField;
    public ComboBox<Periodo> periodoComboBox;
    public Label errorMessage;
    public Button registrarButton;
    public Button cancelarButton;


    private TarifaDAO tarifaDAO;


    private UsuarioEmpleado empleadoAutenticado;

    // Callback para actualizar la vista principal después de registrar una dieta
    private Runnable onRegistroExitoso;

    @FXML
    public void initialize() {
        tarifaDAO = new TarifaDAO();

        periodoComboBox.setItems(FXCollections.observableArrayList(Periodo.values()) );

        registrarButton.setOnAction(event -> manejarRegistro());
        cancelarButton.setOnAction(event -> manejarCancelacion());

    }

    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empleadoAutenticado = empleadoAutenticado;
    }


    public void setOnRegistroExitoso(Runnable callback) {
        this.onRegistroExitoso = callback;

    }

    public void manejarRegistro() {
        errorMessage.setVisible(false);
        if (validarCampos()) {
            boolean registroExitoso = registrarTarifa();
            if (registroExitoso) {
                mostrarAlerta("Registro Exitoso", "La tarifa ha sido registrada correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
            }
            // Ejecutar el callback si existe
            if (onRegistroExitoso != null) {
                onRegistroExitoso.run();
            }
        }
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (nombreTarifaField.getText().trim().isEmpty()) {
            errores.append("El nombre de la tarifa es obligatorio.\n");
        }
        if (descripcionTarifaField.getText().trim().isEmpty()) {
            errores.append("La descripcion de la tarifa es obligatoria.\n");
        }

        if (precioTarifaField.getText().trim().isEmpty()) {
            errores.append("El precio de la tarifa es obligatorio.\n");
        }

        if (periodoComboBox.getValue() == null) {
            errores.append("El perido de la tarifa es obligatorio.\n");
        }

        if (errores.length() > 0) {
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            return false;
        }
        return true;
    }

    public boolean registrarTarifa(){
        try {
            Tarifa tarifa = new Tarifa();
            tarifa.setNombre(nombreTarifaField.getText().trim());
            tarifa.setDescripcion(descripcionTarifaField.getText().trim());
            tarifa.setPrecio(Double.parseDouble(precioTarifaField.getText().trim()));
            tarifa.setPeriodo(periodoComboBox.getValue());
            tarifa.setCreador(empleadoAutenticado);

            tarifaDAO.insert(tarifa);
           return true;
        } catch (Exception e) {
            e.printStackTrace();
           mostrarAlerta("Error","Error al registrar la tarifa", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        nombreTarifaField.clear();
        descripcionTarifaField.clear();
        precioTarifaField.clear();
        periodoComboBox.getSelectionModel().clearSelection();
        errorMessage.setVisible(false);
    }

    /**
     * Maneja la acción de cancelar el registro.
     */
    private void manejarCancelacion() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
