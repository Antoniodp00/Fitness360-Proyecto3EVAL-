package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.ClienteDietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.DietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.model.ClienteDieta;
import com.dam.adp.fitness360proyecto3eval.model.Dieta;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class RegistroDietaController {
    public TextField nombreDietaField;
    public TextArea descripcionDietaField;
    public VBox clienteAsignadoFields;
    public ComboBox clienteAsignadoComboBox;
    public Label errorMessage;
    public Button registrarButton;
    public Button cancelarButton;

    private UsuarioClienteDAO usuarioClienteDAO;
    private DietaDAO dietaDAO;
    private ClienteDietaDAO clienteDietaDAO;

    private UsuarioEmpleado empleadoAutenticado;

    // Callback para actualizar la vista principal después de registrar una dieta
    private Runnable onRegistroExitoso;

    @FXML
    private void initialize() {
        usuarioClienteDAO = new UsuarioClienteDAO();
        dietaDAO = new DietaDAO();
        clienteDietaDAO = new ClienteDietaDAO();

        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
    }

    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empleadoAutenticado = empleadoAutenticado;
        cargarClientesConTarifasActivas(empleadoAutenticado.getId());
    }

    /**
     * Establece el callback que se ejecutará después de un registro exitoso.
     *
     * @param callback El callback a ejecutar
     */
    public void setOnRegistroExitoso(Runnable callback) {
        this.onRegistroExitoso = callback;
    }

    /**
     * Carga la lista de clientes con tarifas activas para un empleado específico.
     *
     * @param idEmpleado ID del empleado
     */
    private void cargarClientesConTarifasActivas(int idEmpleado) {
        List<UsuarioCliente> clientesConTarifasActivas = usuarioClienteDAO.findClientesByEmpleadoTarifa(idEmpleado);
        clienteAsignadoComboBox.setItems(FXCollections.observableArrayList(clientesConTarifasActivas));
    }

    private void manejarRegistro() {
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = registrarDieta();

            if (registroExitoso) {
                mostrarAlerta("Registro Exitoso", "La dieta ha sido registrada correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();

                // Ejecutar el callback si existe
                if (onRegistroExitoso != null) {
                    onRegistroExitoso.run();
                }
            }
        }
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        //Validar nombre de la dieta
        if (nombreDietaField.getText().trim().isEmpty()) {
            errores.append("El nombre de la dieta es obligatorio.\n");
        }

        if (descripcionDietaField.getText().trim().isEmpty()) {
            errores.append("La descripcion de la dieta es obligatoria.\n");
        }
        if (empleadoAutenticado != null) {
            // Si hay un empleado autenticado, ya está preseleccionado
            // Validar que se haya seleccionado un cliente para asignar la rutina
            if (clienteAsignadoComboBox.getValue() == null) {
                errores.append("Debe seleccionar un cliente al que asignar la dieta.\n");
            }
        }

        if (errores.length() > 0) {
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            return false;
        }
        return true;
    }

    public boolean registrarDieta() {
        try {
            //Obtener el cliente al que se le asignara la dieta
            UsuarioCliente clienteAsignado = (UsuarioCliente) clienteAsignadoComboBox.getValue();

            if (clienteAsignado == null) {
                mostrarAlerta("Error", "Debe seleccionar un cliente al que asignar la dieta.", Alert.AlertType.ERROR);
                return false;
            }
            //Crear dieta
            Dieta dieta = new Dieta();
            dieta.setNombre(nombreDietaField.getText().trim());
            dieta.setDescripcion(descripcionDietaField.getText().trim());

            dieta.setCreador(empleadoAutenticado);

            //Insertar la dieta
            dietaDAO.insert(dieta);
            Dieta dietaRegistrada;
            //Obtener la dieta con el id
            dietaRegistrada = dietaDAO.getByName(dieta.getNombre());

            if (dietaRegistrada != null) {
                //Asignar la dieta al cliente
                ClienteDieta clienteDieta = new ClienteDieta();
                clienteDieta.setCliente(clienteAsignado);
                clienteDieta.setDieta(dietaRegistrada);
                clienteDieta.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));

                clienteDietaDAO.insert(clienteDieta);
                return true;
            }
            return false;
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar la dieta", Alert.AlertType.ERROR);
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
        nombreDietaField.clear();
        descripcionDietaField.clear();
        clienteAsignadoComboBox.getSelectionModel().clearSelection();
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
