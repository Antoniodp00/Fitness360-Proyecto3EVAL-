package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con la asignación de dietas a clientes en la base de datos.
 * Proporciona métodos para insertar, buscar y eliminar asignaciones de dietas a clientes.
 */
public class ClienteDietaDAO implements GenericDAO<ClienteDieta> {
    /** Consulta SQL para insertar una nueva asignación de dieta a cliente */
    private static final String SQL_INSERT =
            "INSERT INTO ClienteDieta (idCliente, idDieta, fechaAsignacion, fechaFin) VALUES (?, ?, ?, ?)";

    /** Consulta SQL para obtener todas las asignaciones de dietas a clientes */
    private static final String SQL_GET_ALL =
            "SELECT cd.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "d.idDieta, d.nombre as nombreDieta, d.descripcion as descripcionDieta, " +
                    "d.createdAt as createdAtDieta, d.updatedAt as updatedAtDieta " +
                    "FROM ClienteDieta cd " +
                    "JOIN Cliente c ON cd.idCliente = c.idCliente " +
                    "JOIN Dieta d ON cd.idDieta = d.idDieta";

    /** Consulta SQL para buscar asignaciones de dietas por cliente con información completa */
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

    /** Consulta SQL para buscar asignaciones de clientes por dieta con información completa */
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

    /** Consulta SQL para eliminar una asignación de dieta a cliente */
    private static final String SQL_DELETE =
            "DELETE FROM ClienteDieta WHERE idCliente = ? AND idDieta = ?";

    /**
     * Obtiene todas las asignaciones de dietas a clientes
     * 
     * @return Lista de objetos ClienteDieta con todas las asignaciones
     */
    @Override
    public List<ClienteDieta> getAll() {
        List<ClienteDieta> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_GET_ALL)) {

            while (rs.next()) {
                lista.add(mapearClienteDietaEager(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Obtiene una asignación de dieta a cliente por su ID
     * Nota: En este caso, no hay un ID único para ClienteDieta, por lo que este método
     * no es aplicable directamente. Se implementa para cumplir con la interfaz.
     * 
     * @param id ID de la asignación (no aplicable)
     * @return null ya que no hay un ID único
     */
    @Override
    public ClienteDieta getById(int id) {
        // No hay un ID único para ClienteDieta, por lo que este método no es aplicable
        return null;
    }

    /**
     * Inserta una nueva asignación de dieta a cliente en la base de datos
     * 
     * @param entity Objeto ClienteDieta con los datos a insertar
     * @return El objeto ClienteDieta insertado
     */
    @Override
    public ClienteDieta insert(ClienteDieta entity) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setInt(1, entity.getCliente().getId());
            stmt.setInt(2, entity.getDieta().getIdDieta());
            stmt.setDate(3, (Date) entity.getFechaAsignacion());
            stmt.setDate(4, (Date) entity.getFechaFin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Actualiza una asignación de dieta a cliente en la base de datos
     * Nota: En este caso, no hay una operación de actualización específica para ClienteDieta,
     * por lo que este método simplemente elimina la asignación existente y crea una nueva.
     * 
     * @param entity Objeto ClienteDieta con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    @Override
    public boolean update(ClienteDieta entity) {
        try {
            delete(entity);
            insert(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina una asignación de dieta a cliente de la base de datos
     * 
     * @param entity Objeto ClienteDieta a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    @Override
    public boolean delete(ClienteDieta entity) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, entity.getCliente().getId());
            stmt.setInt(2, entity.getDieta().getIdDieta());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca asignaciones de dietas para un cliente específico (versión básica)
     * 
     * @param idCliente ID del cliente a buscar
     * @return Lista de objetos ClienteDieta con las asignaciones encontradas
     */
    public List<ClienteDieta> findByClient(int idCliente) {
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

    /**
     * Busca asignaciones de dietas para un cliente específico (versión completa)
     * Incluye información detallada del cliente y la dieta
     * 
     * @param idCliente ID del cliente a buscar
     * @return Lista de objetos ClienteDieta con las asignaciones encontradas y datos completos
     */
    public List<ClienteDieta> findByClientEager(int idCliente) {
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

    /**
     * Busca asignaciones de clientes para una dieta específica (versión básica)
     * 
     * @param idDieta ID de la dieta a buscar
     * @return Lista de objetos ClienteDieta con las asignaciones encontradas
     */
    public List<ClienteDieta> findByDieta(int idDieta) {
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

    /**
     * Busca asignaciones de clientes para una dieta específica (versión completa)
     * Incluye información detallada del cliente y la dieta
     * 
     * @param idDieta ID de la dieta a buscar
     * @return Lista de objetos ClienteDieta con las asignaciones encontradas y datos completos
     */
    public List<ClienteDieta> findByDietaEager(int idDieta) {
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

    /**
     * Elimina una asignación de dieta a cliente de la base de datos
     * 
     * @param idCliente ID del cliente
     * @param idDieta ID de la dieta
     */
    public void deleteByIds(int idCliente, int idDieta) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idDieta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto ClienteDieta con referencias básicas
     * Solo incluye los IDs de cliente y dieta
     * 
     * @param rs ResultSet con los datos a mapear
     * @return Objeto ClienteDieta con datos básicos
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private ClienteDieta mapearClienteDieta(ResultSet rs) throws SQLException {
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

    /**
     * Mapea un ResultSet a un objeto ClienteDieta incluyendo los objetos Cliente y Dieta completos
     * 
     * @param rs ResultSet con los datos a mapear
     * @return Objeto ClienteDieta con todos los datos relacionados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private ClienteDieta mapearClienteDietaEager(ResultSet rs) throws SQLException {
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
