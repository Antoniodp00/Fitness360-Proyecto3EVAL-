package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las rutinas en la base de datos.
 * Proporciona métodos para insertar, buscar, actualizar y eliminar rutinas.
 */
public class RutinaDAO {
    /** Consulta SQL para insertar una nueva rutina */
    private static final String SQL_INSERT =
            "INSERT INTO Rutina (nombre, descripcion, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una rutina por su ID */
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Rutina WHERE idRutina = ?";

    /** Consulta SQL para obtener todas las rutinas */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Rutina";

    /** Consulta SQL para actualizar los datos de una rutina */
    private static final String SQL_UPDATE =
            "UPDATE Rutina SET nombre = ?, descripcion = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRutina = ?";

    /** Consulta SQL para eliminar una rutina */
    private static final String SQL_DELETE =
            "DELETE FROM Rutina WHERE idRutina = ?";

    /** Consulta SQL para obtener las rutinas creadas por un empleado */
    private static final String SQL_GET_BY_CREATOR =
            "SELECT * FROM Rutina WHERE idEmpleado = ?";

    /**
     * Inserta una nueva rutina en la base de datos
     * 
     * @param rutina Objeto Rutina con los datos de la rutina a insertar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static void insertar(Rutina rutina) throws SQLException {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rutina.getNombre());
            stmt.setString(2, rutina.getDescripcion());
            stmt.setInt(3, rutina.getCliente().getId());
            stmt.setInt(4, rutina.getCreador().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    rutina.setIdRutina(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Busca una rutina por su ID
     * 
     * @param idRutina ID de la rutina a buscar
     * @return Objeto Rutina si se encuentra, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Rutina buscarPorId(int idRutina) throws SQLException {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_ID)) {

            stmt.setInt(1, idRutina);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRutina(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todas las rutinas de la base de datos
     * 
     * @return Lista de todas las rutinas
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<Rutina> obtenerTodas() throws SQLException {
        List<Rutina> rutinas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rutinas.add(mapearRutina(rs));
            }
        }
        return rutinas;
    }

    /**
     * Obtiene todas las rutinas creadas por un empleado específico
     * 
     * @param idEmpleado ID del empleado creador
     * @return Lista de rutinas creadas por el empleado
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<Rutina> getByCreator(int idEmpleado) throws SQLException {
        List<Rutina> rutinas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CREATOR)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearRutina(rs));
                }
            }
        }
        return rutinas;
    }

    /**
     * Actualiza los datos de una rutina en la base de datos
     * 
     * @param rutina Objeto Rutina con los datos actualizados
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static void actualizar(Rutina rutina) throws SQLException {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, rutina.getNombre());
            stmt.setString(2, rutina.getDescripcion());
            stmt.setInt(3, rutina.getCliente().getId());
            stmt.setInt(4, rutina.getCreador().getId());
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(6, rutina.getIdRutina());

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina una rutina de la base de datos
     * 
     * @param idRutina ID de la rutina a eliminar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static void eliminar(int idRutina) throws SQLException {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, idRutina);
            stmt.executeUpdate();
        }
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto Rutina
     * 
     * @param rs ResultSet con los datos de la rutina
     * @return Objeto Rutina con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private static Rutina mapearRutina(ResultSet rs) throws SQLException {
        Rutina rutina = new Rutina();
        rutina.setIdRutina(rs.getInt("idRutina"));
        rutina.setNombre(rs.getString("nombre"));
        rutina.setDescripcion(rs.getString("descripcion"));

        int idCliente = rs.getInt("idCliente");
        if (!rs.wasNull()) {
            UsuarioCliente cliente = new UsuarioCliente();
            cliente.setId(idCliente);
            rutina.setCliente(cliente);
        }

        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            UsuarioEmpleado empleado = new UsuarioEmpleado();
            empleado.setId(idEmpleado);
            rutina.setCreador(empleado);
        }

        rutina.setCreatedAt(rs.getTimestamp("createdAt"));
        rutina.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return rutina;
    }


}
