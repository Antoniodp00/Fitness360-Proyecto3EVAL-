package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Controlador para la vista principal del empleado
 * Maneja la visualización de clientes, rutinas, dietas, tarifas y revisiones del empleado
 */
public class MainViewEmpleadoController {
    // Componentes generales
    public TabPane tabPane;
    public Label labelUsuario;
    @FXML
    public Button btnCerrarSesion;

    // Tab Clientes
    public Tab tabClientes;
    public ComboBox comboFiltroClientes;
    public TableView tablaClientes;
    public TableColumn colNombreCliente;
    public TableColumn colApellidosCliente;
    public TableColumn colEmailCliente;
    public TableColumn colTelefonoCliente;
    public TableColumn colFechaAltaCliente;
    public TableColumn colAccionesCliente;

    // Tab Rutinas
    public Tab tabRutinas;
    public ComboBox comboFiltroRutinas;
    public Button btnCrearRutina;
    public TableView tablaRutinas;
    public TableColumn colNombreRutina;
    public TableColumn colDescripcionRutina;
    public TableColumn colFechaRutina;
    public TableColumn colClientesAsignadosRutina;
    public TableColumn colAccionesRutina;

    // Tab Dietas
    public Tab tabDietas;
    public ComboBox comboFiltroDietas;
    public Button btnCrearDieta;
    public TableView tablaDietas;
    public TableColumn colNombreDieta;
    public TableColumn colDescripcionDieta;
    public TableColumn colFechaDieta;
    public TableColumn colClientesAsignadosDieta;
    public TableColumn colAccionesDieta;

    // Tab Tarifas
    public Tab tabTarifas;
    public ComboBox comboFiltroPeriodo;
    public Button btnCrearTarifa;
    public TableView tablaTarifas;
    public TableColumn colNombreTarifa;
    public TableColumn colPrecioTarifa;
    public TableColumn colPeriodoTarifa;
    public TableColumn colDescripcionTarifa;
    public TableColumn colClientesAsignadosTarifa;
    public TableColumn colAccionesTarifa;

    // Tab Revisiones
    public Tab tabRevisiones;
    public ComboBox comboFiltroRevisiones;
    public Button btnNuevaRevision;
    public TableView tablaRevisiones;
    public TableColumn colFechaRevision;
    public TableColumn colClienteRevision;
    public TableColumn colPesoRevision;
    public TableColumn colGrasaRevision;
    public TableColumn colMusculoRevision;
    public TableColumn colObservacionesRevision;
    public TableColumn colAccionesRevision;

    private UsuarioEmpleado empleadoAutenticado;

    /**
     * Establece el empleado autenticado y actualiza la interfaz
     *
     * @param empleado El empleado autenticado
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleado) {
        this.empleadoAutenticado = empleado;
        if (empleado != null) {
            labelUsuario.setText("Usuario: " + empleado.getNombreUsuario());
            // Aquí se cargarán los datos específicos del empleado
            cargarDatosEmpleado();
        }
    }

    /**
     * Carga los datos específicos del empleado en la interfaz
     */
    private void cargarDatosEmpleado() {
        // Cargar clientes asignados al empleado
        cargarClientes();

        // Cargar rutinas creadas por el empleado
        cargarRutinas();

        // Cargar dietas creadas por el empleado
        cargarDietas();

        // Cargar tarifas del empleado
        cargarTarifas();

        // Cargar revisiones realizadas por el empleado
        cargarRevisiones();
    }

    /**
     * Carga los clientes asignados al empleado
     */
    private void cargarClientes() {
        UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();
        // Implementación para cargar los clientes del empleado
        List<UsuarioCliente> misClientes = clienteDAO.findClientesByEmpleadoTarifa(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidosCliente.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEmailCliente.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFechaAltaCliente.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Limpiar y agregar los clientes a la tabla
        tablaClientes.getItems().clear();
        tablaClientes.getItems().addAll(misClientes);
    }

    /**
     * Carga las rutinas creadas por el empleado
     */
    private void cargarRutinas() {
        RutinaDAO rutinaDAO = new RutinaDAO();
        // Implementación para cargar las rutinas creadas por el empleado
        List<Rutina> misRutinas = rutinaDAO.getByCreator(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colNombreRutina.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionRutina.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFechaRutina.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Limpiar y agregar las rutinas a la tabla
        tablaRutinas.getItems().clear();
        tablaRutinas.getItems().addAll(misRutinas);
    }

    /**
     * Carga las dietas creadas por el empleado
     */
    private void cargarDietas() {
        DietaDAO dietaDAO = new DietaDAO();
        // Implementación para cargar las dietas creadas por el empleado
        List<Dieta> misDietas = dietaDAO.getByCreator(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colNombreDieta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionDieta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFechaDieta.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Limpiar y agregar las dietas a la tabla
        tablaDietas.getItems().clear();
        tablaDietas.getItems().addAll(misDietas);
    }

    /**
     * Carga las tarifas del empleado
     */
    private void cargarTarifas() {
        TarifaDAO tarifaDAO = new TarifaDAO();
        // Implementación para cargar las tarifas del empleado
        List<Tarifa> misTarifas = tarifaDAO.getByCreator(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colNombreTarifa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioTarifa.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPeriodoTarifa.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colDescripcionTarifa.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Limpiar y agregar las tarifas a la tabla
        tablaTarifas.getItems().clear();
        tablaTarifas.getItems().addAll(misTarifas);
    }

    /**
     * Carga las revisiones realizadas por el empleado
     */
    private void cargarRevisiones() {
        RevisionDAO revisionDAO = new RevisionDAO();
        // Implementación para cargar las revisiones realizadas por el empleado
        List<Revision> misRevisiones = revisionDAO.getByCreator(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colFechaRevision.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPesoRevision.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colGrasaRevision.setCellValueFactory(new PropertyValueFactory<>("porcentajeGrasa"));
        colMusculoRevision.setCellValueFactory(new PropertyValueFactory<>("porcentajeMusculo"));
        colObservacionesRevision.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        // Limpiar y agregar las revisiones a la tabla
        tablaRevisiones.getItems().clear();
        tablaRevisiones.getItems().addAll(misRevisiones);
    }

    /**
     * Maneja el evento de clic en el botón de cerrar sesión
     * Navega de vuelta a la pantalla de login
     *
     * @param event El evento que desencadenó esta acción
     */
    @FXML
    public void cerrarSesion(ActionEvent event) {
        try {
            // Cargar la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));
            Parent root = loader.load();

            // Configurar la nueva escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inicializa el controlador y configura los eventos
     */
    @FXML
    public void initialize() {
        // Configurar el evento de clic para el botón de cerrar sesión
        btnCerrarSesion.setOnAction(this::cerrarSesion);
    }
}
