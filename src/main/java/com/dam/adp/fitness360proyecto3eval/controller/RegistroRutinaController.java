package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.DAO.ClienteRutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.RutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controlador para la vista de registro de rutinas.
 * Gestiona la interfaz de usuario para crear nuevas rutinas en el sistema.
 */
public class RegistroRutinaController {

    @FXML
    private RadioButton clienteRadio;

    @FXML
    private RadioButton empleadoRadio;

    @FXML
    private TextField nombreRutinaField;

    @FXML
    private TextArea descripcionRutinaField;

    @FXML
    private ComboBox<UsuarioCliente> clienteComboBox;

    @FXML
    private ComboBox<UsuarioEmpleado> empleadoComboBox;

    @FXML
    private VBox clienteCreadorFields;

    @FXML
    private VBox empleadoCreadorFields;

    @FXML
    private VBox clienteAsignadoFields;

    @FXML
    private ComboBox<UsuarioCliente> clienteAsignadoComboBox;

    @FXML
    private Button registrarButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private Label errorMessage;

    @FXML
    private HBox creadorSelectionBox;

    private UsuarioClienteDAO usuarioClienteDAO;
    private UsuarioEmpleadoDAO usuarioEmpleadoDAO;
    private RutinaDAO rutinaDAO;
    private ClienteRutinaDAO clienteRutinaDAO;

    private UsuarioCliente clienteAutenticado;
    private UsuarioEmpleado empleadoAutenticado;
    private Rutina rutina;
    private ObservableList<Rutina> rutinas;



    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     */
    @FXML
    private void initialize() {
        // Inicializar DAOs
        usuarioClienteDAO = new UsuarioClienteDAO();
        usuarioEmpleadoDAO = new UsuarioEmpleadoDAO();
        rutinaDAO = new RutinaDAO();
        clienteRutinaDAO = new ClienteRutinaDAO();

        // Cargar clientes y empleados en los ComboBox
        cargarClientes();
        cargarEmpleados();

        // Configurar listeners para los radio buttons
        clienteRadio.setOnAction(e -> alternarCamposCreador());
        empleadoRadio.setOnAction(e -> {
            alternarCamposCreador();
            // Si hay un empleado seleccionado, cargar sus clientes con tarifas activas
            if (empleadoComboBox.getValue() != null) {
                cargarClientesConTarifasActivas(empleadoComboBox.getValue().getId());
            }
        });

        // Configurar listener para el combobox de empleados
        empleadoComboBox.setOnAction(e -> {
            if (empleadoComboBox.getValue() != null && empleadoRadio.isSelected()) {
                cargarClientesConTarifasActivas(empleadoComboBox.getValue().getId());
            }
        });

        // Configurar botones
        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
    }

    /**
     * Establece el cliente autenticado y configura la interfaz en consecuencia.
     *
     * @param cliente El cliente autenticado
     */
    public void setClienteAutenticado(UsuarioCliente cliente) {
        this.clienteAutenticado = cliente;
        this.empleadoAutenticado = null;

        if (cliente != null) {
            // Ocultar la selección de tipo de creador
            if (creadorSelectionBox != null) {
                creadorSelectionBox.setVisible(false);
                creadorSelectionBox.setManaged(false);
            }

            // Seleccionar automáticamente el tipo cliente
            clienteRadio.setSelected(true);
            alternarCamposCreador();

            // Preseleccionar el cliente autenticado en el ComboBox
            clienteComboBox.setValue(cliente);
            clienteComboBox.setDisable(true); // Deshabilitar cambio
        }
    }

 /**
     * Establece la rutina a editar y rellena los campos del formulario.
     * Si la rutina es null, limpia los campos.
     *
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
        if (rutina != null) {
            nombreRutinaField.setText(rutina.getNombre());
            descripcionRutinaField.setText(rutina.getDescripcion());
        } else {
            nombreRutinaField.setText("");
            descripcionRutinaField.setText("");
        }
    }

    /**
     * Establece el empleado autenticado y configura la interfaz en consecuencia.
     *
     * @param empleado El empleado autenticado
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleado) {
        this.empleadoAutenticado = empleado;
        this.clienteAutenticado = null;

        if (empleado != null) {

            // Ocultar la selección de tipo de creador
            if (creadorSelectionBox != null) {
                creadorSelectionBox.setVisible(false);
                creadorSelectionBox.setManaged(false);
            }

            // Seleccionar automáticamente el tipo empleado
            empleadoRadio.setSelected(true);
            alternarCamposCreador();

            // Preseleccionar el empleado autenticado en el ComboBox
            empleadoComboBox.setValue(empleado);
            empleadoComboBox.setDisable(true); // Deshabilitar cambio

            // Cargar los clientes con tarifas activas para este empleado
            cargarClientesConTarifasActivas(empleado.getId());
        }
    }


    /**
     * Establece la lista de rutinas que se actualizará al guardar.
     *
     * @param rutinas La lista observable de rutinas
     */
    public void setRutinas(ObservableList<Rutina> rutinas) {
        this.rutinas = rutinas;
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
     * Carga la lista de clientes en el ComboBox correspondiente.
     */
    private void cargarClientes() {
        List<UsuarioCliente> clientes = usuarioClienteDAO.getAll();
        clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
    }

    /**
     * Carga la lista de empleados en el ComboBox correspondiente.
     */
    private void cargarEmpleados() {
        List<UsuarioEmpleado> empleados = usuarioEmpleadoDAO.getAll();
        empleadoComboBox.setItems(FXCollections.observableArrayList(empleados));
    }

    /**
     * Alterna la visibilidad de los campos según el tipo de creador seleccionado.
     */
    private void alternarCamposCreador() {
        boolean esCliente = clienteRadio.isSelected();
        clienteCreadorFields.setVisible(esCliente);
        clienteCreadorFields.setManaged(esCliente);
        empleadoCreadorFields.setVisible(!esCliente);
        empleadoCreadorFields.setManaged(!esCliente);

        // Mostrar el selector de cliente asignado solo cuando el creador es un empleado
        clienteAsignadoFields.setVisible(!esCliente);
        clienteAsignadoFields.setManaged(!esCliente);
    }

    /**
     * Maneja el proceso de registro o actualización de una rutina.
     */
    private void manejarRegistro() {
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una rutina existente
            if (this.rutina != null) {
                // Actualizar los datos de la rutina existente
                rutina.setNombre(nombreRutinaField.getText().trim());
                rutina.setDescripcion(descripcionRutinaField.getText().trim());

                // Actualizar la rutina en la base de datos
                try {
                    rutinaDAO.update(rutina);
                    registroExitoso = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Verificar si el empleado es dietista (comprobación adicional de seguridad)
                if (empleadoAutenticado != null && empleadoAutenticado.getEspecialidad() == Especialidad.DIETISTA) {
                    Utilidades.mostrarAlerta("Acceso denegado", "Los dietistas no pueden crear rutinas.", Alert.AlertType.ERROR);
                    Stage stage = (Stage) nombreRutinaField.getScene().getWindow();
                    stage.close();
                    return;
                }

                // Determinar el tipo de creador basado en el usuario autenticado
                if (clienteAutenticado != null || clienteRadio.isSelected()) {
                    registroExitoso = registrarRutinaPorCliente();
                } else {
                    registroExitoso = registrarRutinaPorEmpleado();
                }
            }

            if (registroExitoso) {
                Utilidades.mostrarAlerta("Operación Exitosa", "La rutina ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) nombreRutinaField.getScene().getWindow();
                stage.close();

            }
        }
    }



    /**
     * Valida que todos los campos necesarios estén completos.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar nombre de la rutina
        if (nombreRutinaField.getText().trim().isEmpty()) {
            errores.append("El nombre de la rutina es obligatorio.\n");
        }

        // Validar descripción
        if (descripcionRutinaField.getText().trim().isEmpty()) {
            errores.append("La descripción de la rutina es obligatoria.\n");
        }

        // Validar selección de creador
        if (clienteAutenticado != null) {
            // Si hay un cliente autenticado, ya está preseleccionado
        } else if (empleadoAutenticado != null) {
            // Si hay un empleado autenticado, ya está preseleccionado
            // Validar que se haya seleccionado un cliente para asignar la rutina
            if (clienteAsignadoComboBox.getValue() == null) {
                errores.append("Debe seleccionar un cliente al que asignar la rutina.\n");
            }
        }

        if (errores.length() > 0) {
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            return false;
        }

        return true;
    }

    /**
     * Registra una nueva rutina creada por un cliente y la asigna automáticamente al mismo cliente.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarRutinaPorCliente() {
        try {
            // Crear la rutina
            Rutina rutina = new Rutina();
            rutina.setNombre(nombreRutinaField.getText().trim());
            rutina.setDescripcion(descripcionRutinaField.getText().trim());

            // Determinar el cliente creador
            UsuarioCliente cliente;
            if (clienteAutenticado != null) {
                cliente = clienteAutenticado;
            } else {
                cliente = clienteComboBox.getValue();
            }

            // Establecer el cliente creador
            rutina.setCreadorCliente(cliente);

            // Registrar la rutina
            rutinaDAO.insertRutinaByClient(rutina);
            Rutina rutinaRegistrada;
            //Vuelvo a buscar la rutina creada para obtener el id
            rutinaRegistrada = rutinaDAO.getByName(rutina.getNombre());
            if (rutinaRegistrada != null) {
                // Asignar la rutina al mismo cliente que la creó
                ClienteRutina clienteRutina = new ClienteRutina();
                clienteRutina.setCliente(cliente);
                clienteRutina.setRutina(rutinaRegistrada);
                clienteRutina.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));

                // Insertar la asignación en la base de datos
                clienteRutinaDAO.insert(clienteRutina);

                return true;
            }
            return false;
        } catch (Exception e) {
            Utilidades.mostrarAlerta("Error", "Error al registrar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Registra una nueva rutina creada por un empleado y la asigna al cliente seleccionado.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarRutinaPorEmpleado() {
        try {
            // Obtener el cliente al que se asignará la rutina
            UsuarioCliente clienteAsignado = clienteAsignadoComboBox.getValue();
            if (clienteAsignado == null) {
                Utilidades.mostrarAlerta("Error", "Debe seleccionar un cliente al que asignar la rutina.", Alert.AlertType.ERROR);
                return false;
            }

            // Crear la rutina
            Rutina rutina = new Rutina();
            rutina.setNombre(nombreRutinaField.getText().trim());
            rutina.setDescripcion(descripcionRutinaField.getText().trim());

            // Usar el empleado autenticado

            rutina.setCreadorEmpleado(empleadoAutenticado);


            // Registrar la rutina
            rutinaDAO.insertRutinaByEmployee(rutina);
            Rutina rutinaRegistrada;
            //Vuelvo a buscar la rutina creada para obtener el id
            rutinaRegistrada = rutinaDAO.getByName(rutina.getNombre());

            if (rutinaRegistrada != null) {
                // Asignar la rutina al cliente seleccionado
                ClienteRutina clienteRutina = new ClienteRutina();
                clienteRutina.setCliente(clienteAsignado);
                clienteRutina.setRutina(rutinaRegistrada);
                clienteRutina.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));
                // Insertar la asignación en la base de datos
                clienteRutinaDAO.insert(clienteRutina);

                return true;
            }
            return false;
        } catch (Exception e) {
            Utilidades.mostrarAlerta("Error", "Error al registrar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
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
