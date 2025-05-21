package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.DAO.ClienteRutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.RutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final UsuarioClienteDAO usuarioClienteDAO = new UsuarioClienteDAO();
    private final UsuarioEmpleadoDAO usuarioEmpleadoDAO = new UsuarioEmpleadoDAO();
    private final RutinaDAO rutinaDAO = new RutinaDAO();
    private final ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();


    private UsuarioCliente clienteAutenticado;
    private UsuarioEmpleado empleadoAutenticado;
    private Rutina rutina;
    private ObservableList<Rutina> rutinas;

    private static final Logger logger = LoggerFactory.getLogger(RegistroRutinaController.class);



    /**
     * Inicializa el controlador. Este método es llamado automáticamente
     * después de que el archivo FXML ha sido cargado.
     */
    @FXML
    private void initialize() {
        logger.debug("Inicializando RegistroRutinaController");

        // Cargar clientes y empleados en los ComboBox
        logger.debug("Cargando listas de clientes y empleados");
        cargarClientes();
        cargarEmpleados();

        // Configurar listeners para los radio buttons
        logger.debug("Configurando listeners para controles de UI");
        clienteRadio.setOnAction(e -> alternarCamposCreador());
        empleadoRadio.setOnAction(e -> {
            alternarCamposCreador();
            // Si hay un empleado seleccionado, cargar sus clientes con tarifas activas
            if (empleadoComboBox.getValue() != null) {
                logger.debug("Empleado seleccionado, cargando sus clientes con tarifas activas");
                cargarClientesConTarifasActivas(empleadoComboBox.getValue().getId());
            }
        });

        // Configurar listener para el combobox de empleados
        empleadoComboBox.setOnAction(e -> {
            if (empleadoComboBox.getValue() != null && empleadoRadio.isSelected()) {
                logger.debug("Cambio en selección de empleado, cargando sus clientes con tarifas activas");
                cargarClientesConTarifasActivas(empleadoComboBox.getValue().getId());
            }
        });

        // Configurar botones
        registrarButton.setOnAction(e -> manejarRegistro());
        cancelarButton.setOnAction(e -> manejarCancelacion());
        logger.debug("RegistroRutinaController inicializado correctamente");
    }

    /**
     * Establece el cliente autenticado y configura la interfaz en consecuencia.
     *
     * @param cliente El cliente autenticado
     */
    public void setClienteAutenticado(UsuarioCliente cliente) {
        logger.debug("Estableciendo cliente autenticado: {}", cliente != null ? cliente.getNombre() : "null");
        this.clienteAutenticado = cliente;
        this.empleadoAutenticado = null;

        if (cliente != null) {
            logger.debug("Configurando interfaz para cliente autenticado");
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
            logger.debug("Interfaz configurada correctamente para cliente autenticado");
        } else {
            logger.debug("No hay cliente autenticado, no se realizan cambios en la interfaz");
        }
    }

 /**
     * Establece la rutina a editar y rellena los campos del formulario.
     * Si la rutina es null, limpia los campos.
     *
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void setRutina(Rutina rutina) {
        logger.debug("Estableciendo rutina para edición: {}", rutina != null ? rutina.getNombre() : "null");
        this.rutina = rutina;
        if (rutina != null) {
            logger.debug("Rellenando campos con datos de la rutina existente");
            nombreRutinaField.setText(rutina.getNombre());
            descripcionRutinaField.setText(rutina.getDescripcion());
            logger.debug("Campos rellenados correctamente");
        } else {
            logger.debug("Limpiando campos del formulario");
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
        logger.debug("Estableciendo empleado autenticado: {}", empleado != null ? empleado.getNombre() : "null");
        this.empleadoAutenticado = empleado;
        this.clienteAutenticado = null;

        if (empleado != null) {
            logger.debug("Configurando interfaz para empleado autenticado");

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
            logger.debug("Cargando clientes con tarifas activas para el empleado ID: {}", empleado.getId());
            cargarClientesConTarifasActivas(empleado.getId());
            logger.debug("Interfaz configurada correctamente para empleado autenticado");
        } else {
            logger.debug("No hay empleado autenticado, no se realizan cambios en la interfaz");
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
        logger.debug("Cargando clientes con tarifas activas para el empleado ID: {}", idEmpleado);
        List<UsuarioCliente> clientesConTarifasActivas = usuarioClienteDAO.findClientesByEmpleadoTarifa(idEmpleado);
        clienteAsignadoComboBox.setItems(FXCollections.observableArrayList(clientesConTarifasActivas));
        logger.debug("Se han cargado {} clientes con tarifas activas", clientesConTarifasActivas.size());
    }

    /**
     * Carga la lista de clientes en el ComboBox correspondiente.
     */
    private void cargarClientes() {
        logger.debug("Cargando lista completa de clientes");
        List<UsuarioCliente> clientes = usuarioClienteDAO.getAll();
        clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
        logger.debug("Se han cargado {} clientes", clientes.size());
    }

    /**
     * Carga la lista de empleados en el ComboBox correspondiente.
     */
    private void cargarEmpleados() {
        logger.debug("Cargando lista completa de empleados");
        List<UsuarioEmpleado> empleados = usuarioEmpleadoDAO.getAll();
        empleadoComboBox.setItems(FXCollections.observableArrayList(empleados));
        logger.debug("Se han cargado {} empleados", empleados.size());
    }

    /**
     * Alterna la visibilidad de los campos según el tipo de creador seleccionado.
     */
    private void alternarCamposCreador() {
        boolean esCliente = clienteRadio.isSelected();
        logger.debug("Alternando campos de creador. Tipo seleccionado: {}", esCliente ? "Cliente" : "Empleado");
        clienteCreadorFields.setVisible(esCliente);
        clienteCreadorFields.setManaged(esCliente);
        empleadoCreadorFields.setVisible(!esCliente);
        empleadoCreadorFields.setManaged(!esCliente);

        // Mostrar el selector de cliente asignado solo cuando el creador es un empleado
        clienteAsignadoFields.setVisible(!esCliente);
        clienteAsignadoFields.setManaged(!esCliente);
        logger.debug("Campos de interfaz actualizados según el tipo de creador");
    }

    /**
     * Maneja el proceso de registro o actualización de una rutina.
     */
    private void manejarRegistro() {
        logger.debug("Iniciando proceso de registro/actualización de rutina");
        errorMessage.setVisible(false);

        if (validarCampos()) {
            boolean registroExitoso = false;

            // Si estamos editando una rutina existente
            if (this.rutina != null) {
                logger.info("Actualizando rutina existente: {}", rutina.getNombre());
                // Actualizar los datos de la rutina existente
                rutina.setNombre(nombreRutinaField.getText().trim());
                rutina.setDescripcion(descripcionRutinaField.getText().trim());

                // Actualizar la rutina en la base de datos
                try {
                    rutinaDAO.update(rutina);
                    logger.info("Rutina actualizada correctamente: {}", rutina.getNombre());
                    registroExitoso = true;
                } catch (Exception e) {
                    logger.error("Error al actualizar la rutina: {}", e.getMessage(), e);
                    e.printStackTrace();
                    Utilidades.mostrarAlerta("Error", "Error al actualizar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Verificar si el empleado es dietista (comprobación adicional de seguridad)
                if (empleadoAutenticado != null && empleadoAutenticado.getEspecialidad() == Especialidad.DIETISTA) {
                    logger.warn("Intento de crear rutina por un dietista, acceso denegado");
                    Utilidades.mostrarAlerta("Acceso denegado", "Los dietistas no pueden crear rutinas.", Alert.AlertType.ERROR);
                    Stage stage = (Stage) nombreRutinaField.getScene().getWindow();
                    stage.close();
                    return;
                }

                // Determinar el tipo de creador basado en el usuario autenticado
                if (clienteAutenticado != null || clienteRadio.isSelected()) {
                    logger.info("Creando nueva rutina por cliente");
                    registroExitoso = registrarRutinaPorCliente();
                } else {
                    logger.info("Creando nueva rutina por empleado");
                    registroExitoso = registrarRutinaPorEmpleado();
                }
            }

            if (registroExitoso) {
                logger.info("Operación de rutina completada con éxito");
                Utilidades.mostrarAlerta("Operación Exitosa", "La rutina ha sido guardada correctamente.", Alert.AlertType.INFORMATION);

                // Cerrar la ventana
                Stage stage = (Stage) nombreRutinaField.getScene().getWindow();
                stage.close();
            } else {
                logger.warn("La operación de rutina no fue exitosa");
            }
        } else {
            logger.warn("Validación de campos fallida");
        }
    }



    /**
     * Valida que todos los campos necesarios estén completos.
     * La asignación de cliente es opcional.
     *
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        logger.debug("Validando campos del formulario de rutina");
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        try {
            // Validar nombre de la rutina
            Utilidades.validarCampoNoVacio(nombreRutinaField.getText(), "nombre de la rutina");

            // Validar descripción
            Utilidades.validarCampoNoVacio(descripcionRutinaField.getText(), "descripción de la rutina");

            // Validar selección de creador
            if (clienteAutenticado != null) {
                // Si hay un cliente autenticado, ya está preseleccionado
                logger.debug("Cliente autenticado, no se requiere validación adicional de creador");
            } else if (empleadoAutenticado != null) {
                // Si hay un empleado autenticado, ya está preseleccionado
                logger.debug("Empleado autenticado, la asignación de cliente es opcional");
                // La asignación de cliente ahora es opcional
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Validación fallida: {}", e.getMessage());
            errores.append(e.getMessage()).append("\n");
            errorMessage.setText(errores.toString());
            errorMessage.setVisible(true);
            logger.warn("Validación de campos fallida: {}", errores);
            valido = false;
        }

        if (valido) {
            logger.debug("Validación de campos exitosa");
        }
        return valido;
    }

    /**
     * Registra una nueva rutina creada por un cliente y la asigna automáticamente al mismo cliente.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarRutinaPorCliente() {
        logger.debug("Iniciando registro de rutina por cliente");
        boolean registroExitoso = false;

        try {
            // Crear la rutina
            logger.debug("Creando objeto de rutina con los datos del formulario");
            Rutina rutina = new Rutina();
            rutina.setNombre(nombreRutinaField.getText().trim());
            rutina.setDescripcion(descripcionRutinaField.getText().trim());

            // Determinar el cliente creador
            UsuarioCliente cliente;
            if (clienteAutenticado != null) {
                logger.debug("Usando cliente autenticado como creador: {}", clienteAutenticado.getNombre());
                cliente = clienteAutenticado;
            } else {
                logger.debug("Usando cliente seleccionado como creador: {}", clienteComboBox.getValue().getNombre());
                cliente = clienteComboBox.getValue();
            }

            // Establecer el cliente creador
            rutina.setCreadorCliente(cliente);

            // Registrar la rutina
            logger.debug("Insertando rutina en la base de datos");
            rutinaDAO.insertRutinaByClient(rutina);
            Rutina rutinaRegistrada;
            //Vuelvo a buscar la rutina creada para obtener el id
            rutinaRegistrada = rutinaDAO.getByName(rutina.getNombre());
            if (rutinaRegistrada != null) {
                logger.debug("Rutina registrada correctamente, asignando al cliente");
                // Asignar la rutina al mismo cliente que la creó
                ClienteRutina clienteRutina = new ClienteRutina();
                clienteRutina.setCliente(cliente);
                clienteRutina.setRutina(rutinaRegistrada);
                clienteRutina.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));

                // Insertar la asignación en la base de datos
                clienteRutinaDAO.insert(clienteRutina);
                logger.info("Rutina '{}' registrada y asignada correctamente al cliente {}", rutina.getNombre(), cliente.getNombre());
                registroExitoso = true;
            } else {
                logger.warn("No se pudo recuperar la rutina recién creada con nombre: {}", rutina.getNombre());
            }
        } catch (Exception e) {
            logger.error("Error al registrar la rutina por cliente: {}", e.getMessage(), e);
            Utilidades.mostrarAlerta("Error", "Error al registrar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return registroExitoso;
    }

    /**
     * Registra una nueva rutina creada por un empleado y opcionalmente la asigna al cliente seleccionado.
     *
     * @return true si el registro fue exitoso, false en caso contrario
     */
    private boolean registrarRutinaPorEmpleado() {
        logger.debug("Iniciando registro de rutina por empleado");
        boolean registroExitoso = false;

        try {
            // Obtener el cliente al que se asignará la rutina (puede ser null)
            UsuarioCliente clienteAsignado = clienteAsignadoComboBox.getValue();

            // Crear la rutina
            logger.debug("Creando objeto de rutina con los datos del formulario");
            Rutina rutina = new Rutina();
            rutina.setNombre(nombreRutinaField.getText().trim());
            rutina.setDescripcion(descripcionRutinaField.getText().trim());

            // Usar el empleado autenticado
            logger.debug("Estableciendo empleado creador: {}", empleadoAutenticado.getNombre());
            rutina.setCreadorEmpleado(empleadoAutenticado);

            // Registrar la rutina
            logger.debug("Insertando rutina en la base de datos");

            Rutina rutinaRegistrada=rutinaDAO.insertRutinaByEmployee(rutina);

            if (rutinaRegistrada != null) {
                // Si se ha seleccionado un cliente, asignar la rutina
                if (clienteAsignado != null) {
                    logger.debug("Rutina registrada correctamente, asignando al cliente seleccionado");
                    // Asignar la rutina al cliente seleccionado
                    ClienteRutina clienteRutina = new ClienteRutina();
                    clienteRutina.setCliente(clienteAsignado);
                    clienteRutina.setRutina(rutinaRegistrada);
                    clienteRutina.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));
                    // Insertar la asignación en la base de datos
                    clienteRutinaDAO.insert(clienteRutina);
                    logger.info("Rutina '{}' registrada y asignada correctamente al cliente {}", rutina.getNombre(), clienteAsignado.getNombre());
                } else {
                    logger.info("Rutina '{}' registrada sin asignación a cliente", rutina.getNombre());
                }
                registroExitoso = true;
            } else {
                logger.warn("No se pudo recuperar la rutina recién creada con nombre: {}", rutina.getNombre());
            }
        } catch (Exception e) {
            logger.error("Error al registrar la rutina por empleado: {}", e.getMessage(), e);
            Utilidades.mostrarAlerta("Error", "Error al registrar la rutina: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return registroExitoso;
    }

    /**
     * Maneja la acción de cancelar el registro.
     */
    private void manejarCancelacion() {
        logger.debug("Cancelando registro de rutina");
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
        logger.info("Ventana de registro de rutina cerrada");
    }

}
