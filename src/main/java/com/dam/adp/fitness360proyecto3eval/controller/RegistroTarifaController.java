package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.ClienteTarifa;
import com.dam.adp.fitness360proyecto3eval.model.Periodo;
import com.dam.adp.fitness360proyecto3eval.model.Tarifa;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Tarifa tarifa;
    private ObservableList<Tarifa> tarifas;


    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     * Inicializa los DAOs, configura el ComboBox de periodos y los eventos de los botones.
     */
    @FXML
    public void initialize() {
        tarifaDAO = new TarifaDAO();

        periodoComboBox.setItems(FXCollections.observableArrayList(Periodo.values()) );

        registrarButton.setOnAction(event -> manejarRegistro());
        cancelarButton.setOnAction(event -> manejarCancelacion());

    }

    /**
     * Establece el empleado autenticado que creará la tarifa.
     * 
     * @param empleadoAutenticado El empleado autenticado en el sistema
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empleadoAutenticado = empleadoAutenticado;
    }



    /**
     * Establece la tarifa a editar y rellena los campos del formulario.
     * Si la tarifa es null, limpia los campos.
     *
     * @param tarifa La tarifa a editar, o null para crear una nueva
     */
    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
        if (tarifa != null) {
            // Rellenar los campos con los datos de la tarifa
            nombreTarifaField.setText(tarifa.getNombre());
            descripcionTarifaField.setText(tarifa.getDescripcion());
            precioTarifaField.setText(String.valueOf(tarifa.getPrecio()));
            periodoComboBox.setValue(tarifa.getPeriodo());
        } else {
            // Limpiar los campos
            nombreTarifaField.clear();
            descripcionTarifaField.clear();
            precioTarifaField.clear();
            periodoComboBox.getSelectionModel().clearSelection();
        }
    }

    /**
     * Establece la lista de tarifas que se actualizará al guardar.
     *
     * @param tarifas La lista observable de tarifas
     */
    public void setTarifas(ObservableList<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    /**
     * Maneja el proceso de registro o actualización de una tarifa.
     * Valida los campos, actualiza o crea una nueva tarifa según corresponda,
     * y muestra mensajes de éxito o error.
     */
    public void manejarRegistro() {
        errorMessage.setVisible(false);
        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una tarifa existente
            if (this.tarifa != null) {
                // Actualizar los datos de la tarifa existente
                try {
                    tarifa.setNombre(nombreTarifaField.getText().trim());
                    tarifa.setDescripcion(descripcionTarifaField.getText().trim());
                    tarifa.setPrecio(Double.parseDouble(precioTarifaField.getText().trim()));
                    tarifa.setPeriodo(periodoComboBox.getValue());

                    // Actualizar la tarifa en la base de datos
                    tarifaDAO.update(tarifa);
                    registroExitoso = true;
                } catch (Exception e) {
                    mostrarAlerta("Error", "Error al actualizar la tarifa: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Crear una nueva tarifa
                registroExitoso = registrarTarifa();
            }

            if (registroExitoso) {
                mostrarAlerta("Operación Exitosa", "La tarifa ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) nombreTarifaField.getScene().getWindow();
                stage.close();


            }
        }
    }

    /**
     * Valida que todos los campos necesarios estén completos.
     * Verifica que el nombre, descripción, precio y periodo de la tarifa no estén vacíos.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
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

    /**
     * Registra una nueva tarifa en el sistema.
     * Crea la tarifa con los datos del formulario y la guarda en la base de datos.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
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

    /**
     * Muestra una alerta con el título, mensaje y tipo especificados.
     * 
     * @param titulo Título de la alerta
     * @param mensaje Mensaje de la alerta
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR, etc.)
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Limpia todos los campos del formulario.
     * Restablece los campos de texto, deselecciona el periodo en el ComboBox,
     * y oculta los mensajes de error.
     */
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
