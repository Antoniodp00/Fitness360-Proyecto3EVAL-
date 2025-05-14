package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.exceptions.*;
import com.dam.adp.fitness360proyecto3eval.model.ClienteTarifa;
import com.dam.adp.fitness360proyecto3eval.model.Periodo;
import com.dam.adp.fitness360proyecto3eval.model.Tarifa;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
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
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la tarifa: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Crear una nueva tarifa
                registroExitoso = registrarTarifa();
            }

            if (registroExitoso) {
                Utilidades.mostrarAlerta("Operación Exitosa", "La tarifa ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

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
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar campos de texto
            Utilidades.validarCampoNoVacio(nombreTarifaField.getText(), "nombre de la tarifa");
            Utilidades.validarCampoNoVacio(descripcionTarifaField.getText(), "descripción de la tarifa");

            // Validar precio como número decimal positivo
            Utilidades.validarDecimalPositivo(precioTarifaField.getText(), "precio");

            // Validar selección de periodo
            Utilidades.validarComboBox(periodoComboBox, "periodo");

        } catch (IllegalArgumentException e) {
            errores.append(e.getMessage()).append("\n");
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            valido = false;
        }

        return valido;
    }

    /**
     * Registra una nueva tarifa en el sistema.
     * Crea la tarifa con los datos del formulario y la guarda en la base de datos.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarTarifa(){
        try {
            // Validar y obtener el precio como número decimal positivo
            double precio = Utilidades.validarDecimalPositivo(precioTarifaField.getText(), "precio");

            Tarifa tarifa = new Tarifa();
            tarifa.setNombre(nombreTarifaField.getText().trim());
            tarifa.setDescripcion(descripcionTarifaField.getText().trim());
            tarifa.setPrecio(precio);
            tarifa.setPeriodo(periodoComboBox.getValue());
            tarifa.setCreador(empleadoAutenticado);

            tarifaDAO.insert(tarifa);
            return true;
        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlerta("Error","Error al registrar la tarifa: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Maneja la acción de cancelar el registro.
     */
    private void manejarCancelacion() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
