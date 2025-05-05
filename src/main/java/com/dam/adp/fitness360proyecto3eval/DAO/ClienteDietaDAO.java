package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDietaDAO {
    private static final String SQL_INSERT =
            "INSERT INTO ClienteDieta (idCliente, idDieta, fechaAsignacion, fechaFin) VALUES (?, ?, ?, ?)";

    private static final String SQL_FIND_BY_CLIENTE =
            "SELECT cd.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "d.idDieta, d.nombre as nombreDieta, d.descripcion as descripcionDieta, " +
                    "d.createdAt as createdAtDieta, d.updatedAt as updatedAtDieta " +
                    "FROM ClienteDieta cd " +
                    "JOIN Cliente c ON cd.idCliente = c.idCliente " +
                    "JOIN Dieta d ON cd.idDieta = d.idDieta " +
                    "WHERE cd.idCliente = ?";

    private static final String SQL_FIND_BY_DIETA =
            "SELECT cd.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "d.idDieta, d.nombre as nombreDieta, d.descripcion as descripcionDieta, " +
                    "d.createdAt as createdAtDieta, d.updatedAt as updatedAtDieta " +
                    "FROM ClienteDieta cd " +
                    "JOIN Cliente c ON cd.idCliente = c.idCliente " +
                    "JOIN Dieta d ON cd.idDieta = d.idDieta " +
                    "WHERE cd.idDieta = ?";

    private static final String SQL_DELETE =
            "DELETE FROM ClienteDieta WHERE idCliente = ? AND idDieta = ?";

    public static void insert(ClienteDieta cd) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setInt(1, cd.getCliente().getId());
            stmt.setInt(2, cd.getDieta().getIdDieta());
            stmt.setDate(3, (Date) cd.getFechaAsignacion());
            stmt.setDate(4, (Date) cd.getFechaFin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ClienteDieta> findByClient(int idCliente) {
        List<ClienteDieta> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CLIENTE)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteDieta(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static List<ClienteDieta> findByClientEager(int idCliente) {
        List<ClienteDieta> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CLIENTE)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteDietaEager(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static List<ClienteDieta> findByDieta(int idDieta) {
        List<ClienteDieta> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_DIETA)) {

            stmt.setInt(1, idDieta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteDieta(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static List<ClienteDieta> findByDietaEager(int idDieta) {
        List<ClienteDieta> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_DIETA)) {

            stmt.setInt(1, idDieta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteDietaEager(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static void delete(int idCliente, int idDieta) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idDieta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClienteDieta mapearClienteDieta(ResultSet rs) throws SQLException {
        ClienteDieta cd = new ClienteDieta();

        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(rs.getInt("idUsuario"));
        cd.setCliente(cliente);

        Dieta dieta = new Dieta();
        dieta.setIdDieta(rs.getInt("idDieta"));
        cd.setDieta(dieta);

        cd.setFechaAsignacion(rs.getDate("fechaAsignacion"));
        cd.setFechaFin(rs.getDate("fechaFin"));

        return cd;
    }

    private static ClienteDieta mapearClienteDietaEager(ResultSet rs) throws SQLException {
        ClienteDieta cd = new ClienteDieta();

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
        if (sexoStr != null) {
            cliente.setSexo(Sexo.valueOf(sexoStr));
        }

        cliente.setAltura(rs.getDouble("altura"));

        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            cliente.setEstado(Estado.valueOf(estadoStr));
        }

        cliente.setCreatedAt(rs.getTimestamp("createdAt"));
        cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
        cd.setCliente(cliente);

        Dieta dieta = new Dieta();
        dieta.setIdDieta(rs.getInt("idDieta"));
        dieta.setNombre(rs.getString("nombreDieta"));
        dieta.setDescripcion(rs.getString("descripcionDieta"));
        dieta.setCreatedAt(rs.getTimestamp("createdAtDieta"));
        dieta.setUpdatedAt(rs.getTimestamp("updatedAtDieta"));
        cd.setDieta(dieta);

        cd.setFechaAsignacion(rs.getDate("fechaAsignacion"));
        cd.setFechaFin(rs.getDate("fechaFin"));

        return cd;
    }
}
