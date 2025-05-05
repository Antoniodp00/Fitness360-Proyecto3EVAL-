package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteTarifaDAO {

    private static final String SQL_INSERT =
            "INSERT INTO ClienteTarifa (idCliente, idTarifa, estado, fechaContratacion, fechaRenovacion, fechaFin) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_CLIENTE =
            "SELECT ct.*, " +
                    "c.*, " +
                    "t.* " +
                    "FROM ClienteTarifa ct " +
                    "JOIN Cliente c ON ct.idCliente = c.idCliente " +
                    "JOIN Tarifa t ON ct.idTarifa = t.idTarifa " +
                    "WHERE ct.idCliente = ?";

    private static final String SQL_FIND_BY_TARIFA =
            "SELECT ct.*, " +
                    "c.*, " +
                    "t.* " +
                    "FROM ClienteTarifa ct " +
                    "JOIN Cliente c ON ct.idCliente = c.idCliente " +
                    "JOIN Tarifa t ON ct.idTarifa = t.idTarifa " +
                    "WHERE ct.idTarifa = ?";

    private static final String SQL_DELETE =
            "DELETE FROM ClienteTarifa WHERE idCliente = ? AND idTarifa = ?";

    public static void insert(ClienteTarifa ct) {
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

    public static List<ClienteTarifa> findByCliente(int idCliente) {
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

    public static List<ClienteTarifa> findByTarifa(int idTarifa) {
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

    public static void delete(int idCliente, int idTarifa) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idTarifa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClienteTarifa mapearClienteTarifaEager(ResultSet rs) throws SQLException {
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
        String estadoCliente = rs.getString("estado");
        if (estadoCliente != null) cliente.setEstado(Estado.valueOf(estadoCliente));
        cliente.setCreatedAt(rs.getTimestamp("createdAt"));
        cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));

        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(rs.getInt("idTarifa"));
        tarifa.setNombre(rs.getString("nombre"));
        tarifa.setDescripcion(rs.getString("descripcion"));
        tarifa.setPrecio(rs.getDouble("precio"));
        tarifa.setCreatedAt(rs.getTimestamp("createdAt"));
        tarifa.setUpdatedAt(rs.getTimestamp("updatedAt"));

        ct.setCliente(cliente);
        ct.setTarifa(tarifa);
        String estadoStr = rs.getString("estado");
        if (estadoStr != null) ct.setEstado(EstadoTarifa.valueOf(estadoStr));
        ct.setFechaContratacion(rs.getDate("fechaContratacion"));
        ct.setFechaRenovacion(rs.getDate("fechaRenovacion"));
        ct.setFechaFin(rs.getDate("fechaFin"));
        ct.setCreatedAt(rs.getDate("createdAt"));
        ct.setUpdatedAt(rs.getDate("updatedAt"));
        return ct;
    }

}
