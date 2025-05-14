package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.*;
import com.dam.adp.fitness360proyecto3eval.model.*;
import com.dam.adp.fitness360proyecto3eval.model.Sesion;
import com.dam.adp.fitness360proyecto3eval.utilidades.Utilidades;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public Button btnEliminarRutina;
    public TableView<Rutina> tablaRutinas;
    public TableColumn<Rutina, String> colNombreRutina;
    public TableColumn<Rutina, String> colDescripcionRutina;
    public TableColumn<Rutina, Date> colFechaRutina;
    public TableColumn<Rutina, String> colClientesAsignadosRutina;
    private ObservableList<Rutina> rutinas = FXCollections.observableArrayList();

    // Tab Dietas
    public Tab tabDietas;
    public ComboBox comboFiltroDietas;
    public Button btnCrearDieta;
    public Button btnModificarDieta;
    public Button btnEliminarDieta;
    public TableView<Dieta> tablaDietas;
    public TableColumn<Dieta, String> colNombreDieta;
    public TableColumn<Dieta, String> colDescripcionDieta;
    public TableColumn<Dieta, Date> colFechaDieta;
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
    public TableColumn<Tarifa, Double> colPrecioTarifa;
    public TableColumn<Tarifa, Periodo> colPeriodoTarifa;
    public TableColumn<Tarifa, String> colDescripcionTarifa;
    public TableColumn<Tarifa, String> colClientesAsignadosTarifa;
    private ObservableList<Tarifa> tarifas = FXCollections.observableArrayList();

    // Tab Revisiones
    public Tab tabRevisiones;
    public ComboBox comboFiltroRevisiones;
    public Button btnNuevaRevision;
    public TableView<Revision> tablaRevisiones;
    public TableColumn<Revision, Date> colFechaRevision;
    public TableColumn<Revision, String> colClienteRevision;
    public TableColumn<Revision, Double> colPesoRevision;
    public TableColumn<Revision, Double> colGrasaRevision;
    public TableColumn<Revision, Double> colMusculoRevision;
    public TableColumn<Revision, String> colObservacionesRevision;
    public TableColumn<Revision, String> colAccionesRevision;
    private ObservableList<Revision> revisiones = FXCollections.observableArrayList();

    private UsuarioEmpleado empleadoAutenticado;

    /**
     * Establece el empleado autenticado y actualiza la interfaz
     *
     * @param empleado El empleado autenticado
     */
    /**
     * Establece el empleado autenticado y actualiza la interfaz
     *
     * @param empleado El empleado autenticado
     */
    public void setEmpleadoAutenticado(UsuarioEmpleado empleado) {
        this.empleadoAutenticado = empleado;
        if (empleado != null) {
            labelUsuario.setText("Usuario: " + empleado.getNombreUsuario());

            // Configurar los botones según la especialidad del empleado
            Especialidad especialidad = empleado.getEspecialidad();

            // Botón Crear Dieta
            boolean puedeCrearDieta = (especialidad == Especialidad.DIETISTA || especialidad == Especialidad.AMBOS);
            btnCrearDieta.setDisable(!puedeCrearDieta);
            if (puedeCrearDieta) {
                btnCrearDieta.setOnAction(this::abrirRegistroDieta);
            }

            // Botón Crear Rutina
            boolean puedeCrearRutina = (especialidad == Especialidad.ENTRENADOR || especialidad == Especialidad.AMBOS);
            btnCrearRutina.setDisable(!puedeCrearRutina);
            if (puedeCrearRutina) {
                btnCrearRutina.setOnAction(this::manejarBotonCrearRutina);
            }

            // Cargar los datos específicos del empleado
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
            return new javafx.beans.property.SimpleStringProperty(Utilidades.formatearFechaEspanol(cliente.getCreatedAt()));
        });


        // Limpiar y agregar los clientes a la lista observable
        clientes.clear();
        clientes.addAll(misClientes);

        // Asignar la lista observable a la tabla
        tablaClientes.setItems(clientes);
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

        // Formatear la fecha en español
        colFechaRutina.setCellFactory(column -> new TableCell<Rutina, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(Utilidades.formatearFechaEspanol(item));
                }
            }
        });

        // Limpiar y agregar las rutinas a la lista observable
        rutinas.clear();
        rutinas.addAll(misRutinas);

        // Asignar la lista observable a la tabla
        tablaRutinas.setItems(rutinas);
    }

    /**
     * Carga las dietas creadas por el empleado
     */
    private void cargarDietas() {
        DietaDAO dietaDAO = new DietaDAO();
        ClienteDietaDAO clienteDietaDAO = new ClienteDietaDAO();
        // Implementación para cargar las dietas creadas por el empleado
        List<Dieta> misDietas = dietaDAO.getByCreator(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colNombreDieta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionDieta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFechaDieta.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Formatear la fecha en español
        colFechaDieta.setCellFactory(column -> new TableCell<Dieta, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(Utilidades.formatearFechaEspanol(item));
                }
            }
        });
        colClientesAsignadosDieta.setCellValueFactory(new PropertyValueFactory<>("clientesAsignados"));

        // Limpiar y agregar las dietas a la lista observable
        dietas.clear();
        dietas.addAll(misDietas);

        // Asignar la lista observable a la tabla
        tablaDietas.setItems(dietas);
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

        // Limpiar y agregar las tarifas a la lista observable
        tarifas.clear();
        tarifas.addAll(misTarifas);

        // Asignar la lista observable a la tabla
        tablaTarifas.setItems(tarifas);
    }

    /**
     * Carga las revisiones realizadas por el empleado
     */
    private void cargarRevisiones() {
        RevisionDAO revisionDAO = new RevisionDAO();
        // Implementación para cargar las revisiones realizadas por el empleado
        List<Revision> misRevisiones = revisionDAO.getByCreatorEager(empleadoAutenticado.getId());

        // Configurar las columnas de la tabla
        colFechaRevision.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        // Formatear la fecha en español
        colFechaRevision.setCellFactory(column -> new TableCell<Revision, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(Utilidades.formatearFechaEspanol(item));
                }
            }
        });
        colClienteRevision.setCellValueFactory(new PropertyValueFactory<>("nombreClienteCompleto"));
        colPesoRevision.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colGrasaRevision.setCellValueFactory(new PropertyValueFactory<>("grasa"));
        colMusculoRevision.setCellValueFactory(new PropertyValueFactory<>("musculo"));
        colObservacionesRevision.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        // Limpiar y agregar las revisiones a la lista observable
        revisiones.clear();
        revisiones.addAll(misRevisiones);

        // Asignar la lista observable a la tabla
        tablaRevisiones.setItems(revisiones);
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
            System.err.println("Error al cargar la pantalla de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra el formulario para añadir o editar una rutina
     *
     * @param rutina La rutina a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarRutina(Rutina rutina) {
        try {
            // Cargar la vista de registro de rutina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-rutina-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            RegistroRutinaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la rutina si se está editando
            if (rutina != null) {
                controller.setRutina(rutina);
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
            mostrarAlerta("Selección requerida", "Por favor, seleccione una rutina para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Método obsoleto, reemplazado por manejarBotonEditarRutina
     *
     * @param event El evento que desencadenó esta acción
     */
    public void abrirModificarRutina(ActionEvent event) {
        manejarBotonEditarRutina(event);
    }

    /**
     * Muestra el formulario para añadir o editar una dieta
     *
     * @param dieta La dieta a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarDieta(Dieta dieta) {
        try {
            // Cargar la vista de registro de dieta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-dieta-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            RegistroDietaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la dieta si se está editando
            if (dieta != null) {
                controller.setDieta(dieta);
                stage.setTitle("Fitness360 - Modificar Dieta");
            } else {
                stage.setTitle("Fitness360 - Añadir Dieta");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las dietas después de cerrar el formulario
            cargarDietas();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de dieta: " + e.getMessage());
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
            mostrarAlerta("Selección requerida", "Por favor, seleccione una dieta para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra el formulario para añadir o editar una tarifa
     *
     * @param tarifa La tarifa a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarTarifa(Tarifa tarifa) {
        try {
            // Cargar la vista de registro de tarifa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-tarifa-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            RegistroTarifaController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la tarifa si se está editando
            if (tarifa != null) {
                controller.setTarifa(tarifa);
                stage.setTitle("Fitness360 - Modificar Tarifa");
            } else {
                stage.setTitle("Fitness360 - Añadir Tarifa");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las tarifas después de cerrar el formulario
            cargarTarifas();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de tarifa: " + e.getMessage());
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
            mostrarAlerta("Selección requerida", "Por favor, seleccione una tarifa para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra el formulario para añadir o editar una revisión
     *
     * @param revision La revisión a editar, o null para crear una nueva
     */
    public void mostrarFormularioAñadirEditarRevision(Revision revision) {
        try {
            // Cargar la vista de registro de revisión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/registro-revision-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            // Obtener el controlador y establecer el empleado autenticado
            RegistroRevisionController controller = loader.getController();
            controller.setEmpleadoAutenticado(empleadoAutenticado);

            // Configurar la revisión si se está editando
            if (revision != null) {
                controller.setRevision(revision);
                stage.setTitle("Fitness360 - Modificar Revisión");
            } else {
                stage.setTitle("Fitness360 - Nueva Revisión");
            }

            // Configurar la modalidad para que bloquee la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Recargar las revisiones después de cerrar el formulario
            cargarRevisiones();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de registro de revisión: " + e.getMessage());
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
            mostrarAlerta("Selección requerida", "Por favor, seleccione una revisión para editar", Alert.AlertType.WARNING);
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
     * Inicializa el controlador y configura los eventos
     */
    @FXML
    public void initialize() {
        // Configurar el evento de clic para el botón de cerrar sesión
        btnCerrarSesion.setOnAction(this::cerrarSesion);

        // Configurar eventos para los botones de crear
        btnCrearRutina.setOnAction(this::manejarBotonCrearRutina);
        btnCrearDieta.setOnAction(this::abrirRegistroDieta);
        btnCrearTarifa.setOnAction(this::abrirRegistroTarifa);
        btnNuevaRevision.setOnAction(this::abrirRegistroRevision);

        // Configurar eventos para los botones de modificar
        btnModificarRutina.setOnAction(this::manejarBotonEditarRutina);
        btnModificarDieta.setOnAction(this::manejarBotonEditarDieta);
        btnModificarTarifa.setOnAction(this::manejarBotonEditarTarifa);

        // Configurar eventos para los botones de eliminar
        btnEliminarRutina.setOnAction(this::manejarBotonBorrarRutina);
        btnEliminarDieta.setOnAction(this::manejarBotonBorrarDieta);
        btnEliminarTarifa.setOnAction(this::manejarBotonBorrarTarifa);

        // Obtener el empleado autenticado de la sesión
        if (Sesion.getInstance().isEmpleado()) {
            setEmpleadoAutenticado(Sesion.getInstance().getEmpleadoAutenticado());
        }
    }

    /**
     * Muestra una alerta con el título, mensaje y tipo especificados.
     *
     * @param titulo  Título de la alerta
     * @param mensaje Mensaje de la alerta
     * @param tipo    Tipo de alerta
     * @deprecated Use Utilidades.mostrarAlerta instead
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Utilidades.mostrarAlerta(titulo, mensaje, tipo);
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

                    mostrarAlerta("Dieta eliminada", "La dieta ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    System.err.println("Error al eliminar la dieta: " + e.getMessage());
                    mostrarAlerta("Error", "No se pudo eliminar la dieta", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione una dieta para eliminar", Alert.AlertType.WARNING);
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

                    mostrarAlerta("Tarifa eliminada", "La tarifa ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    System.err.println("Error al eliminar la tarifa: " + e.getMessage());
                    mostrarAlerta("Error", "No se pudo eliminar la tarifa", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione una tarifa para eliminar", Alert.AlertType.WARNING);
        }
    }
}
