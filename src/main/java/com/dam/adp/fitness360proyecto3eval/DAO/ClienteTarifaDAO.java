package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con la asignación de tarifas a clientes en la base de datos.
 * Proporciona métodos para insertar, buscar y eliminar asignaciones de tarifas a clientes.
 */
public class ClienteTarifaDAO {

    /** Consulta SQL para insertar una nueva asignación de tarifa a cliente */
    private static final String SQL_INSERT =
            "INSERT INTO ClienteTarifa (idCliente, idTarifa, estado, fechaContratacion, fechaRenovacion, fechaFin) VALUES (?, ?, ?, ?, ?, ?)";

    /** Consulta SQL para buscar asignaciones de tarifas por cliente */
    private static final String SQL_FIND_BY_CLIENTE =
            "SELECT ct.idCliente, ct.idTarifa, ct.estado AS estadoTarifa, ct.fechaContratacion, ct.fechaRenovacion, ct.fechaFin, ct.createdAt, ct.updatedAt, " +
                    "c.idCliente, c.nombreUsuario, c.nombre AS nombreCliente, c.apellidos, c.correo, c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, c.estado AS estadoCliente, c.createdAt, c.updatedAt, " +
                    "t.idTarifa, t.nombre, t.descripcion, t.precio, t.periodo, t.createdAt, t.updatedAt, e.idEmpleado, e.nombre AS nombreEmpleado, e.apellidos AS apellidosEmpleado " +
                    "FROM ClienteTarifa ct " +
                    "JOIN Cliente c ON ct.idCliente = c.idCliente " +
                    "JOIN Tarifa t ON ct.idTarifa = t.idTarifa " +
                    "JOIN Empleado e ON t.idEmpleado = e.idEmpleado " +
                    "WHERE ct.idCliente = ?";

    /** Consulta SQL para buscar asignaciones de clientes por tarifa */
    private static final String SQL_FIND_BY_TARIFA =
            "SELECT ct.idCliente, ct.idTarifa, ct.estado AS estadoTarifa, ct.fechaContratacion, ct.fechaRenovacion, ct.fechaFin, ct.createdAt, ct.updatedAt, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, c.estado AS estadoCliente, c.createdAt, c.updatedAt, " +
                    "t.idTarifa, t.nombre, t.descripcion, t.precio, t.createdAt, t.updatedAt, e.idEmpleado, e.nombre AS nombreEmpleado, e.apellidos AS apellidosEmpleado " +
                    "FROM ClienteTarifa ct " +
                    "JOIN Cliente c ON ct.idCliente = c.idCliente " +
                    "JOIN Tarifa t ON ct.idTarifa = t.idTarifa " +
                    "WHERE ct.idTarifa = ?";

    /** Consulta SQL para eliminar una asignación de tarifa a cliente */
    private static final String SQL_DELETE =
            "DELETE FROM ClienteTarifa WHERE idCliente = ? AND idTarifa = ?";

    /** Consulta SQL para obtener todas las asignaciones de tarifas a clientes */
    private static final String SQL_GET_ALL =
            "SELECT ct.idCliente, ct.idTarifa, ct.estado AS estadoTarifa, ct.fechaContratacion, ct.fechaRenovacion, ct.fechaFin, ct.createdAt, ct.updatedAt, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, c.estado AS estadoCliente, c.createdAt, c.updatedAt, " +
                    "t.idTarifa, t.nombre, t.descripcion, t.precio, t.createdAt, t.updatedAt " +
                    "FROM ClienteTarifa ct " +
                    "JOIN Cliente c ON ct.idCliente = c.idCliente " +
                    "JOIN Tarifa t ON ct.idTarifa = t.idTarifa";

    /** Consulta SQL para actualizar una asignación de tarifa a cliente */
    private static final String SQL_UPDATE =
            "UPDATE ClienteTarifa SET estado = ?, fechaContratacion = ?, fechaRenovacion = ?, fechaFin = ? WHERE idCliente = ? AND idTarifa = ?";

    /**
     * Inserta una nueva asignación de tarifa a cliente en la base de datos
     * 
     * @param ct Objeto ClienteTarifa con los datos a insertar
     */
    public void insert(ClienteTarifa ct) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setInt(1, ct.getCliente().getId());
            stmt.setInt(2, ct.getTarifa().getIdTarifa());
            stmt.setString(3, ct.getEstado().name());
            stmt.setDate(4, (Date) ct.getFechaContratacion());
            stmt.setDate(5, (Date) ct.getFechaRenovacion());
            stmt.setDate(6, (Date) ct.getFechaFin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca todas las asignaciones de tarifas para un cliente específico
     * 
     * @param idCliente ID del cliente a buscar
     * @return Lista de objetos ClienteTarifa con las asignaciones encontradas
     */
    public List<ClienteTarifa> findByCliente(int idCliente) {
        List<ClienteTarifa> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CLIENTE)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapearClienteTarifaEager(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Busca todas las asignaciones de clientes para una tarifa específica
     * 
     * @param idTarifa ID de la tarifa a buscar
     * @return Lista de objetos ClienteTarifa con las asignaciones encontradas
     */
    public List<ClienteTarifa> findByTarifa(int idTarifa) {
        List<ClienteTarifa> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_TARIFA)) {
            stmt.setInt(1, idTarifa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapearClienteTarifaEager(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Elimina una asignación de tarifa a cliente de la base de datos
     * 
     * @param idCliente ID del cliente
     * @param idTarifa ID de la tarifa
     */
    public void delete(int idCliente, int idTarifa) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idTarifa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto ClienteTarifa incluyendo los objetos Cliente y Tarifa relacionados
     * 
     * @param rs ResultSet con los datos a mapear
     * @return Objeto ClienteTarifa con todos los datos
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private ClienteTarifa mapearClienteTarifaEager(ResultSet rs) throws SQLException {
        ClienteTarifa ct = new ClienteTarifa();

        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(rs.getInt("idCliente"));
        cliente.setNombreUsuario(rs.getString("nombreUsuario"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setPassword(rs.getString("password"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        String sexoStr = rs.getString("sexo");
        if (sexoStr != null) cliente.setSexo(Sexo.valueOf(sexoStr));
        cliente.setAltura(rs.getDouble("altura"));
        String estadoCliente = rs.getString("estadoCliente");
        if (estadoCliente != null) cliente.setEstado(Estado.valueOf(estadoCliente));
        cliente.setCreatedAt(rs.getTimestamp("createdAt"));
        cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));

        UsuarioEmpleado empleado = new UsuarioEmpleado();
        empleado.setId(rs.getInt("idEmpleado"));
        empleado.setNombre(rs.getString("nombreEmpleado"));
        empleado.setApellidos(rs.getString("apellidosEmpleado"));


        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(rs.getInt("idTarifa"));
        tarifa.setNombre(rs.getString("nombre"));
        tarifa.setDescripcion(rs.getString("descripcion"));
        tarifa.setPrecio(rs.getDouble("precio"));
        tarifa.setPeriodo(Periodo.valueOf(rs.getString("periodo")));
        tarifa.setCreador(empleado);
        tarifa.setCreatedAt(rs.getTimestamp("createdAt"));
        tarifa.setUpdatedAt(rs.getTimestamp("updatedAt"));


        ct.setCliente(cliente);
        ct.setTarifa(tarifa);
        String estadoStr = rs.getString("estadoTarifa");
        if (estadoStr != null) ct.setEstado(EstadoTarifa.valueOf(estadoStr));
        ct.setFechaContratacion(rs.getDate("fechaContratacion"));
        ct.setFechaRenovacion(rs.getDate("fechaRenovacion"));
        ct.setFechaFin(rs.getDate("fechaFin"));
        ct.setCreatedAt(rs.getDate("createdAt"));
        ct.setUpdatedAt(rs.getDate("updatedAt"));
        return ct;
    }

    /**
     * Obtiene todas las asignaciones de tarifas a clientes
     * 
     * @return Lista de todas las asignaciones
     */
    public List<ClienteTarifa> getAll() {
        List<ClienteTarifa> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearClienteTarifaEager(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Actualiza una asignación de tarifa a cliente
     * 
     * @param ct Objeto ClienteTarifa con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(ClienteTarifa ct) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, ct.getEstado().name());
            stmt.setDate(2, (Date) ct.getFechaContratacion());
            stmt.setDate(3, (Date) ct.getFechaRenovacion());
            stmt.setDate(4, (Date) ct.getFechaFin());
            stmt.setInt(5, ct.getCliente().getId());
            stmt.setInt(6, ct.getTarifa().getIdTarifa());

            int filas = stmt.executeUpdate();
            actualizado = filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }
}
