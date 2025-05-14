package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.RevisionDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.model.Revision;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RegistroRevisionController {
    public ComboBox<UsuarioCliente> clienteComboBox;
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


    private RevisionDAO revisionDAO;
    private UsuarioClienteDAO clienteDAO;
    private UsuarioEmpleadoDAO empleadoDAO;

    private UsuarioEmpleado empladoAutenticado;
    private Revision revision;
    private ObservableList<Revision> revisiones;



    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     * Inicializa los DAOs y configura los eventos de los botones.
     */
    @FXML
    private void initialize() {
        revisionDAO = new RevisionDAO();
        clienteDAO = new UsuarioClienteDAO();
        empleadoDAO = new UsuarioEmpleadoDAO();

        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
    }

    /**
     * Establece el empleado autenticado y carga los clientes con tarifas activas para ese empleado.
     * 
     * @param empleadoAutenticado El empleado autenticado en el sistema
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empladoAutenticado = empleadoAutenticado;
        cargarClientesConTarifasActivas(empleadoAutenticado.getId());
    }




    /**
     * Establece la revisión a editar y rellena los campos del formulario.
     * Si la revisión es null, limpia los campos.
     *
     * @param revision La revisión a editar, o null para crear una nueva
     */
    public void setRevision(Revision revision) {
        this.revision = revision;
        if (revision != null) {
            // Rellenar los campos con los datos de la revisión
            clienteComboBox.setValue(revision.getCliente());
            // Convertir java.sql.Date a java.time.LocalDate
            if (revision.getFecha() != null) {
                LocalDate localDate = LocalDate.ofInstant(
                    revision.getFecha().toInstant(), ZoneId.systemDefault());
                fechaRevisionPicker.setValue(localDate);
            } else {
                fechaRevisionPicker.setValue(LocalDate.now());
            }
            pesoField.setText(String.valueOf(revision.getPeso()));
            grasaField.setText(String.valueOf(revision.getGrasa()));
            musculoField.setText(String.valueOf(revision.getMusculo()));
            pechoField.setText(String.valueOf(revision.getmPecho()));
            cinturaField.setText(String.valueOf(revision.getmCintura()));
            caderaField.setText(String.valueOf(revision.getmCadera()));
            observacionesField.setText(revision.getObservaciones());
        } else {
            // Limpiar los campos
            fechaRevisionPicker.setValue(null);
            pesoField.clear();
            grasaField.clear();
            musculoField.clear();
            pechoField.clear();
            cinturaField.clear();
            caderaField.clear();
            observacionesField.clear();
        }
    }

    /**
     * Establece la lista de revisiones que se actualizará al guardar.
     *
     * @param revisiones La lista observable de revisiones
     */
    public void setRevisiones(ObservableList<Revision> revisiones) {
        this.revisiones = revisiones;
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

    /**
     * Maneja el proceso de registro o actualización de una revisión.
     * Valida los campos, actualiza o crea una nueva revisión según corresponda,
     * y muestra mensajes de éxito o error.
     */
    private void manejarRegistro() {
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una revisión existente
            if (this.revision != null) {
                // Actualizar los datos de la revisión existente
                try {
                    UsuarioCliente cliente = clienteComboBox.getValue();
                    if (cliente == null) {
                        Utilidades.mostrarAlerta("Error", "Error al registrar la revisión: cliente vacío", Alert.AlertType.ERROR);
                        return;
                    }

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

                    // Actualizar la revisión en la base de datos
                    revisionDAO.update(revision);
                    registroExitoso = true;
                } catch (Exception e) {
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la revisión: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Crear una nueva revisión
                registroExitoso = registrarRevision();
            }

            if (registroExitoso) {
                Utilidades.mostrarAlerta("Operación Exitosa", "La revisión ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) pesoField.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Valida que todos los campos necesarios estén completos.
     * Verifica que la fecha, peso, grasa, músculo y medidas corporales no estén vacíos.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
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

    /**
     * Registra una nueva revisión en el sistema.
     * Crea la revisión con los datos del formulario y la guarda en la base de datos.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarRevision() {
        try {
            UsuarioCliente cliente = (UsuarioCliente) clienteComboBox.getValue();
            if (cliente == null) {
                Utilidades.mostrarAlerta("Error", "Error al registrar la revision cliente vacio", Alert.AlertType.ERROR);
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

            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
