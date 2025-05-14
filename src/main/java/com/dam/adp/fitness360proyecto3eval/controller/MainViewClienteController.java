package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
    public TableView<ClienteRutina> tablaRutinas;
    public TableColumn<ClienteRutina, String> colNombreRutina;
    public TableColumn<ClienteRutina, String> colDescripcionRutina;
    public TableColumn<ClienteRutina, String> colCreadorRutina;
    public TableColumn<ClienteRutina, Date> colFechaInicioRutina;
    public TableColumn<ClienteRutina, Date> colFechaFinRutina;
    private ObservableList<ClienteRutina> rutinas = FXCollections.observableArrayList();

    //Tab Dietas
    public Tab tabDietas;
    public TableView<ClienteDieta> tablaDietas;
    public TableColumn<ClienteDieta, String> colNombreDieta;
    public TableColumn<ClienteDieta, String> colDescripcionDieta;
    public TableColumn<ClienteDieta, String> colCreadorDieta;
    public TableColumn<ClienteDieta, Date> colFechaInicioDieta;
    public TableColumn<ClienteDieta, Date> colFechaFinDieta;
    private ObservableList<ClienteDieta> dietas = FXCollections.observableArrayList();

    //Tab Revisiones
    public Tab tabRevisiones;
    public TableView<Revision> tablaRevisiones;
    public TableColumn<Revision, Date> colFechaRevision;
    public TableColumn<Revision, Double> colPesoRevision;
    public TableColumn<Revision, Double> colGrasaRevision;
    public TableColumn<Revision, Double> colMusculoRevision;
    public TableColumn<Revision, String> colEntrenadorRevision;
    public TableColumn<Revision, String> colObservacionesRevision;
    public ComboBox comboFiltroEspecialidad;
    private ObservableList<Revision> revisiones = FXCollections.observableArrayList();

    //Tab Entrenadores
    public Tab tabContratarEntrenador;
    public TableView<UsuarioEmpleado> tablaEntrenadores;
    public TableColumn<UsuarioEmpleado, String> colNombreEntrenador;
    public TableColumn<UsuarioEmpleado, Especialidad> colEspecialidadEntrenador;
    public TableColumn<UsuarioEmpleado, String> colDescripcionEntrenador;
    public TableColumn<UsuarioEmpleado, String> colAccionesEntrenador;
    public ComboBox comboFiltroPeriodo;
    private ObservableList<UsuarioEmpleado> entrenadores = FXCollections.observableArrayList();

    //Tab Tarifas
    public TableView<Tarifa> tablaTarifas;
    public TableColumn<Tarifa, String> colNombreTarifa;
    public TableColumn<Tarifa, Double> colPrecioTarifa;
    public TableColumn<Tarifa, Periodo> colPeriodoTarifa;
    public TableColumn<Tarifa, String> colEntrenadorTarifa;
    public TableColumn<Tarifa, String> colDescripcionTarifa;
    public TableColumn<Tarifa, String> colAccionesTarifa;
    private ObservableList<Tarifa> tarifas = FXCollections.observableArrayList();

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
            // Aquí se cargan los datos específicos del cliente
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

        // Limpiar y agregar las rutinas a la lista observable
        rutinas.clear();
        rutinas.addAll(misRutinas);

        // Asignar la lista observable a la tabla
        tablaRutinas.setItems(rutinas);
    }

    /**
     * Carga las dietas asignadas al cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarDietas() {
        ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();
        List<ClienteDieta> misDietas = clienteDietaDAO.findByClientEager(clienteAutenticado.getId());

        colNombreDieta.setCellValueFactory(new PropertyValueFactory<>("nombreDieta"));
        colDescripcionDieta.setCellValueFactory(new PropertyValueFactory<>("descripcionDieta"));
        colCreadorDieta.setCellValueFactory(new PropertyValueFactory<>("creadorDieta"));
        colFechaInicioDieta.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        colFechaFinDieta.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));

        // Limpiar y agregar las dietas a la lista observable
        dietas.clear();
        dietas.addAll(misDietas);

        // Asignar la lista observable a la tabla
        tablaDietas.setItems(dietas);
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

        // Limpiar y agregar las revisiones a la lista observable
        revisiones.clear();
        revisiones.addAll(misRevisiones);

        // Asignar la lista observable a la tabla
        tablaRevisiones.setItems(revisiones);
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

        // Limpiar y agregar los entrenadores a la lista observable
        entrenadores.clear();
        entrenadores.addAll(empleados);

        // Asignar la lista observable a la tabla
        tablaEntrenadores.setItems(entrenadores);
    }

    /**
     * Carga las tarifas disponibles para un entrenador específico.
     *
     * @param empleado El entrenador del que se cargarán las tarifas
     */
    public void cargarTarifasEntrenador(UsuarioEmpleado empleado) {
        TarifaDAO tarifaDAO = new TarifaDAO();
        List<Tarifa> tarifasEmpleado = tarifaDAO.getByCreator(empleado.getId());

        colNombreTarifa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioTarifa.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPeriodoTarifa.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colDescripcionTarifa.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Limpiar y agregar las tarifas a la lista observable
        tarifas.clear();
        tarifas.addAll(tarifasEmpleado);

        // Asignar la lista observable a la tabla
        tablaTarifas.setItems(tarifas);
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
     * Muestra el formulario para añadir o editar una rutina
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarRutina(ClienteRutina rutina) {
        try {
            // Cargar la vista de registro de rutina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-rutina-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el cliente autenticado
            RegistroRutinaController controller = loader.getController();
            controller.setClienteAutenticado(clienteAutenticado);

            // Configurar la rutina si se está editando
            if (rutina != null) {
                controller.setRutina(rutina.getRutina());
                stage.setTitle("Fitness360 - Modificar Rutina");
            } else {
                stage.setTitle("Fitness360 - Añadir Rutina");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las rutinas después de cerrar el formulario
            cargarRutinas();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de rutina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de rutina pasando el cliente autenticado
     * @param event El evento que desencadenó esta acción
     */
    public void abrirRegistroRutina(ActionEvent event) {
        mostrarFormularioAñadirEditarRutina(null);
    }

    /**
     * Abre la ventana de modificación de rutina
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarRutina(ActionEvent event) {
        ClienteRutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        System.out.println(rutinaSeleccionada.getRutina().getCreadorEmpleado());
        System.out.println(rutinaSeleccionada.getRutina().getCreadorCliente());
        if (rutinaSeleccionada != null) {
            // Verificar si la rutina fue creada por un entrenador y no por el cliente actual
            if (rutinaSeleccionada.getRutina().getCreadorEmpleado().getNombre() != null) {
                mostrarAlerta("Acción no permitida", "No puede modificar una rutina asignada por un entrenador", Alert.AlertType.WARNING);
                return;
            }
            mostrarFormularioAñadirEditarRutina(rutinaSeleccionada);
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de eliminar una rutina
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEliminarRutina(ActionEvent event) {
        ClienteRutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            // Verificar si la rutina fue creada por un entrenador y no por el cliente actual
            if (rutinaSeleccionada.getRutina().getCreadorEmpleado() != null && 
                (rutinaSeleccionada.getRutina().getCreadorCliente() == null || 
                 rutinaSeleccionada.getRutina().getCreadorCliente().getId() != clienteAutenticado.getId())) {
                mostrarAlerta("Acción no permitida", "No puede eliminar una rutina asignada por un entrenador", Alert.AlertType.WARNING);
                return;
            }

            // Confirmar eliminación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar rutina");
            alert.setContentText("¿Está seguro que desea eliminar la rutina seleccionada?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    // Eliminar la rutina
                    ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();
                    clienteRutinaDAO.delete(clienteAutenticado.getId(), rutinaSeleccionada.getRutina().getIdRutina());

                    // Recargar las rutinas
                    cargarRutinas();

                    mostrarAlerta("Rutina eliminada", "La rutina ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    System.err.println("Error al eliminar la rutina: " + e.getMessage());
                    mostrarAlerta("Error", "No se pudo eliminar la rutina", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para eliminar", Alert.AlertType.WARNING);
        }
    }


    /**
     * Maneja el evento de contratar una tarifa
     * Verifica si el cliente ya tiene la tarifa contratada y si no, la contrata
     * 
     * @param event El evento que desencadenó esta acción
     */
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

    /**
     * Muestra una alerta con el título, mensaje y tipo especificados
     * 
     * @param titulo Título de la alerta
     * @param mensaje Mensaje de la alerta
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR, etc.)
     */
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

        //Configurar el evento de click para modificar rutina
        btnModificarRutina.setOnAction(this::manejarBotonEditarRutina);

        //Configurar el evento de click para eliminar rutina
        btnEliminarRutina.setOnAction(this::manejarBotonEliminarRutina);

        //Configurar el evento de click para contratar tarifa
        btnContratarTarifa.setOnAction(this::contratarTarifa);
    }

}
