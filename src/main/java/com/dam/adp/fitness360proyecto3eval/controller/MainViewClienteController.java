package com.dam.adp.fitness360proyecto3eval.controller;

import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainViewClienteController {
    public TabPane tabPane;
    public Tab tabRutinas;
    public Tab tabDietas;
    public Tab tabRevisiones;
    public Tab tabContratarEntrenador;
    public Label labelUsuario;
    public ComboBox comboFiltroRutinas;
    public TableView tablaRutinas;
    public TableColumn colNombreRutina;
    public TableColumn colDescripcionRutina;
    public TableColumn colCreadorRutina;
    public TableColumn colFechaRutina;
    public TableColumn colAccionesRutina;
    public TableView tablaDietas;
    public TableColumn colNombreDieta;
    public TableColumn colDescripcionDieta;
    public TableColumn colCreadorDieta;
    public TableColumn colFechaDieta;
    public TableColumn colAccionesDieta;
    public TableView tablaRevisiones;
    public TableColumn colFechaRevision;
    public TableColumn colPesoRevision;
    public TableColumn colGrasaRevision;
    public TableColumn colMusculoRevision;
    public TableColumn colEntrenadorRevision;
    public TableColumn colObservacionesRevision;
    public TableColumn colAccionesRevision;
    public ComboBox comboFiltroEspecialidad;
    public TableView tablaEntrenadores;
    public TableColumn colNombreEntrenador;
    public TableColumn colEspecialidadEntrenador;
    public TableColumn colDescripcionEntrenador;
    public TableColumn colAccionesEntrenador;
    public ComboBox comboFiltroPeriodo;
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
        }
    }

}
