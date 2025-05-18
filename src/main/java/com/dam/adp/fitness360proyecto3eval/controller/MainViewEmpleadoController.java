package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para la vista principal del empleado
 * Maneja la visualización de clientes, rutinas, dietas, tarifas y revisiones del empleado
 */
public class MainViewEmpleadoController {
    private static final Logger logger = LoggerFactory.getLogger(MainViewEmpleadoController.class);

    // Componentes generales
    public TabPane tabPane;
    public Label labelUsuario;
    @FXML
    public Button btnCerrarSesion;

    // Tab Clientes
    public Tab tabClientes;
    public ComboBox comboFiltroClientes;
    public TableView<UsuarioCliente> tablaClientes;
    public TableColumn<UsuarioCliente, String> colNombreCliente;
    public TableColumn<UsuarioCliente, String> colApellidosCliente;
    public TableColumn<UsuarioCliente, String> colEmailCliente;
    public TableColumn<UsuarioCliente, String> colTelefonoCliente;
    public TableColumn<UsuarioCliente, String> colFechaAltaCliente;
    private ObservableList<UsuarioCliente> clientes = FXCollections.observableArrayList();

    // Tab Rutinas
    public Tab tabRutinas;
    public ComboBox comboFiltroRutinas;
    public Button btnCrearRutina;
    public Button btnModificarRutina;
    public Button btnAsignarRutina;
    public Button btnEliminarRutina;
    public TableView<Rutina> tablaRutinas;
    public TableColumn<Rutina, String> colNombreRutina;
    public TableColumn<Rutina, String> colDescripcionRutina;
    public TableColumn<Rutina, String> colFechaRutina;
    public TableColumn<Rutina, String> colClientesAsignadosRutina;
    private ObservableList<Rutina> rutinas = FXCollections.observableArrayList();

    // Tab Dietas
    public Tab tabDietas;
    public ComboBox comboFiltroDietas;
    public Button btnCrearDieta;
    public Button btnModificarDieta;
    public Button btnAsignarDieta;
    public Button btnEliminarDieta;
    public TableView<Dieta> tablaDietas;
    public TableColumn<Dieta, String> colNombreDieta;
    public TableColumn<Dieta, String> colDescripcionDieta;
    public TableColumn<Dieta, String> colFechaDieta;
    public TableColumn<Dieta, String> colClientesAsignadosDieta;
    private ObservableList<Dieta> dietas = FXCollections.observableArrayList();

    // Tab Tarifas
    public Tab tabTarifas;
    public ComboBox comboFiltroPeriodo;
    public Button btnCrearTarifa;
    public Button btnModificarTarifa;
    public Button btnEliminarTarifa;
    public TableView<Tarifa> tablaTarifas;
    public TableColumn<Tarifa, String> colNombreTarifa;
    public TableColumn<Tarifa, String> colPrecioTarifa;
    public TableColumn<Tarifa, String> colPeriodoTarifa;
    public TableColumn<Tarifa, String> colDescripcionTarifa;
    public TableColumn<Tarifa, String> colClientesAsignadosTarifa;
    private ObservableList<Tarifa> tarifas = FXCollections.observableArrayList();

    // Tab Revisiones
    public Tab tabRevisiones;
    public ComboBox comboFiltroRevisiones;
    public Button btnNuevaRevision;
    public TableView<Revision> tablaRevisiones;
    public TableColumn<Revision, String> colFechaRevision;
    public TableColumn<Revision, String> colClienteRevision;
    public TableColumn<Revision, String> colPesoRevision;
    public TableColumn<Revision, String> colGrasaRevision;
    public TableColumn<Revision, String> colMusculoRevision;
    public TableColumn<Revision, String> colPechoRevision;
    public TableColumn<Revision, String> colCinturaRevision;
    public TableColumn<Revision, String> colCaderaRevision;
    public TableColumn<Revision, String> colObservacionesRevision1;
    public TableColumn<Revision, String> colAccionesRevision;
    private ObservableList<Revision> revisiones = FXCollections.observableArrayList();

    private UsuarioEmpleado empleadoAutenticado;

    /**
     * Establece el empleado autenticado y actualiza la interfaz
     *
     * @param empleado El empleado autenticado
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleado) {
        logger.debug("Estableciendo empleado autenticado");
        this.empleadoAutenticado = empleado;
        if (empleado != null) {
            logger.info("Empleado autenticado: {}", empleado.getNombre());
            labelUsuario.setText("Usuario: " + empleado.getNombreUsuario());

            // Configurar los botones según la especialidad del empleado
            Especialidad especialidad = empleado.getEspecialidad();
            logger.debug("Configurando botones según especialidad: {}", especialidad);

            // Botón Crear Dieta
            boolean puedeCrearDieta = (especialidad == Especialidad.DIETISTA || especialidad == Especialidad.AMBOS);
            btnCrearDieta.setDisable(!puedeCrearDieta);
            if (puedeCrearDieta) {
                logger.debug("Empleado puede crear dietas, configurando botón");
                btnCrearDieta.setOnAction(this::abrirRegistroDieta);
            } else {
                logger.debug("Empleado no puede crear dietas, botón deshabilitado");
            }

            // Botón Crear Rutina
            boolean puedeCrearRutina = (especialidad == Especialidad.ENTRENADOR || especialidad == Especialidad.AMBOS);
            btnCrearRutina.setDisable(!puedeCrearRutina);
            if (puedeCrearRutina) {
                logger.debug("Empleado puede crear rutinas, configurando botón");
                btnCrearRutina.setOnAction(this::manejarBotonCrearRutina);
            } else {
                logger.debug("Empleado no puede crear rutinas, botón deshabilitado");
            }

            // Cargar los datos específicos del empleado
            cargarDatosEmpleado();
        } else {
            logger.warn("Se intentó establecer un empleado nulo");
        }
    }

    /**
     * Inicializa el controlador y configura los eventos
     */
    @FXML
    public void initialize() {
        logger.debug("Inicializando MainViewEmpleadoController");

        // Configurar el evento de clic para el botón de cerrar sesión
        logger.debug("Configurando evento para botón de cerrar sesión");
        btnCerrarSesion.setOnAction(this::cerrarSesion);

        // Configurar eventos para los botones de crear
        logger.debug("Configurando eventos para botones de crear");
        btnCrearRutina.setOnAction(this::manejarBotonCrearRutina);
        btnCrearDieta.setOnAction(this::abrirRegistroDieta);
        btnCrearTarifa.setOnAction(this::abrirRegistroTarifa);
        btnNuevaRevision.setOnAction(this::abrirRegistroRevision);

        // Configurar eventos para los botones de modificar
        logger.debug("Configurando eventos para botones de modificar");
        btnModificarRutina.setOnAction(this::manejarBotonEditarRutina);
        btnModificarDieta.setOnAction(this::manejarBotonEditarDieta);
        btnModificarTarifa.setOnAction(this::manejarBotonEditarTarifa);

        // Configurar eventos para los botones de asignar
        logger.debug("Configurando eventos para botones de asignar");
        btnAsignarRutina.setOnAction(this::manejarBotonAsignarRutina);
        btnAsignarDieta.setOnAction(this::manejarBotonAsignarDieta);

        // Configurar eventos para los botones de eliminar
        logger.debug("Configurando eventos para botones de eliminar");
        btnEliminarRutina.setOnAction(this::manejarBotonBorrarRutina);
        btnEliminarDieta.setOnAction(this::manejarBotonBorrarDieta);
        btnEliminarTarifa.setOnAction(this::manejarBotonBorrarTarifa);

        // Obtener el empleado autenticado de la sesión
        logger.debug("Verificando si hay un empleado autenticado en la sesión");
        if (Sesion.getInstance().isEmpleado()) {
            logger.debug("Empleado autenticado encontrado en la sesión, estableciendo en el controlador");
            setEmpleadoAutenticado(Sesion.getInstance().getEmpleadoAutenticado());
        } else {
            logger.warn("No se encontró un empleado autenticado en la sesión");
        }

        logger.info("MainViewEmpleadoController inicializado correctamente");
    }


    /**
     * Carga los datos específicos del empleado en la interfaz
     */
    private void cargarDatosEmpleado() {
        logger.debug("Iniciando carga de datos del empleado");

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

        logger.info("Datos del empleado cargados correctamente");
    }

    /**
     * Carga los clientes asignados al empleado
     */
    private void cargarClientes() {
        logger.debug("Iniciando carga de clientes");
        UsuarioClienteDAO clienteDAO = new UsuarioClienteDAO();

        logger.debug("Buscando clientes para el empleado ID: {}", empleadoAutenticado.getId());
        List<UsuarioCliente> misClientes = clienteDAO.findClientesByEmpleadoTarifa(empleadoAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de clientes");
        // Configurar las columnas de la tabla
        colNombreCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colApellidosCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getApellidos())
        );
        colEmailCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCorreo())
        );
        colTelefonoCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono())
        );

        // Usar fechaContratacion de ClienteTarifa y formatear en español
        colFechaAltaCliente.setCellValueFactory(cellData -> {
            UsuarioCliente cliente = cellData.getValue();
            ClienteTarifaDAO clienteTarifaDAO = new ClienteTarifaDAO();
            List<ClienteTarifa> tarifas = clienteTarifaDAO.findByCliente(cliente.getId());
            // Buscar la tarifa activa
            for (ClienteTarifa tarifa : tarifas) {
                if (tarifa.getEstado() == EstadoTarifa.ACTIVA) {
                    return new javafx.beans.property.SimpleStringProperty(Utilidades.formatearFechaEspanol(tarifa.getFechaContratacion()));
                }
            }
            // Si no hay tarifa activa, usar la fecha de creación del cliente
            logger.debug("No se encontró tarifa activa para el cliente ID: {}, usando fecha de creación", cliente.getId());
            return new javafx.beans.property.SimpleStringProperty(Utilidades.formatearFechaEspanol(cliente.getCreatedAt()));
        });

        // Limpiar y agregar los clientes a la lista observable
        logger.debug("Actualizando lista observable de clientes");
        clientes.clear();
        clientes.addAll(misClientes);

        // Asignar la lista observable a la tabla
        tablaClientes.setItems(clientes);
        logger.info("Se han cargado {} clientes para el empleado", misClientes.size());
    }

    /**
     * Carga las rutinas creadas por el empleado
     */
    private void cargarRutinas() {
        logger.debug("Iniciando carga de rutinas");
        RutinaDAO rutinaDAO = new RutinaDAO();

        logger.debug("Buscando rutinas para el empleado ID: {}", empleadoAutenticado.getId());
        List<Rutina> misRutinas = rutinaDAO.getByCreator(empleadoAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de rutinas");
        // Configurar las columnas de la tabla
        colNombreRutina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colDescripcionRutina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion())
        );

        colFechaRutina.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getCreatedAt();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        // Limpiar y agregar las rutinas a la lista observable
        logger.debug("Actualizando lista observable de rutinas");
        rutinas.clear();
        rutinas.addAll(misRutinas);

        // Asignar la lista observable a la tabla
        tablaRutinas.setItems(rutinas);
        logger.info("Se han cargado {} rutinas para el empleado", misRutinas.size());
    }

    /**
     * Carga las dietas creadas por el empleado
     */
    private void cargarDietas() {
        logger.debug("Iniciando carga de dietas");
        DietaDAO dietaDAO = new DietaDAO();
        ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();

        logger.debug("Buscando dietas para el empleado ID: {}", empleadoAutenticado.getId());
        List<Dieta> misDietas = dietaDAO.getByCreator(empleadoAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de dietas");
        // Configurar las columnas de la tabla
        colNombreDieta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colDescripcionDieta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion())
        );

        colFechaDieta.setCellValueFactory(cellData -> {
            java.util.Date fecha = cellData.getValue().getCreatedAt();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        // Limpiar y agregar las dietas a la lista observable
        logger.debug("Actualizando lista observable de dietas");
        dietas.clear();
        dietas.addAll(misDietas);

        // Asignar la lista observable a la tabla
        tablaDietas.setItems(dietas);
        logger.info("Se han cargado {} dietas para el empleado", misDietas.size());
    }

    /**
     * Carga las tarifas del empleado
     */
    private void cargarTarifas() {
        logger.debug("Iniciando carga de tarifas");
        TarifaDAO tarifaDAO = new TarifaDAO();

        logger.debug("Buscando tarifas para el empleado ID: {}", empleadoAutenticado.getId());
        List<Tarifa> misTarifas = tarifaDAO.getByCreator(empleadoAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de tarifas");
        // Configurar las columnas de la tabla
        colNombreTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colPrecioTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getPrecio()) + " €")
        );

        colPeriodoTarifa.setCellValueFactory(cellData->{
            Periodo periodo = cellData.getValue().getPeriodo();
            return new SimpleStringProperty(periodo != null ? periodo.toString() : "Sin especificar");
        });
        ;
        colDescripcionTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion())
                );

        // Limpiar y agregar las tarifas a la lista observable
        logger.debug("Actualizando lista observable de tarifas");
        tarifas.clear();
        tarifas.addAll(misTarifas);

        // Asignar la lista observable a la tabla
        tablaTarifas.setItems(tarifas);
        logger.info("Se han cargado {} tarifas para el empleado", misTarifas.size());
    }

    /**
     * Carga las revisiones realizadas por el empleado
     */
    private void cargarRevisiones() {
        logger.debug("Iniciando carga de revisiones");
        RevisionDAO revisionDAO = new RevisionDAO();

        // Verificar si la columna está inicializada
        if (colObservacionesRevision1 == null) {
            logger.error("La columna de observaciones no está inicializada correctamente");
            return;
        }


        logger.debug("Buscando revisiones para el empleado ID: {}", empleadoAutenticado.getId());
        List<Revision> misRevisiones = revisionDAO.getByCreatorEager(empleadoAutenticado.getId());

        logger.debug("Configurando columnas de la tabla de revisiones");
        // Configurar las columnas de la tabla
        colFechaRevision.setCellValueFactory(cellData->{
            java.util.Date fecha = cellData.getValue().getFecha();
            return new SimpleStringProperty(Utilidades.formatearFechaEspanol(fecha));
        });

        colClienteRevision.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getCliente().getNombre() + " " + cellData.getValue().getCliente().getApellidos())
                );
        colPesoRevision.setCellValueFactory(cellData -> {
            Double peso = cellData.getValue().getPeso();
            String textoPeso = (peso != null) ? String.format("%.2f", peso) + " kg" : "N/D";
            return new SimpleStringProperty(textoPeso);
        });

        colGrasaRevision.setCellValueFactory(cellData -> {
            Double grasa = cellData.getValue().getGrasa();
            String textoGrasa = (grasa != null) ? String.format("%.2f", grasa) + " %" : "N/D";
            return new SimpleStringProperty(textoGrasa);
        });

        colMusculoRevision.setCellValueFactory(cellData -> {
            Double musculo = cellData.getValue().getMusculo();
            String textoMusculo = (musculo != null) ? String.format("%.2f", musculo) + " %" : "N/D";
            return new SimpleStringProperty(textoMusculo);
        });

        colPechoRevision.setCellValueFactory(cellData -> {
            Double pecho = cellData.getValue().getmPecho();
            String textoPecho = (pecho != null) ? String.format("%.2f", pecho) + " cm" : "N/D";
            return new SimpleStringProperty(textoPecho);
        });

        colCinturaRevision.setCellValueFactory(cellData -> {
            Double cintura = cellData.getValue().getmCintura();
            String textoCintura = (cintura != null) ? String.format("%.2f", cintura) + " cm" : "N/D";
            return new SimpleStringProperty(textoCintura);
        });

        colCaderaRevision.setCellValueFactory(cellData -> {
            Double cadera = cellData.getValue().getmCadera();
            String textoCadera = (cadera != null) ? String.format("%.2f", cadera) + " cm" : "N/D";
            return new SimpleStringProperty(textoCadera);
        });

        colObservacionesRevision1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getObservaciones())
                );

        // Limpiar y agregar las revisiones a la lista observable
        logger.debug("Actualizando lista observable de revisiones");
        revisiones.clear();
        revisiones.addAll(misRevisiones);

        // Asignar la lista observable a la tabla
        tablaRevisiones.setItems(revisiones);
        logger.info("Se han cargado {} revisiones para el empleado", misRevisiones.size());
    }

    /**
     * Maneja el evento de clic en el botón de cerrar sesión
     * Cierra la sesión actual y navega de vuelta a la pantalla de login
     *
     * @param event El evento que desencadenó esta acción
     */
    @FXML
    public void cerrarSesion(ActionEvent event) {
        logger.debug("Iniciando proceso de cierre de sesión");
        try {
            // Cerrar la sesión actual
            logger.debug("Cerrando sesión actual");
            Sesion.getInstance().cerrarSesion();

            // Cargar la vista de login
            logger.debug("Cargando vista de login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/login-view.fxml"));
            Parent root = loader.load();

            // Configurar la nueva escena
            logger.debug("Configurando escena para pantalla de login");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Fitness360 - Login");
            stage.setScene(scene);
            stage.show();
            logger.info("Sesión cerrada correctamente, navegación a pantalla de login completada");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de login: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Muestra el formulario para añadir o editar una rutina
     *
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarRutina(Rutina rutina) {
        logger.debug("Mostrando formulario para {}", rutina != null ? "editar rutina" : "añadir nueva rutina");
        try {
            // Cargar la vista de registro de rutina
            logger.debug("Cargando vista de registro de rutina");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-rutina-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            logger.debug("Configurando controlador con empleado autenticado");
            RegistroRutinaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la rutina si se está editando
            if (rutina != null) {
                logger.debug("Configurando rutina existente para edición");
                controller.setRutina(rutina);
                stage.setTitle("Fitness360 - Modificar Rutina");
            } else {
                logger.debug("Configurando para nueva rutina");
                stage.setTitle("Fitness360 - Añadir Rutina");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las rutinas después de cerrar el formulario
            logger.debug("Recargando rutinas después de cerrar el formulario");
            cargarRutinas();
            logger.info("Formulario de rutina cerrado correctamente");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de registro de rutina: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de rutina pasando el empleado autenticado
     *
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void abrirRegistroRutina(Rutina rutina) {
        mostrarFormularioAñadirEditarRutina(rutina);
    }

    /**
     * Abre la ventana de modificación de rutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarRutina(ActionEvent event) {
        Rutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            mostrarFormularioAñadirEditarRutina(rutinaSeleccionada);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra el formulario para añadir o editar una dieta
     *
     * @param dieta La dieta a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarDieta(Dieta dieta) {
        logger.debug("Mostrando formulario para {}", dieta != null ? "editar dieta" : "añadir nueva dieta");
        try {
            // Cargar la vista de registro de dieta
            logger.debug("Cargando vista de registro de dieta");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-dieta-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            logger.debug("Configurando controlador con empleado autenticado");
            RegistroDietaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la dieta si se está editando
            if (dieta != null) {
                logger.debug("Configurando dieta existente para edición");
                controller.setDieta(dieta);
                stage.setTitle("Fitness360 - Modificar Dieta");
            } else {
                logger.debug("Configurando para nueva dieta");
                stage.setTitle("Fitness360 - Añadir Dieta");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las dietas después de cerrar el formulario
            logger.debug("Recargando dietas después de cerrar el formulario");
            cargarDietas();
            logger.info("Formulario de dieta cerrado correctamente");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de registro de dieta: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de dieta
     *
     * @param event El evento que desencadenó esta acción
     */
    public void abrirRegistroDieta(ActionEvent event) {
        mostrarFormularioAñadirEditarDieta(null);
    }

    /**
     * Abre la ventana de modificación de dieta
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarDieta(ActionEvent event) {
        Dieta dietaSeleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (dietaSeleccionada != null) {
            mostrarFormularioAñadirEditarDieta(dietaSeleccionada);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una dieta para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra el formulario para añadir o editar una tarifa
     *
     * @param tarifa La tarifa a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarTarifa(Tarifa tarifa) {
        logger.debug("Mostrando formulario para {}", tarifa != null ? "editar tarifa" : "añadir nueva tarifa");
        try {
            // Cargar la vista de registro de tarifa
            logger.debug("Cargando vista de registro de tarifa");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-tarifa-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            logger.debug("Configurando controlador con empleado autenticado");
            RegistroTarifaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la tarifa si se está editando
            if (tarifa != null) {
                logger.debug("Configurando tarifa existente para edición");
                controller.setTarifa(tarifa);
                stage.setTitle("Fitness360 - Modificar Tarifa");
            } else {
                logger.debug("Configurando para nueva tarifa");
                stage.setTitle("Fitness360 - Añadir Tarifa");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las tarifas después de cerrar el formulario
            logger.debug("Recargando tarifas después de cerrar el formulario");
            cargarTarifas();
            logger.info("Formulario de tarifa cerrado correctamente");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de registro de tarifa: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de tarifa
     *
     * @param event El evento que desencadenó esta acción
     */
    public void abrirRegistroTarifa(ActionEvent event) {
        mostrarFormularioAñadirEditarTarifa(null);
    }

    /**
     * Abre la ventana de modificación de tarifa
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarTarifa(ActionEvent event) {
        Tarifa tarifaSeleccionada = tablaTarifas.getSelectionModel().getSelectedItem();
        if (tarifaSeleccionada != null) {
            mostrarFormularioAñadirEditarTarifa(tarifaSeleccionada);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una tarifa para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra el formulario para añadir o editar una revisión
     *
     * @param revision La revisión a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarRevision(Revision revision) {
        logger.debug("Mostrando formulario para {}", revision != null ? "editar revisión" : "añadir nueva revisión");
        try {
            // Cargar la vista de registro de revisión
            logger.debug("Cargando vista de registro de revisión");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-revision-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            logger.debug("Configurando controlador con empleado autenticado");
            RegistroRevisionController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la revisión si se está editando
            if (revision != null) {
                logger.debug("Configurando revisión existente para edición");
                controller.setRevision(revision);
                stage.setTitle("Fitness360 - Modificar Revisión");
            } else {
                logger.debug("Configurando para nueva revisión");
                stage.setTitle("Fitness360 - Nueva Revisión");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las revisiones después de cerrar el formulario
            logger.debug("Recargando revisiones después de cerrar el formulario");
            cargarRevisiones();
            logger.info("Formulario de revisión cerrado correctamente");
        } catch (IOException e) {
            logger.error("Error al cargar la pantalla de registro de revisión: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de registro de revisión
     *
     * @param event El evento que desencadenó esta acción
     */
    public void abrirRegistroRevision(ActionEvent event) {
        mostrarFormularioAñadirEditarRevision(null);
    }

    /**
     * Abre la ventana de modificación de revisión
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonEditarRevision(ActionEvent event) {
        Revision revisionSeleccionada = tablaRevisiones.getSelectionModel().getSelectedItem();
        if (revisionSeleccionada != null) {
            mostrarFormularioAñadirEditarRevision(revisionSeleccionada);
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una revisión para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Método para manejar el evento de crear rutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonCrearRutina(ActionEvent event) {
        abrirRegistroRutina(null);
    }


    /**
     * Maneja el evento de eliminar una rutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonBorrarRutina(ActionEvent event) {
        Rutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            // Confirmar eliminación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar rutina");
            alert.setContentText("¿Está seguro que desea eliminar la rutina seleccionada?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    // Eliminar la rutina
                    RutinaDAO rutinaDAO = new RutinaDAO();
                    rutinaDAO.delete(rutinaSeleccionada);

                    // Eliminar de la lista observable
                    rutinas.remove(rutinaSeleccionada);

                    logger.info("Rutina '{}' eliminada correctamente", rutinaSeleccionada.getNombre());
                    Utilidades.mostrarAlerta("Rutina eliminada", "La rutina ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    logger.error("Error al eliminar la rutina: {}", e.getMessage(), e);
                    Utilidades.mostrarAlerta("Error", "No se pudo eliminar la rutina", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para eliminar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de eliminar una dieta
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonBorrarDieta(ActionEvent event) {
        Dieta dietaSeleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (dietaSeleccionada != null) {
            // Confirmar eliminación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar dieta");
            alert.setContentText("¿Está seguro que desea eliminar la dieta seleccionada?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    // Eliminar la dieta
                    DietaDAO dietaDAO = new DietaDAO();
                    dietaDAO.delete(dietaSeleccionada);

                    // Eliminar de la lista observable
                    dietas.remove(dietaSeleccionada);

                    logger.info("Dieta '{}' eliminada correctamente", dietaSeleccionada.getNombre());
                    Utilidades.mostrarAlerta("Dieta eliminada", "La dieta ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    logger.error("Error al eliminar la dieta: {}", e.getMessage(), e);
                    Utilidades.mostrarAlerta("Error", "No se pudo eliminar la dieta", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una dieta para eliminar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de eliminar una tarifa
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonBorrarTarifa(ActionEvent event) {
        Tarifa tarifaSeleccionada = tablaTarifas.getSelectionModel().getSelectedItem();
        if (tarifaSeleccionada != null) {
            // Confirmar eliminación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar tarifa");
            alert.setContentText("¿Está seguro que desea eliminar la tarifa seleccionada?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    // Eliminar la tarifa
                    TarifaDAO tarifaDAO = new TarifaDAO();
                    tarifaDAO.delete(tarifaSeleccionada);

                    // Eliminar de la lista observable
                    tarifas.remove(tarifaSeleccionada);

                    logger.info("Tarifa '{}' eliminada correctamente", tarifaSeleccionada.getNombre());
                    Utilidades.mostrarAlerta("Tarifa eliminada", "La tarifa ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    logger.error("Error al eliminar la tarifa: {}", e.getMessage(), e);
                    Utilidades.mostrarAlerta("Error", "No se pudo eliminar la tarifa", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una tarifa para eliminar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de asignar una rutina a clientes
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonAsignarRutina(ActionEvent event) {
        logger.debug("Manejando evento de asignar rutina");
        Rutina rutinaSeleccionada = tablaRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada != null) {
            try {
                // Cargar la vista de asignación
                logger.debug("Cargando vista de asignación para rutina: {}", rutinaSeleccionada.getNombre());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/asignar-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();

                // Obtener el controlador y establecer los datos necesarios
                logger.debug("Configurando controlador de asignación");
                AsignarController controller = loader.getController();
                controller.setEmpleadoAutenticado(empleadoAutenticado);
                controller.setRutina(rutinaSeleccionada);

                // Configurar la ventana
                stage.setTitle("Fitness360 - Asignar Rutina");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                // Recargar las rutinas después de cerrar la ventana
                logger.debug("Recargando rutinas después de asignación");
                cargarRutinas();
                logger.info("Asignación de rutina completada");
            } catch (IOException e) {
                logger.error("Error al cargar la pantalla de asignación: {}", e.getMessage(), e);
                Utilidades.mostrarAlerta("Error", "No se pudo abrir la ventana de asignación", Alert.AlertType.ERROR);
            }
        } else {
            logger.warn("No se seleccionó ninguna rutina para asignar");
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para asignar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Maneja el evento de asignar una dieta a clientes
     *
     * @param event El evento que desencadenó esta acción
     */
    public void manejarBotonAsignarDieta(ActionEvent event) {
        logger.debug("Manejando evento de asignar dieta");
        Dieta dietaSeleccionada = tablaDietas.getSelectionModel().getSelectedItem();
        if (dietaSeleccionada != null) {
            try {
                // Cargar la vista de asignación
                logger.debug("Cargando vista de asignación para dieta: {}", dietaSeleccionada.getNombre());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/asignar-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();

                // Obtener el controlador y establecer los datos necesarios
                logger.debug("Configurando controlador de asignación");
                AsignarController controller = loader.getController();
                controller.setEmpleadoAutenticado(empleadoAutenticado);
                controller.setDieta(dietaSeleccionada);

                // Configurar la ventana
                stage.setTitle("Fitness360 - Asignar Dieta");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                // Recargar las dietas después de cerrar la ventana
                logger.debug("Recargando dietas después de asignación");
                cargarDietas();
                logger.info("Asignación de dieta completada");
            } catch (IOException e) {
                logger.error("Error al cargar la pantalla de asignación: {}", e.getMessage(), e);
                Utilidades.mostrarAlerta("Error", "No se pudo abrir la ventana de asignación", Alert.AlertType.ERROR);
            }
        } else {
            logger.warn("No se seleccionó ninguna dieta para asignar");
            Utilidades.mostrarAlerta("Selección requerida", "Por favor, seleccione una dieta para asignar", Alert.AlertType.WARNING);
        }
    }
}
