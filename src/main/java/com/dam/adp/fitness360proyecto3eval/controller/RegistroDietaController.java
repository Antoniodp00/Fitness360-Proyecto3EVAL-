package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.ClienteDietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.DietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.model.ClienteDieta;
import com.dam.adp.fitness360proyecto3eval.model.Dieta;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RegistroDietaController {
    public TextField nombreDietaField;
    public TextArea descripcionDietaField;
    public VBox clienteAsignadoFields;
    public ComboBox<UsuarioCliente> clienteAsignadoComboBox;
    public Label errorMessage;
    public Button registrarButton;
    public Button cancelarButton;

    private UsuarioClienteDAO usuarioClienteDAO;
    private DietaDAO dietaDAO;
    private ClienteDietaDAO clienteDietaDAO;

    private UsuarioEmpleado empleadoAutenticado;
    private Dieta dieta;
    private ObservableList<Dieta> dietas;

    private static final Logger logger = LoggerFactory.getLogger(RegistroDietaController.class);



    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     * Inicializa los DAOs y configura los eventos de los botones.
     */
    @FXML
    private void initialize() {
        usuarioClienteDAO = new UsuarioClienteDAO();
        dietaDAO = new DietaDAO();
        clienteDietaDAO = new ClienteDietaDAO();

        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
    }

    /**
     * Establece el empleado autenticado y carga los clientes con tarifas activas para ese empleado.
     * 
     * @param empleadoAutenticado El empleado autenticado en el sistema
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleadoAutenticado) {
        this.empleadoAutenticado = empleadoAutenticado;
        cargarClientesConTarifasActivas(empleadoAutenticado.getId());
    }

    /**
     * Establece la dieta a editar y rellena los campos del formulario.
     * Si la dieta es null, limpia los campos.
     *
     * @param dieta La dieta a editar, o null para crear una nueva
     */
    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
        if (dieta != null) {
            // Rellenar los campos con los datos de la dieta
            nombreDietaField.setText(dieta.getNombre());
            descripcionDietaField.setText(dieta.getDescripcion());

            // Para obtener el cliente asignado, necesitaríamos consultar la base de datos
            // Esto se podría implementar si es necesario
        } else {
            // Limpiar los campos
            nombreDietaField.clear();
            descripcionDietaField.clear();
            clienteAsignadoComboBox.getSelectionModel().clearSelection();
        }
    }

    /**
     * Establece la lista de dietas que se actualizará al guardar.
     *
     * @param dietas La lista observable de dietas
     */
    public void setDietas(ObservableList<Dieta> dietas) {
        this.dietas = dietas;
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

    /**
     * Maneja el proceso de registro o actualización de una dieta.
     * Valida los campos, actualiza o crea una nueva dieta según corresponda,
     * y muestra mensajes de éxito o error.
     */
    private void manejarRegistro() {
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una dieta existente
            if (this.dieta != null) {
                // Actualizar los datos de la dieta existente
                try {
                    dieta.setNombre(nombreDietaField.getText().trim());
                    dieta.setDescripcion(descripcionDietaField.getText().trim());

                    // Actualizar la dieta en la base de datos
                    dietaDAO.update(dieta);
                    logger.info("Dieta actualizada correctamente: {}", dieta.getNombre());
                    registroExitoso = true;
                } catch (Exception e) {
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la dieta: " + e.getMessage(), Alert.AlertType.ERROR);
                    logger.error("Error al actualizar la dieta" + e.getMessage(), e);
                }
            } else {
                // Crear una nueva dieta
                registroExitoso = registrarDieta();
            }

            if (registroExitoso) {
                Utilidades.mostrarAlerta("Operación Exitosa", "La dieta ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) nombreDietaField.getScene().getWindow();
                stage.close();

            }
        }
    }

    /**
     * Valida que todos los campos necesarios estén completos.
     * Verifica que el nombre y descripción de la dieta no estén vacíos.
     * La asignación de cliente es opcional.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar campos de texto
            Utilidades.validarCampoNoVacio(nombreDietaField.getText(), "nombre de la dieta");
            Utilidades.validarCampoNoVacio(descripcionDietaField.getText(), "descripción de la dieta");

            // La asignación de cliente ahora es opcional
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            errores.append(e.getMessage()).append("\n");
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            valido = false;
        }

        return valido;
    }

    /**
     * Registra una nueva dieta en el sistema y opcionalmente la asigna a un cliente.
     * Crea la dieta con los datos del formulario, la guarda en la base de datos,
     * y si se ha seleccionado un cliente, crea una asignación entre la dieta y el cliente.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarDieta() {
        try {
            // Validar campos
            Utilidades.validarCampoNoVacio(nombreDietaField.getText(), "nombre de la dieta");
            Utilidades.validarCampoNoVacio(descripcionDietaField.getText(), "descripción de la dieta");

            // Obtener el cliente al que se le asignará la dieta (puede ser null)
            UsuarioCliente clienteAsignado = (UsuarioCliente) clienteAsignadoComboBox.getValue();

            // Crear dieta
            Dieta dieta = new Dieta();
            dieta.setNombre(nombreDietaField.getText().trim());
            dieta.setDescripcion(descripcionDietaField.getText().trim());
            dieta.setCreador(empleadoAutenticado);

            // Insertar la dieta
            Dieta dietaRegistrada = dietaDAO.insert(dieta);

            if (dietaRegistrada != null) {
                // Si se ha seleccionado un cliente, asignar la dieta
                if (clienteAsignado != null) {
                    ClienteDieta clienteDieta = new ClienteDieta();
                    clienteDieta.setCliente(clienteAsignado);
                    clienteDieta.setDieta(dietaRegistrada);
                    clienteDieta.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));

                    clienteDietaDAO.insert(clienteDieta);
                    logger.info("Nueva dieta registrada y asignada: {} para el cliente {}", dietaRegistrada.getNombre(), clienteAsignado.getNombre());
                } else {
                    logger.info("Nueva dieta registrada sin asignación a cliente: {}", dietaRegistrada.getNombre());
                }
                return true;
            }
            logger.warn("No se pudo recuperar la dieta recién creada con nombre: {}", dieta.getNombre());
            return false;
        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
            logger.error(e.getMessage(), e);
            return false;
        } catch (Exception e) {
            Utilidades.mostrarAlerta("Error", "Error al registrar la dieta: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Error al registrar la dieta "+ e.getMessage(), e);
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
