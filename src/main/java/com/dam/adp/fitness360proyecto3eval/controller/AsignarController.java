package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.ClienteDietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.ClienteRutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador para la vista de asignación de rutinas o dietas a clientes
 */
public class AsignarController {
    private static final Logger logger = LoggerFactory.getLogger(AsignarController.class);

    @FXML
    private Label labelTitulo;
    @FXML
    private TableView<ClienteSeleccionable> tablaClientes;
    @FXML
    private TableColumn<ClienteSeleccionable, Boolean> colSeleccion;
    @FXML
    private TableColumn<ClienteSeleccionable, String> colNombre;
    @FXML
    private TableColumn<ClienteSeleccionable, String> colApellidos;
    @FXML
    private TableColumn<ClienteSeleccionable, String> colEmail;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAsignar;

    private final ObservableList<ClienteSeleccionable> clientes = FXCollections.observableArrayList();

    private final UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
    private final ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();
    private final ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();

    private UsuarioEmpleado empleadoAutenticado;
    private Rutina rutinaSeleccionada;
    private Dieta dietaSeleccionada;
    private boolean esDieta = false;

    /**
     * Inicializa el controlador
     */
    @FXML
    public void initialize() {
        logger.debug("Inicializando AsignarController");

        // Configurar la columna de selección con checkboxes
        colSeleccion.setCellValueFactory(param -> param.getValue().seleccionadoProperty());
        colSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleccion));
        colSeleccion.setEditable(true);

        // Configurar las demás columnas
        colNombre.setCellValueFactory(cellData->
                new SimpleObjectProperty<>(cellData.getValue().getNombre())
                );
        colApellidos.setCellValueFactory(cellData->
                new SimpleObjectProperty<>(cellData.getValue().getApellidos())
                );
        colEmail.setCellValueFactory(cellData->
                new SimpleObjectProperty<>(cellData.getValue().getEmail())
                );

        // Hacer la tabla editable para los checkboxes
        tablaClientes.setEditable(true);

        // Configurar eventos de botones
        btnCancelar.setOnAction(event -> cerrarVentana());
        btnAsignar.setOnAction(event -> asignarSeleccionados());

        logger.info("AsignarController inicializado correctamente");
    }

    /**
     * Establece el empleado autenticado
     * @param empleado El empleado autenticado
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleado) {
        logger.debug("Estableciendo empleado autenticado");
        this.empleadoAutenticado = empleado;
    }

    /**
     * Establece la rutina seleccionada para asignar
     * @param rutina La rutina seleccionada
     */
    public void setRutina(Rutina rutina) {
        logger.debug("Estableciendo rutina seleccionada: {}", rutina.getNombre());
        this.rutinaSeleccionada = rutina;
        this.esDieta = false;
        labelTitulo.setText("Asignar Rutina: " + rutina.getNombre());
        cargarClientes();
    }

    /**
     * Establece la dieta seleccionada para asignar
     * @param dieta La dieta seleccionada
     */
    public void setDieta(Dieta dieta) {
        logger.debug("Estableciendo dieta seleccionada: {}", dieta.getNombre());
        this.dietaSeleccionada = dieta;
        this.esDieta = true;
        labelTitulo.setText("Asignar Dieta: " + dieta.getNombre());
        cargarClientes();
    }

    /**
     * Carga los clientes del empleado autenticado
     */
    private void cargarClientes() {
        logger.debug("Cargando clientes para asignar");

        List<UsuarioCliente> misClientes = clienteDAO.findClientesByEmpleadoTarifa(empleadoAutenticado.getId());

        // Limpiar la lista observable
        clientes.clear();

        // Obtener los clientes ya asignados
        List<Integer> clientesYaAsignados = new ArrayList<>();
        if (esDieta) {
            List<ClienteDieta> asignaciones = clienteDietaDAO.findByDieta(dietaSeleccionada.getIdDieta());
            for (ClienteDieta asignacion : asignaciones) {
                clientesYaAsignados.add(asignacion.getCliente().getId());
            }
        } else {
            List<ClienteRutina> asignaciones = clienteRutinaDAO.findByRutine(rutinaSeleccionada.getIdRutina());
            for (ClienteRutina asignacion : asignaciones) {
                clientesYaAsignados.add(asignacion.getCliente().getId());
            }
        }

        // Convertir a ClienteSeleccionable y agregar a la lista observable
        for (UsuarioCliente cliente : misClientes) {
            boolean yaAsignado = clientesYaAsignados.contains(cliente.getId());
            clientes.add(new ClienteSeleccionable(cliente, yaAsignado));
        }

        // Asignar la lista a la tabla
        tablaClientes.setItems(clientes);

        logger.info("Se han cargado {} clientes para asignar", misClientes.size());
    }

    /**
     * Asigna la rutina o dieta a los clientes seleccionados
     */
    private void asignarSeleccionados() {
        logger.debug("Asignando a clientes seleccionados");
        List<ClienteSeleccionable> seleccionados = new ArrayList<>();
        List<ClienteSeleccionable> deseleccionados = new ArrayList<>();

        // Separar clientes seleccionados y deseleccionados
        for (ClienteSeleccionable cliente : clientes) {
            if (cliente.isSeleccionado()) {
                seleccionados.add(cliente);
            } else {
                deseleccionados.add(cliente);
            }
        }

        try {
            if (esDieta) {
                asignarDieta(seleccionados, deseleccionados);
            } else {
                asignarRutina(seleccionados, deseleccionados);
            }

            Utilidades.mostrarAlerta("Asignación completada", 
                    "La asignación se ha realizado correctamente", 
                    Alert.AlertType.INFORMATION);

            cerrarVentana();
        } catch (Exception e) {
            logger.error("Error al asignar: {}", e.getMessage(), e);
            Utilidades.mostrarAlerta("Error", 
                    "Ha ocurrido un error al realizar la asignación: " + e.getMessage(), 
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Asigna la rutina a los clientes seleccionados
     * @param seleccionados Clientes seleccionados
     * @param deseleccionados Clientes deseleccionados
     */
    private void asignarRutina(List<ClienteSeleccionable> seleccionados, List<ClienteSeleccionable> deseleccionados) {
        logger.debug("Asignando rutina a {} clientes", seleccionados.size());

        // Eliminar asignaciones de clientes deseleccionados
        for (ClienteSeleccionable cliente : deseleccionados) {
            if (cliente.isYaAsignado()) {
                logger.debug("Eliminando asignación de rutina para cliente ID: {}", cliente.getCliente().getId());
                clienteRutinaDAO.delete(cliente.getCliente().getId(), rutinaSeleccionada.getIdRutina());
            }
        }

        // Crear nuevas asignaciones para clientes seleccionados
        for (ClienteSeleccionable cliente : seleccionados) {
            if (!cliente.isYaAsignado()) {
                logger.debug("Creando asignación de rutina para cliente ID: {}", cliente.getCliente().getId());
                ClienteRutina cr = new ClienteRutina();
                cr.setCliente(cliente.getCliente());
                cr.setRutina(rutinaSeleccionada);
                cr.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));
                clienteRutinaDAO.insert(cr);
            }
        }

        logger.info("Rutina asignada correctamente a {} clientes", seleccionados.size());
    }

    /**
     * Asigna la dieta a los clientes seleccionados
     * @param seleccionados Clientes seleccionados
     * @param deseleccionados Clientes deseleccionados
     */
    private void asignarDieta(List<ClienteSeleccionable> seleccionados, List<ClienteSeleccionable> deseleccionados) {
        logger.debug("Asignando dieta a {} clientes", seleccionados.size());
        ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();

        // Eliminar asignaciones de clientes deseleccionados
        for (ClienteSeleccionable cliente : deseleccionados) {
            if (cliente.isYaAsignado()) {
                logger.debug("Eliminando asignación de dieta para cliente ID: {}", cliente.getCliente().getId());
                clienteDietaDAO.deleteByIds(cliente.getCliente().getId(), dietaSeleccionada.getIdDieta());
            }
        }

        // Crear nuevas asignaciones para clientes seleccionados
        for (ClienteSeleccionable cliente : seleccionados) {
            if (!cliente.isYaAsignado()) {
                logger.debug("Creando asignación de dieta para cliente ID: {}", cliente.getCliente().getId());
                ClienteDieta cd = new ClienteDieta();
                cd.setCliente(cliente.getCliente());
                cd.setDieta(dietaSeleccionada);
                cd.setFechaAsignacion(new java.sql.Date(System.currentTimeMillis()));
                clienteDietaDAO.insert(cd);
            }
        }

        logger.info("Dieta asignada correctamente a {} clientes", seleccionados.size());
    }

    /**
     * Cierra la ventana actual
     */
    private void cerrarVentana() {
        logger.debug("Cerrando ventana de asignación");
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}
