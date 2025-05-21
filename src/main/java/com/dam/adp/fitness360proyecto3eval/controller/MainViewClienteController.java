package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.exceptions.ValidacionException;
import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.model.Sesion;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para la vista principal del cliente.
 * Maneja la interfaz que muestra las rutinas, dietas, revisiones y entrenadores disponibles para el cliente.
 * Permite al cliente ver sus datos y contratar servicios.
 */
public class MainViewClienteController {
    private static final Logger logger = LoggerFactory.getLogger(MainViewClienteController.class);
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
    public TableColumn<ClienteRutina, String> colFechaInicioRutina;
    public TableColumn<ClienteRutina, String> colFechaFinRutina;
    private final ObservableList<ClienteRutina> rutinas = FXCollections.observableArrayList();

    //Tab Dietas
    public Tab tabDietas;
    public TableView<ClienteDieta> tablaDietas;
    public TableColumn<ClienteDieta, String> colNombreDieta;
    public TableColumn<ClienteDieta, String> colDescripcionDieta;
    public TableColumn<ClienteDieta, String> colCreadorDieta;
    public TableColumn<ClienteDieta, String> colFechaInicioDieta;
    public TableColumn<ClienteDieta, String> colFechaFinDieta;
    private final ObservableList<ClienteDieta> dietas = FXCollections.observableArrayList();

    //Tab Revisiones
    public Tab tabRevisiones;
    public TableView<Revision> tablaRevisiones;
    public TableColumn<Revision, String> colFechaRevision;
    public TableColumn<Revision, String> colPesoRevision;
    public TableColumn<Revision, String> colGrasaRevision;
    public TableColumn<Revision, String> colMusculoRevision;
    public TableColumn<Revision, String> colPechoRevision;
    public TableColumn<Revision, String> colCinturaRevision;
    public TableColumn<Revision, String> colCaderaRevision;
    public TableColumn<Revision, String> colEntrenadorRevision;
    public TableColumn<Revision, String> colObservacionesRevision;
    public ComboBox comboFiltroEspecialidad;
    private final ObservableList<Revision> revisiones = FXCollections.observableArrayList();

    //Tab Entrenadores
    public Tab tabContratarEntrenador;
    public TableView<UsuarioEmpleado> tablaEntrenadores;
    public TableColumn<UsuarioEmpleado, String> colNombreEntrenador;
    public TableColumn<UsuarioEmpleado, String> colEspecialidadEntrenador;
    public TableColumn<UsuarioEmpleado, String> colDescripcionEntrenador;
    public TableColumn<UsuarioEmpleado, String> colAccionesEntrenador;
    public ComboBox comboFiltroPeriodo;
    private final ObservableList<UsuarioEmpleado> entrenadores = FXCollections.observableArrayList();

    //Tab Tarifas
    public TableView<Tarifa> tablaTarifas;
    public TableColumn<Tarifa, String> colNombreTarifa;
    public TableColumn<Tarifa, String> colPrecioTarifa;
    public TableColumn<Tarifa, String> colPeriodoTarifa;
    public TableColumn<Tarifa, String> colEntrenadorTarifa;
    public TableColumn<Tarifa, String> colDescripcionTarifa;
    public TableColumn<Tarifa, String> colAccionesTarifa;
    private final ObservableList<Tarifa> tarifas = FXCollections.observableArrayList();

    //Tab Mis Tarifas
    public Tab tabMisTarifas;
    public TableView<ClienteTarifa> tablaMisTarifas;
    public TableColumn<ClienteTarifa, String> colNombreMiTarifa;
    public TableColumn<ClienteTarifa, String> colPrecioMiTarifa;
    public TableColumn<ClienteTarifa, String> colPeriodoMiTarifa;
    public TableColumn<ClienteTarifa, String> colEntrenadorMiTarifa;
    public TableColumn<ClienteTarifa, String> colFechaContratacionMiTarifa;
    public TableColumn<ClienteTarifa, String> colEstadoMiTarifa;
    private final ObservableList<ClienteTarifa> misTarifas = FXCollections.observableArrayList();

    //Botones
    public Button btnCerrarSesion;
    public Button btnCrearRutina;
    public Button btnModificarRutina;
    public Button btnEliminarRutina;
    public Button btnContratarTarifa;

    private UsuarioCliente clienteAutenticado;

    private final ClienteTarifaDAO clienteTarifaDAO = new ClienteTarifaDAO();
    private final ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();
    private final ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();
    private final RevisionDAO revisionDAO = new RevisionDAO();
    private final UsuarioEmpleadoDAO usuarioEmpleadoDAO = new UsuarioEmpleadoDAO();
    private final TarifaDAO tarifaDAO = new TarifaDAO();


    /**
     * Establece el cliente autenticado y actualiza la interfaz
     *
     * @param cliente El cliente autenticado
     */
    public void setClienteAutenticado(UsuarioCliente cliente) {
        logger.debug("Estableciendo cliente autenticado");
        this.clienteAutenticado = cliente;
        if (cliente != null) {
            logger.info("Cliente autenticado: {}", cliente.getNombre());
            labelUsuario.setText("Usuario: " + cliente.getNombreUsuario());
            // Aquí se cargan los datos específicos del cliente
            cargarDatosCliente();
        } else {
            logger.warn("Se intentó establecer un cliente nulo");
        }
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
            if (newValue != null) {
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

        // Obtener el cliente autenticado de la sesión
        if (Sesion.getInstance().isCliente()) {
            setClienteAutenticado(Sesion.getInstance().getClienteAutenticado());
        }
    }

    /**
     * Carga todos los datos relacionados con el cliente autenticado.
     * Incluye rutinas, dietas, revisiones, tarifas contratadas y entrenadores disponibles.
     */
    public void cargarDatosCliente() {
        logger.debug("Iniciando carga de datos del cliente");
        cargarRutinas();
        cargarDietas();
        cargarRevisiones();
        cargarMisTarifas();
        cargarEntrenadores();
        logger.info("Datos del cliente cargados correctamente");
    }

    /**
     * Carga las tarifas contratadas por el cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarMisTarifas() {
        logger.debug("Iniciando carga de tarifas contratadas");


        logger.debug("Buscando tarifas contratadas para el cliente ID: {}", clienteAutenticado.getId());
        List<ClienteTarifa> tarifasContratadas = clienteTarifaDAO.findByCliente(clienteAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de tarifas contratadas");

        colNombreMiTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTarifa().getNombre())
        );

        colPrecioMiTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getTarifa().getPrecio()) + " €")
        );

        colPeriodoMiTarifa.setCellValueFactory(cellData -> {
            Periodo periodo = cellData.getValue().getTarifa().getPeriodo();
            return new SimpleStringProperty(periodo != null ? periodo.toString() : "Sin especificar");
        });

        colEntrenadorMiTarifa.setCellValueFactory(cellData -> {
            // Obtener el empleado creador de la tarifa
            UsuarioEmpleado empleado = cellData.getValue().getTarifa().getCreador();
            return new SimpleStringProperty(empleado != null ? empleado.getNombre() : "Sin especificar");
        });

        colFechaContratacionMiTarifa.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFechaContratacion();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        colEstadoMiTarifa.setCellValueFactory(cellData -> {
            EstadoTarifa estado = cellData.getValue().getEstado();
            return new SimpleStringProperty(estado != null ? estado.toString() : "Sin especificar");
        });

        // Limpiar y agregar las tarifas a la lista observable
        logger.debug("Actualizando lista observable de tarifas contratadas");
        misTarifas.clear();
        misTarifas.addAll(tarifasContratadas);

        // Asignar la lista observable a la tabla
        tablaMisTarifas.setItems(misTarifas);
        logger.info("Se han cargado {} tarifas contratadas para el cliente", tarifasContratadas.size());
    }

    /**
     * Carga las rutinas asignadas al cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarRutinas() {
        logger.debug("Iniciando carga de rutinas");


        logger.debug("Buscando rutinas para el cliente ID: {}", clienteAutenticado.getId());
        List<ClienteRutina> misRutinas = clienteRutinaDAO.findByClientEager(clienteAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de rutinas");

        colNombreRutina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRutina().getNombre())
        );

        colDescripcionRutina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRutina().getDescripcion())
        );

        colCreadorRutina.setCellValueFactory(cellData -> {
            String creador = cellData.getValue().getRutina().getCreadorEmpleado().getNombre();
            String textoCreador = (creador != null && !creador.isBlank())
                    ? creador
                    : clienteAutenticado.getNombre();
            return new SimpleStringProperty(textoCreador);
        });

        colFechaInicioRutina.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFechaAsignacion();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        colFechaFinRutina.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFechaFin();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        logger.debug("Actualizando lista observable de rutinas");
        rutinas.clear();
        rutinas.addAll(misRutinas);

        tablaRutinas.setItems(rutinas);
        logger.info("Se han cargado {} rutinas para el cliente", misRutinas.size());
    }

    /**
     * Carga las dietas asignadas al cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarDietas() {
        logger.debug("Iniciando carga de dietas");

        logger.debug("Buscando dietas para el cliente ID: {}", clienteAutenticado.getId());
        List<ClienteDieta> misDietas = clienteDietaDAO.findByClientEager(clienteAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de dietas");
        colNombreDieta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDieta().getNombre())
        );

        colDescripcionDieta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDieta().getDescripcion())
        );

        colCreadorDieta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDieta().getCreador().getNombre())
        );
        // Formatear la fecha de inicio en español
        colFechaInicioDieta.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFechaAsignacion();
            return new javafx.beans.property.SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        // Formatear la fecha de fin en español
        colFechaFinDieta.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFechaFin();
            return new javafx.beans.property.SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        // Limpiar y agregar las dietas a la lista observable
        logger.debug("Actualizando lista observable de dietas");
        dietas.clear();
        dietas.addAll(misDietas);

        // Asignar la lista observable a la tabla
        tablaDietas.setItems(dietas);
        logger.info("Se han cargado {} dietas para el cliente", misDietas.size());
    }

    /**
     * Carga las revisiones físicas del cliente autenticado en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    private void cargarRevisiones() {
        logger.debug("Iniciando carga de revisiones");

        logger.debug("Buscando revisiones para el cliente ID: {}", clienteAutenticado.getId());
        List<Revision> misRevisiones = revisionDAO.getByClientEager(clienteAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de revisiones");

        // Fecha formateada
        colFechaRevision.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getFecha();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        colPesoRevision.setCellValueFactory(cellData -> {
            Double peso = cellData.getValue().getPeso();
            String textoPeso = String.format("%.2f", peso) + " kg";
            return new SimpleStringProperty(textoPeso);
        });

        colGrasaRevision.setCellValueFactory(cellData -> {
            Double grasa = cellData.getValue().getGrasa();
            String textoGrasa = String.format("%.2f", grasa) + " %";
            return new SimpleStringProperty(textoGrasa);
        });

        colMusculoRevision.setCellValueFactory(cellData -> {
            Double musculo = cellData.getValue().getMusculo();
            String textoMusculo = String.format("%.2f", musculo) + " %";
            return new SimpleStringProperty(textoMusculo);
        });

        colPechoRevision.setCellValueFactory(cellData -> {
            Double pecho = cellData.getValue().getmPecho();
            String textoPecho = String.format("%.2f", pecho) + " cm";
            return new SimpleStringProperty(textoPecho);
        });

        colCinturaRevision.setCellValueFactory(cellData -> {
            Double cintura = cellData.getValue().getmCintura();
            String textoCintura = String.format("%.2f", cintura) + " cm";
            return new SimpleStringProperty(textoCintura);
        });

        colCaderaRevision.setCellValueFactory(cellData -> {
            Double cadera = cellData.getValue().getmCadera();
            String textoCadera = String.format("%.2f", cadera) + " cm";
            return new SimpleStringProperty(textoCadera);
        });

        // Texto del nombre completo del entrenador
        colEntrenadorRevision.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmpleado().getNombre())
        );

        colObservacionesRevision.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getObservaciones())
        );

        logger.debug("Actualizando lista observable de revisiones");
        revisiones.clear();
        revisiones.addAll(misRevisiones);

        tablaRevisiones.setItems(revisiones);
        logger.info("Se han cargado {} revisiones para el cliente", misRevisiones.size());
    }


    /**
     * Carga la lista de entrenadores disponibles en la tabla correspondiente.
     * Obtiene los datos de la base de datos y configura las columnas de la tabla.
     */
    public void cargarEntrenadores() {
        logger.debug("Iniciando carga de entrenadores");

        logger.debug("Buscando todos los empleados");
        List<UsuarioEmpleado> empleados = usuarioEmpleadoDAO.getAll();

        logger.debug("Configurando columnas de la tabla de entrenadores");
        colNombreEntrenador.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );
        colEspecialidadEntrenador.setCellValueFactory(cellData -> {
            Especialidad especialidad = cellData.getValue().getEspecialidad();
            return new SimpleStringProperty(especialidad != null ? especialidad.toString() : "N/D");
        });
        colDescripcionEntrenador.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion())
        );

        // Limpiar y agregar los entrenadores a la lista observable
        logger.debug("Actualizando lista observable de entrenadores");
        entrenadores.clear();
        entrenadores.addAll(empleados);

        // Asignar la lista observable a la tabla
        tablaEntrenadores.setItems(entrenadores);
        logger.info("Se han cargado {} entrenadores disponibles", empleados.size());
    }

    /**
     * Carga las tarifas disponibles para un entrenador específico.
     *
     * @param empleado El entrenador del que se cargarán las tarifas
     */
    public void cargarTarifasEntrenador(UsuarioEmpleado empleado) {
        logger.debug("Iniciando carga de tarifas para el entrenador ID: {}", empleado.getId());

        List<Tarifa> tarifasEmpleado = tarifaDAO.getByCreator(empleado.getId());

        logger.debug("Configurando columnas de la tabla de tarifas");
        colNombreTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );
        colPrecioTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getPrecio()) + " €")
        );
        colPeriodoTarifa.setCellValueFactory(cellData -> {
            Periodo periodo = cellData.getValue().getPeriodo();
            return new SimpleStringProperty(periodo != null ? periodo.toString() : "Sin especificar");
        });

        colDescripcionTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion())
        );

        // Limpiar y agregar las tarifas a la lista observable
        logger.debug("Actualizando lista observable de tarifas");
        tarifas.clear();
        tarifas.addAll(tarifasEmpleado);

        // Asignar la lista observable a la tabla
        tablaTarifas.setItems(tarifas);
        logger.info("Se han cargado {} tarifas para el entrenador {}", tarifasEmpleado.size(), empleado.getNombre());
    }

    /**
     * Maneja el evento de clic en el botón de cerrar sesión
     * Cierra la sesión actual y navega de vuelta a la pantalla de login
     *
     * @param event El evento que desencadenó esta acción
     */
    @FXML
    public void cerrarSesion(ActionEvent event) {
        try {
            // Cerrar la sesión actual
            Sesion.getInstance().cerrarSesion();

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
            logger.error("Error al cargar la pantalla de login: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra el formulario para añadir o editar una rutina
     *
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
            logger.error("Error al cargar la pantalla de registro de rutina: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de rutina pasando el cliente autenticado
     *
     * @param event El evento que desencadenó esta acción
     */
    public void abrirRegistroRutina(ActionEvent event) {
        mostrarFormularioAñadirEditarRutina(null);
    }

    /**
     * Abre la ventana de modificación de rutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarRutina(ActionEvent event) {
        ClienteRutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            // Verificar si la rutina fue creada por un entrenador y no por el cliente actual
            boolean puedeModificar = true;
            if (rutinaSeleccionada.getRutina().getCreadorEmpleado().getNombre() != null) {
                Utilidades.mostrarAlerta("Acción no permitida", "No puede modificar una rutina asignada por un entrenador", Alert.AlertType.WARNING);
                puedeModificar = false;
            }

            if (puedeModificar) {
                mostrarFormularioAñadirEditarRutina(rutinaSeleccionada);
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de eliminar una rutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEliminarRutina(ActionEvent event) {
        ClienteRutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            boolean puedeEliminar = true;

            // Verificar si la rutina fue creada por un entrenador
            if (rutinaSeleccionada.getRutina().getCreadorEmpleado().getNombre() != null) {
                Utilidades.mostrarAlerta("Acción no permitida", "No puede eliminar una rutina asignada por un entrenador", Alert.AlertType.WARNING);
                puedeEliminar = false;
            }

            if (puedeEliminar) {
                // Confirmar eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("Eliminar rutina");
                alert.setContentText("¿Está seguro que desea eliminar la rutina seleccionada?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    try {
                        // Eliminar la rutina
                        clienteRutinaDAO.delete(clienteAutenticado.getId(),
                                rutinaSeleccionada.getRutina().getIdRutina());

                        // Recargar las rutinas
                        cargarRutinas();

                        Utilidades.mostrarAlerta("Rutina eliminada",
                                "La rutina ha sido eliminada correctamente",
                                Alert.AlertType.INFORMATION);
                    } catch (Exception e) {
                        logger.error("Error al eliminar la rutina: {}", e.getMessage());
                        Utilidades.mostrarAlerta("Error",
                                "No se pudo eliminar la rutina",
                                Alert.AlertType.ERROR);
                    }
                }
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida",
                    "Por favor, seleccione una rutina para eliminar",
                    Alert.AlertType.WARNING);
        }
    }


    /**
     * Maneja el evento de contratar una tarifa
     * Verifica si el cliente ya tiene la tarifa contratada y si no, la contrata
     *
     * @param event El evento que desencadenó esta acción
     */
    public void contratarTarifa(ActionEvent event) {
        Tarifa tarifaSeleccionada = tablaTarifas.getSelectionModel().getSelectedItem();
        boolean puedeContratar = true;

        if (tarifaSeleccionada == null) {
            Utilidades.mostrarAlerta("Error", "Por favor, seleccione una tarifa", Alert.AlertType.WARNING);
            puedeContratar = false;
        }

        if (puedeContratar) {
            try {
                // Verificar si ya existe la asignación
                List<ClienteTarifa> tarifasCliente = clienteTarifaDAO.findByCliente(clienteAutenticado.getId());
                boolean tarifaYaExiste = false;

                for (ClienteTarifa ct : tarifasCliente) {
                    if (ct.getTarifa().getIdTarifa() == tarifaSeleccionada.getIdTarifa() && ct.getEstado() == EstadoTarifa.ACTIVA) {
                        tarifaYaExiste = true;
                        break;
                    }
                }

                if (tarifaYaExiste) {
                    Utilidades.mostrarAlerta("Error", "El cliente ya tiene esta tarifa contratada", Alert.AlertType.ERROR);
                    puedeContratar = false;
                }

                if (puedeContratar) {
                    // Crear nueva asignación
                    ClienteTarifa clienteTarifa = new ClienteTarifa();
                    clienteTarifa.setCliente(clienteAutenticado);
                    clienteTarifa.setTarifa(tarifaSeleccionada);
                    clienteTarifa.setFechaContratacion(new Date(System.currentTimeMillis()));
                    clienteTarifa.setEstado(EstadoTarifa.ACTIVA);

                    clienteTarifaDAO.insert(clienteTarifa);
                    // Recargar las tarifas contratadas para mostrar la nueva tarifa
                    cargarMisTarifas();
                    Utilidades.mostrarAlerta("Tarifa Asignada", "Tarifa asignada correctamente", Alert.AlertType.INFORMATION);
                }
            } catch (ValidacionException e) {
                Utilidades.mostrarAlerta("Error", "No se pudo asignar la tarifa", Alert.AlertType.ERROR);
            }
        }
    }
}
