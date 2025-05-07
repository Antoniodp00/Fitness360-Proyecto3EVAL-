package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.DAO.ClienteDietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.ClienteRutinaDAO;
import com.dam.adp.fitness360proyecto3eval.model.ClienteDieta;
import com.dam.adp.fitness360proyecto3eval.model.ClienteRutina;
import com.dam.adp.fitness360proyecto3eval.model.Rutina;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private UsuarioCliente clienteAutenticado;

    /**
     * Establece el cliente autenticado y actualiza la interfaz
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

    private void cargarRevisiones() {}

}
