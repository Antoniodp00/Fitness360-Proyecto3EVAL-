package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con la asignación de rutinas a clientes en la base de datos.
 * Proporciona métodos para insertar, buscar y eliminar asignaciones de rutinas a clientes.
 */
public class ClienteRutinaDAO {
    /**
     * Consulta SQL para insertar una nueva asignación de rutina a cliente
     */
    private static final String SQL_INSERT =
            "INSERT INTO UsuarioRutina (idUsuario, idRutina, fechaAsignacion, fechaFin) VALUES (?, ?, ?, ?)";

    /**
     * Consulta SQL para buscar asignaciones de rutinas por ID de usuario (cliente) con carga completa de datos
     */
    private static final String SQL_FIND_BY_USUARIO =
            "SELECT ur.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "r.nombre as nombreRutina, r.descripcion as descripcionRutina, " +
                    "r.createdAt as createdAtRutina, r.updatedAt as updatedAtRutina " +
                    "FROM UsuarioRutina ur " +
                    "JOIN Cliente c ON ur.idUsuario = c.idCliente " +
                    "JOIN Rutina r ON ur.idRutina = r.idRutina " +
                    "WHERE ur.idUsuario = ?";

    /**
     * Consulta SQL para buscar asignaciones por ID de rutina
     */
    private static final String SQL_FIND_BY_RUTINA =
            "SELECT ur.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "r.nombre as nombreRutina, r.descripcion as descripcionRutina, " +
                    "r.createdAt as createdAtRutina, r.updatedAt as updatedAtRutina " +
                    "FROM UsuarioRutina ur " +
                    "JOIN Cliente c ON ur.idUsuario = c.idCliente " +
                    "JOIN Rutina r ON ur.idRutina = r.idRutina " +
                    "WHERE ur.idRutina = ?";

    /**
     * Consulta SQL para eliminar una asignación de rutina a cliente
     */
    private static final String SQL_DELETE =
            "DELETE FROM UsuarioRutina WHERE idUsuario = ? AND idRutina = ?";

    /**
     * Consulta SQL para obtener todas las asignaciones de rutinas a clientes
     */
    private static final String SQL_GET_ALL =
            "SELECT ur.*, " +
                    "c.idCliente, c.nombreUsuario, c.nombre, c.apellidos, c.correo, " +
                    "c.password, c.telefono, c.fechaNacimiento, c.sexo, c.altura, " +
                    "c.estado, c.createdAt, c.updatedAt, " +
                    "r.nombre as nombreRutina, r.descripcion as descripcionRutina, " +
                    "r.createdAt as createdAtRutina, r.updatedAt as updatedAtRutina " +
                    "FROM UsuarioRutina ur " +
                    "JOIN Cliente c ON ur.idUsuario = c.idCliente " +
                    "JOIN Rutina r ON ur.idRutina = r.idRutina";

    /**
     * Consulta SQL para actualizar una asignación de rutina a cliente
     */
    private static final String SQL_UPDATE =
            "UPDATE UsuarioRutina SET fechaAsignacion = ?, fechaFin = ? WHERE idUsuario = ? AND idRutina = ?";

    /**
     * Inserta una nueva asignación de rutina a cliente en la base de datos
     *
     * @param cr Objeto ClienteRutina con los datos de la asignación a insertar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static void insert(ClienteRutina cr) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setInt(1, cr.getCliente().getId());
            stmt.setInt(2, cr.getRutina().getIdRutina());
            stmt.setDate(3, (Date) cr.getFechaAsignacion());
            stmt.setDate(4, (Date) cr.getFechaFin());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca las asignaciones de rutinas para un cliente específico
     *
     * @param idCliente ID del cliente
     * @return Lista de asignaciones de rutinas para el cliente
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<ClienteRutina> findByClient(int idCliente) {
        List<ClienteRutina> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_BY_USUARIO)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteRutina(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Busca las asignaciones de rutinas para un cliente específico con carga completa de datos (eager loading)
     *
     * @param idCliente ID del cliente
     * @return Lista de asignaciones de rutinas para el cliente con datos completos
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<ClienteRutina> findByClientEager(int idCliente) {
        List<ClienteRutina> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_USUARIO)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteRutinaEager(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Busca las asignaciones de clientes para una rutina específica
     *
     * @param idRutina ID de la rutina
     * @return Lista de asignaciones de clientes para la rutina
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<ClienteRutina> findByRutine(int idRutina){
        List<ClienteRutina> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_RUTINA)) {

            stmt.setInt(1, idRutina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearClienteRutina(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Busca las asignaciones de clientes para una rutina específica con carga completa de datos (eager loading)
     *
     * @param idRutina ID de la rutina
     * @return Lista de asignaciones de clientes para la rutina con datos completos
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<ClienteRutina> findByRutineEager(int idRutina){
        List<ClienteRutina> lista = new ArrayList<>();
        Connection conn = ConnectionDB.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_RUTINA);
            stmt.setInt(1, idRutina);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapearClienteRutinaEager(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    /**
     * Elimina una asignación de rutina a cliente de la base de datos
     *
     * @param idCliente ID del cliente
     * @param idRutina  ID de la rutina
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static void delete(int idCliente, int idRutina){
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idRutina);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto ClienteRutina
     *
     * @param rs ResultSet con los datos de la asignación
     * @return Objeto ClienteRutina con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private static ClienteRutina mapearClienteRutina(ResultSet rs) throws SQLException {
        ClienteRutina cr = new ClienteRutina();

        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(rs.getInt("idUsuario"));
        cr.setCliente(cliente);

        Rutina rutina = new Rutina();
        rutina.setIdRutina(rs.getInt("idRutina"));
        cr.setRutina(rutina);

        cr.setFechaAsignacion(rs.getDate("fechaAsignacion"));
        cr.setFechaFin(rs.getDate("fechaFin"));

        return cr;
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto ClienteRutina con carga completa de datos (eager loading)
     * Incluye todos los datos del cliente y la rutina
     *
     * @param rs ResultSet con los datos completos de la asignación, cliente y rutina
     * @return Objeto ClienteRutina con los datos completos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private static ClienteRutina mapearClienteRutinaEager(ResultSet rs) throws SQLException {
        ClienteRutina cr = new ClienteRutina();

        // Mapeo del Cliente
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
        cr.setCliente(cliente);  // Asignamos el cliente completo al objeto ClienteRutina

        // Mapeo de la Rutina
        Rutina rutina = new Rutina();
        rutina.setIdRutina(rs.getInt("idRutina"));
        rutina.setNombre(rs.getString("nombreRutina"));
        rutina.setDescripcion(rs.getString("descripcionRutina"));
        rutina.setCreatedAt(rs.getTimestamp("createdAtRutina"));
        rutina.setUpdatedAt(rs.getTimestamp("updatedAtRutina"));
        cr.setRutina(rutina);

        // Fecha de asignación y fecha de finalización
        cr.setFechaAsignacion(rs.getDate("fechaAsignacion"));
        cr.setFechaFin(rs.getDate("fechaFin"));

        return cr;
    }

    /**
     * Obtiene todas las asignaciones de rutinas a clientes
     *
     * @return Lista de todas las asignaciones
     */
    public static List<ClienteRutina> getAll() {
        List<ClienteRutina> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearClienteRutinaEager(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Actualiza una asignación de rutina a cliente
     *
     * @param cr Objeto ClienteRutina con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public static boolean update(ClienteRutina cr) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setDate(1, (Date) cr.getFechaAsignacion());
            stmt.setDate(2, (Date) cr.getFechaFin());
            stmt.setInt(3, cr.getCliente().getId());
            stmt.setInt(4, cr.getRutina().getIdRutina());

            int filas = stmt.executeUpdate();
            actualizado = filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }
}
