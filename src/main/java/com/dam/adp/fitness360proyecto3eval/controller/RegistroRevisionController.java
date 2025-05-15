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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(RegistroRevisionController.class);



    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     * Inicializa los DAOs y configura los eventos de los botones.
     */
    @FXML
    private void initialize() {
        logger.debug("Inicializando RegistroRevisionController");
        revisionDAO = new RevisionDAO();
        clienteDAO = new UsuarioClienteDAO();
        empleadoDAO = new UsuarioEmpleadoDAO();

        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
        logger.debug("RegistroRevisionController inicializado correctamente");
    }

    /**
     * Establece el empleado autenticado y carga los clientes con tarifas activas para ese empleado.
     * 
     * @param empleadoAutenticado El empleado autenticado en el sistema
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        logger.debug("Estableciendo empleado autenticado: {}", empleadoAutenticado.getNombre());
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
            logger.debug("Configurando formulario para editar revisión existente");
            // Rellenar los campos con los datos de la revisión
            clienteComboBox.setValue(revision.getCliente());
            // Convertir java.sql.Date a java.time.LocalDate
            if (revision.getFecha() != null) {
                LocalDate localDate = LocalDate.ofInstant(
                    revision.getFecha().toInstant(), ZoneId.systemDefault());
                fechaRevisionPicker.setValue(localDate);
            } else {
                logger.warn("La revisión no tiene fecha, se usará la fecha actual");
                fechaRevisionPicker.setValue(LocalDate.now());
            }
            pesoField.setText(String.valueOf(revision.getPeso()));
            grasaField.setText(String.valueOf(revision.getGrasa()));
            musculoField.setText(String.valueOf(revision.getMusculo()));
            pechoField.setText(String.valueOf(revision.getmPecho()));
            cinturaField.setText(String.valueOf(revision.getmCintura()));
            caderaField.setText(String.valueOf(revision.getmCadera()));
            observacionesField.setText(revision.getObservaciones());
            logger.debug("Formulario configurado correctamente para edición");
        } else {
            logger.debug("Configurando formulario para nueva revisión");
            // Limpiar los campos
            fechaRevisionPicker.setValue(null);
            pesoField.clear();
            grasaField.clear();
            musculoField.clear();
            pechoField.clear();
            cinturaField.clear();
            caderaField.clear();
            observacionesField.clear();
            logger.debug("Formulario limpiado correctamente");
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
        logger.debug("Cargando clientes con tarifas activas para el empleado ID: {}", idEmpleado);
        List<UsuarioCliente> clientesConTarifasActivas = clienteDAO.findClientesByEmpleadoTarifa(idEmpleado);
        clienteComboBox.setItems(FXCollections.observableArrayList(clientesConTarifasActivas));
        logger.debug("Se han cargado {} clientes con tarifas activas", clientesConTarifasActivas.size());
    }

    /**
     * Maneja el proceso de registro o actualización de una revisión.
     * Valida los campos, actualiza o crea una nueva revisión según corresponda,
     * y muestra mensajes de éxito o error.
     */
    private void manejarRegistro() {
        logger.debug("Iniciando proceso de registro/actualización de revisión");
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una revisión existente
            if (this.revision != null) {
                logger.info("Actualizando revisión existente");
                // Actualizar los datos de la revisión existente
                try {
                    UsuarioCliente cliente = clienteComboBox.getValue();
                    if (cliente == null) {
                        logger.error("Error al actualizar revisión: cliente no seleccionado");
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
                    logger.info("Revisión actualizada correctamente para el cliente: {}", cliente.getNombre());
                    registroExitoso = true;
                } catch (Exception e) {
                    logger.error("Error al actualizar la revisión: {}", e.getMessage(), e);
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la revisión: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                logger.info("Creando nueva revisión");
                // Crear una nueva revisión
                registroExitoso = registrarRevision();
            }

            if (registroExitoso) {
                logger.info("Operación de revisión completada con éxito");
                Utilidades.mostrarAlerta("Operación Exitosa", "La revisión ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) pesoField.getScene().getWindow();
                stage.close();
            } else {
                logger.warn("La operación de revisión no fue exitosa");
            }
        } else {
            logger.warn("Validación de campos fallida");
        }
    }

    /**
     * Valida que todos los campos necesarios estén completos.
     * Verifica que la fecha, peso, grasa, músculo y medidas corporales no estén vacíos.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        logger.debug("Validando campos del formulario de revisión");
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar fecha
            Utilidades.validarFecha(fechaRevisionPicker.getValue(), "fecha de revisión");

            // Validar campos de texto
            Utilidades.validarCampoNoVacio(pesoField.getText(), "peso");
            Utilidades.validarCampoNoVacio(grasaField.getText(), "porcentaje de grasa");
            Utilidades.validarCampoNoVacio(musculoField.getText(), "porcentaje de músculo");
            Utilidades.validarCampoNoVacio(pechoField.getText(), "medida de pecho");
            Utilidades.validarCampoNoVacio(cinturaField.getText(), "medida de cintura");
            Utilidades.validarCampoNoVacio(caderaField.getText(), "medida de cadera");
            Utilidades.validarCampoNoVacio(observacionesField.getText(), "observaciones");

            // Validar campos numéricos
            Utilidades.validarDecimalPositivo(pesoField.getText(), "peso");
            Utilidades.validarDecimalPositivo(grasaField.getText(), "porcentaje de grasa");
            Utilidades.validarDecimalPositivo(musculoField.getText(), "porcentaje de músculo");
            Utilidades.validarDecimalPositivo(pechoField.getText(), "medida de pecho");
            Utilidades.validarDecimalPositivo(cinturaField.getText(), "medida de cintura");
            Utilidades.validarDecimalPositivo(caderaField.getText(), "medida de cadera");

        } catch (IllegalArgumentException e) {
            logger.warn("Validación fallida: {}", e.getMessage());
            errores.append(e.getMessage()).append("\n");
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            logger.warn("Validación de campos fallida: {}", errores.toString());
            valido = false;
        }

        if (valido) {
            logger.debug("Validación de campos exitosa");
        }
        return valido;
    }

    /**
     * Registra una nueva revisión en el sistema.
     * Crea la revisión con los datos del formulario y la guarda en la base de datos.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarRevision() {
        logger.debug("Iniciando registro de nueva revisión");
        try {
            UsuarioCliente cliente = (UsuarioCliente) clienteComboBox.getValue();
            if (cliente == null) {
                logger.error("Error al registrar revisión: cliente no seleccionado");
                Utilidades.mostrarAlerta("Error", "Error al registrar la revision cliente vacio", Alert.AlertType.ERROR);
                return false;
            }

            //crear revision
            logger.debug("Creando objeto de revisión con los datos del formulario");
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
            logger.debug("Insertando revisión en la base de datos");
            revisionDAO.insert(revision);
            logger.info("Revisión registrada correctamente para el cliente: {}", cliente.getNombre());

            return true;
        } catch (Exception e) {
            logger.error("Error al registrar la revisión: {}", e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Maneja la acción de cancelar el registro.
     */
    private void manejarCancelacion() {
        logger.debug("Cancelando registro de revisión");
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
        logger.info("Ventana de registro de revisión cerrada");
    }

}
