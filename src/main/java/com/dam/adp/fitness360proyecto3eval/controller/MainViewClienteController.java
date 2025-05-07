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
import java.util.ArrayList;
import java.util.List;

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
    public TableColumn colAccionesRevision;
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
    public Button btnCerrarSesion;

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

    public void cargarDatosCliente() {

        cargarRutinas();
        cargarDietas();
        cargarRevisiones();
        cargarEntrenadores();
    }

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

    private void cargarRevisiones() {

        RevisionDAO revisionDAO = new RevisionDAO();
        List<Revision> misRevisiones = revisionDAO.getByClientEager(clienteAutenticado.getId());

        colFechaRevision.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPesoRevision.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colGrasaRevision.setCellValueFactory(new PropertyValueFactory<>("grasa"));
        colMusculoRevision.setCellValueFactory(new PropertyValueFactory<>("musculo"));
        colEntrenadorRevision.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleadoCompleto"));
        colObservacionesRevision.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colAccionesRevision.setCellValueFactory(new PropertyValueFactory<>("acciones"));

        tablaRevisiones.getItems().clear();
        tablaRevisiones.getItems().addAll(misRevisiones);

    }

    public void cargarEntrenadores() {

        UsuarioEmpleadoDAO usuarioEmpleadoDAO = new UsuarioEmpleadoDAO();
        List<UsuarioEmpleado> empleados = usuarioEmpleadoDAO.getAll();

        colNombreEntrenador.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEspecialidadEntrenador.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colDescripcionEntrenador.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colAccionesEntrenador.setCellValueFactory(new PropertyValueFactory<>("acciones"));

        tablaEntrenadores.getItems().clear();
        tablaEntrenadores.getItems().addAll(empleados);
    }

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
    public void abrirRegistroRutina() {
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

    /**
     * Inicializa el controlador y configura los eventos
     */
    @FXML
    public void initialize() {
        // Configurar el evento de clic para el botón de cerrar sesión
        btnCerrarSesion.setOnAction(this::cerrarSesion);

        tablaEntrenadores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        cargarTarifasEntrenador((UsuarioEmpleado) newValue);
                    }
                }
        );



    }
}
