package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la vista principal del cliente.
 * Maneja la interfaz que muestra las rutinas, dietas, revisiones y entrenadores disponibles para el cliente.
 * Permite al cliente ver sus datos y contratar servicios.
 */
public class MainViewClienteController {
    //Componentes Generales
    public TabPane tabPane;
    public Label labelUsuario;
    public ComboBox comboFiltroRutinas;

    //Tab Rutinas
    public Tab tabRutinas;
    public TableView tablaRutinas;
    public TableColumn colNombreRutina;
    public TableColumn colDescripcionRutina;
    public TableColumn colCreadorRutina;
    public TableColumn colFechaInicioRutina;
    public TableColumn colFechaFinRutina;

    //Tab Dietas
    public Tab tabDietas;
    public TableView tablaDietas;
    public TableColumn colNombreDieta;
    public TableColumn colDescripcionDieta;
    public TableColumn colCreadorDieta;
    public TableColumn colFechaInicioDieta;
    public TableColumn colFechaFinDieta;

    //Tab Revisiones
    public Tab tabRevisiones;
    public TableView tablaRevisiones;
    public TableColumn colFechaRevision;
    public TableColumn colPesoRevision;
    public TableColumn colGrasaRevision;
    public TableColumn colMusculoRevision;
    public TableColumn colEntrenadorRevision;
    public TableColumn colObservacionesRevision;
    public ComboBox comboFiltroEspecialidad;

    //Tab Entrenadores
    public Tab tabContratarEntrenador;
    public TableView tablaEntrenadores;
    public TableColumn colNombreEntrenador;
    public TableColumn colEspecialidadEntrenador;
    public TableColumn colDescripcionEntrenador;
    public TableColumn colAccionesEntrenador;
    public ComboBox comboFiltroPeriodo;

    //Tab Tarifas
    public TableView tablaTarifas;
    public TableColumn colNombreTarifa;
    public TableColumn colPrecioTarifa;
    public TableColumn colPeriodoTarifa;
    public TableColumn colEntrenadorTarifa;
    public TableColumn colDescripcionTarifa;
    public TableColumn colAccionesTarifa;

    //Botones
    public Button btnCerrarSesion;
    public Button btnCrearRutina;
    public Button btnModificarRutina;
    public Button btnEliminarRutina;
    public Button btnContratarTarifa;

    private UsuarioCliente clienteAutenticado;

    /**
     * Establece el cliente autenticado y actualiza la interfaz
     *
     * @param cliente El cliente autenticado
     */
    public void setClienteAutenticado(UsuarioCliente cliente) {
        this.clienteAutenticado = cliente;
        if (cliente != null) {
            labelUsuario.setText("Usuario: " + cliente.getNombreUsuario());
            // Aquí se pueden cargar los datos específicos del cliente
            cargarDatosCliente();
        }
    }

    /**
     * Carga todos los datos relacionados con el cliente autenticado.
     * Incluye rutinas, dietas, revisiones y entrenadores disponibles.
     */
    public void cargarDatosCliente() {
        cargarRutinas();
        cargarDietas();
        cargarRevisiones();
        cargarEntrenadores();
    }

    /**
     * Carga las rutinas asignadas al cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarRutinas() {
        ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();

        List<ClienteRutina> misRutinas = clienteRutinaDAO.findByClientEager(clienteAutenticado.getId());

        colNombreRutina.setCellValueFactory(new PropertyValueFactory<>("nombreRutina"));
        colDescripcionRutina.setCellValueFactory(new PropertyValueFactory<>("descripcionRutina"));
        colCreadorRutina.setCellValueFactory(new PropertyValueFactory<>("creadorRutina"));
        colFechaInicioRutina.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        colFechaFinRutina.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));


        tablaRutinas.getItems().clear();
        tablaRutinas.getItems().addAll(misRutinas);

    }

    /**
     * Carga las dietas asignadas al cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarDietas() {
        ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();
        List<ClienteDieta> misRutinas = clienteDietaDAO.findByClientEager(clienteAutenticado.getId());

        colNombreDieta.setCellValueFactory(new PropertyValueFactory<>("nombreDieta"));
        colDescripcionDieta.setCellValueFactory(new PropertyValueFactory<>("descripcionDieta"));
        colCreadorDieta.setCellValueFactory(new PropertyValueFactory<>("creadorDieta"));
        colFechaInicioDieta.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        colFechaFinDieta.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));

        tablaDietas.getItems().clear();
        tablaDietas.getItems().addAll(misRutinas);

    }

    /**
     * Carga las revisiones físicas del cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarRevisiones() {
        RevisionDAO revisionDAO = new RevisionDAO();
        List<Revision> misRevisiones = revisionDAO.getByClientEager(clienteAutenticado.getId());

        colFechaRevision.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPesoRevision.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colGrasaRevision.setCellValueFactory(new PropertyValueFactory<>("grasa"));
        colMusculoRevision.setCellValueFactory(new PropertyValueFactory<>("musculo"));
        colEntrenadorRevision.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleadoCompleto"));
        colObservacionesRevision.setCellValueFactory(new PropertyValueFactory<>("observaciones"));


        tablaRevisiones.getItems().clear();
        tablaRevisiones.getItems().addAll(misRevisiones);

    }

    /**
     * Carga la lista de entrenadores disponibles en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    public void cargarEntrenadores() {
        UsuarioEmpleadoDAO usuarioEmpleadoDAO = new UsuarioEmpleadoDAO();
        List<UsuarioEmpleado> empleados = usuarioEmpleadoDAO.getAll();

        colNombreEntrenador.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEspecialidadEntrenador.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colDescripcionEntrenador.setCellValueFactory(new PropertyValueFactory<>("descripcion"));


        tablaEntrenadores.getItems().clear();
        tablaEntrenadores.getItems().addAll(empleados);
    }

    /**
     * Carga las tarifas disponibles para un entrenador específico.
     *
     * @param empleado El entrenador del que se cargarán las tarifas
     */
    public void cargarTarifasEntrenador(UsuarioEmpleado empleado) {
        TarifaDAO tarifaDAO = new TarifaDAO();
        List<Tarifa> tarifas = tarifaDAO.getByCreator(empleado.getId());

        colNombreTarifa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioTarifa.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPeriodoTarifa.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colDescripcionTarifa.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tablaTarifas.getItems().clear();
        tablaTarifas.getItems().addAll(tarifas);
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
     * Abre la ventana de registro de rutina pasando el cliente autenticado
     */
    public void abrirRegistroRutina(ActionEvent event) {
        try {
            // Cargar la vista de registro de rutina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-rutina-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y establecer el cliente autenticado
            RegistroRutinaController controller = loader.getController();
            controller.setClienteAutenticado(clienteAutenticado);

            // Configurar y mostrar la nueva ventana
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Registro de Rutina");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de rutina: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void contratarTarifa(ActionEvent event) {
        ClienteTarifaDAO clienteTarifaDAO = new ClienteTarifaDAO();
        Tarifa tarifaSeleccionada = (Tarifa) tablaTarifas.getSelectionModel().getSelectedItem();

        if (tarifaSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, seleccione una tarifa", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Verificar si ya existe la asignación
            List<ClienteTarifa> tarifasCliente = clienteTarifaDAO.findByCliente(clienteAutenticado.getId());
            boolean tarifaYaExiste = false;

            for (ClienteTarifa ct : tarifasCliente) {
                if (ct.getTarifa().getIdTarifa() == tarifaSeleccionada.getIdTarifa()
                        && ct.getEstado() == EstadoTarifa.ACTIVA) {
                    tarifaYaExiste = true;
                    break;
                }
            }

            if (tarifaYaExiste) {
                mostrarAlerta("Error", "El cliente ya tiene esta tarifa contratada", Alert.AlertType.ERROR);
                return;
            }

            // Crear nueva asignación
            ClienteTarifa clienteTarifa = new ClienteTarifa();
            clienteTarifa.setCliente(clienteAutenticado);
            clienteTarifa.setTarifa(tarifaSeleccionada);
            clienteTarifa.setFechaContratacion(new Date(System.currentTimeMillis()));
            clienteTarifa.setEstado(EstadoTarifa.ACTIVA);

            clienteTarifaDAO.insert(clienteTarifa);
            mostrarAlerta("Tarifa Asignada", "Tarifa asignada correctamente", Alert.AlertType.INFORMATION);

        } catch (RuntimeException e) {
            System.err.println("Error al asignar la tarifa: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo asignar la tarifa", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Inicializa el controlador y configura los eventos
     */
    @FXML
    public void initialize() {
        // Configurar el evento de clic para el botón de cerrar sesión
        btnCerrarSesion.setOnAction(this::cerrarSesion);

        //Configurar la seleccion de empleado para mostrar sus tarifas
        tablaEntrenadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                UsuarioEmpleado empleado = (UsuarioEmpleado) newValue;
                cargarTarifasEntrenador(empleado);
            }
        });

        //Configurar el evento de click para abrir el panel de registro de rutina
        btnCrearRutina.setOnAction(this::abrirRegistroRutina);

        //Configurar el evento de click para
        btnContratarTarifa.setOnAction(this::contratarTarifa);

    }
}