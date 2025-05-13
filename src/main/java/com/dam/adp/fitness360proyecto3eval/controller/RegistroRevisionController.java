package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.RevisionDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.model.Revision;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class RegistroRevisionController {
    public ComboBox clienteComboBox;
    public DatePicker fechaRevisionPicker;
    public TextField pesoField;
    public TextField grasaField;
    public TextField musculoField;
    public TextField pechoField;
    public TextField cinturaField;
    public TextField caderaField;
    public TextArea observacionesField;
    public Label errorMessage;
    public Button registrarButton;
    public Button cancelarButton;


    private RevisionDAO revisionDAO = new RevisionDAO();
    private UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
    private UsuarioEmpleadoDAO empleadoDAO = new UsuarioEmpleadoDAO();

    private UsuarioEmpleado empladoAutenticado = new UsuarioEmpleado();

    @FXML
    private void initialize() {
    }

    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empladoAutenticado = empleadoAutenticado;
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
        List<UsuarioCliente> clientesConTarifasActivas = clienteDAO.findClientesByEmpleadoTarifa(idEmpleado);
        clienteComboBox.setItems(FXCollections.observableArrayList(clientesConTarifasActivas));
    }

    private void manejarRegistro() {
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (fechaRevisionPicker.getValue() == null) {
            errores.append("La fecha de la revision es obligatoria.\n");
        }
        if (pesoField.getText().trim().isEmpty()) {
            errores.append("El peso de la revision es obligatorio.\n");
        }
        if (grasaField.getText().trim().isEmpty()) {
            errores.append("El porcentaje de grasa en la revision es obligatoria.\n");
        }
        if (musculoField.getText().trim().isEmpty()) {
            errores.append("El porcentaje de musculo en la revision es obligatorio.\n");
        }
        if (pechoField.getText().trim().isEmpty()) {
            errores.append("La medida del pecho en la revision es obligatorio.\n");
        }
        if (cinturaField.getText().trim().isEmpty()) {
            errores.append("La medida de la cintura en la revision es obligatorio.\n");
        }
        if (caderaField.getText().trim().isEmpty()) {
            errores.append("La medida de la cadera en la revision es obligatorio.\n");
        }
        if (observacionesField.getText().trim().isEmpty()) {
            errores.append("Las observaciones son obligatorias.\n");
        }

        if (errores.length() > 0) {
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            return false;
        }
        return true;
    }

    public boolean registrarRevision() {
        try {
            UsuarioCliente cliente = (UsuarioCliente) clienteComboBox.getValue();
            if (cliente == null) {
                mostrarAlerta("Error", "Error al registrar la revision cliente vacio", Alert.AlertType.ERROR);
                return false;
            }

            //crear revision
            Revision revision = new Revision();
            revision.setCliente(cliente);
            revision.setEmpleado(empladoAutenticado);
            java.sql.Date fecha = java.sql.Date.valueOf(fechaRevisionPicker.getValue());
            revision.setFecha(fecha);
            revision.setPeso(Double.parseDouble(pesoField.getText().trim()));
            revision.setGrasa(Double.parseDouble(grasaField.getText().trim()));
            revision.setMusculo(Double.parseDouble(musculoField.getText().trim()));
            revision.setmPecho(Double.parseDouble(pechoField.getText().trim()));
            revision.setmCintura(Double.parseDouble(cinturaField.getText().trim()));
            revision.setmCadera(Double.parseDouble(caderaField.getText().trim()));
            revision.setObservaciones(observacionesField.getText().trim());

            //insertar revision
            revisionDAO.insert(revision);
            Revision revisionRegistrada;

            revisionRegistrada = revisionDAO.getByUserNameCreator(empladoAutenticado.getNombreUsuario(),fecha,cliente.getNombreUsuario());

            if ()

            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
        fechaRevisionPicker.setValue(null);
        pesoField.clear();
        grasaField.clear();
        musculoField.clear();
        pechoField.clear();
        cinturaField.clear();
        caderaField.clear();
        observacionesField.clear();
        clienteComboBox.getSelectionModel().clearSelection();
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
