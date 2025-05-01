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
    /**
     * Consulta SQL para insertar una nueva rutina
     */
    private static final String SQL_INSERT_By_Client =
            "INSERT INTO Rutina (nombre, descripcion, idCliente, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    private static final String SQL_INSERT_By_Employee =
            "INSERT INTO Rutina (nombre, descripcion, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";


    /**
     * Consulta SQL para buscar una rutina por su ID
     */
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Rutina WHERE idRutina = ?";

    /**
     * Consulta SQL para obtener todas las rutinas
     */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Rutina";

    /**
     * Consulta SQL para actualizar los datos de una rutina
     */
    private static final String SQL_UPDATE =
            "UPDATE Rutina SET nombre = ?, descripcion = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRutina = ?";

    /**
     * Consulta SQL para eliminar una rutina
     */
    private static final String SQL_DELETE =
            "DELETE FROM Rutina WHERE idRutina = ?";

    /**
     * Consulta SQL para obtener las rutinas creadas por un empleado
     */
    private static final String SQL_GET_BY_CREATOR =
            "SELECT * FROM Rutina WHERE idEmpleado = ?";

    /**
     * Inserta una nueva rutina en la base de datos por un ususario cliente
     *
     * @param rutina Objeto Rutina con los datos de la rutina a insertar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Rutina insertRutinaByClient(Rutina rutina) {
        if (rutina != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_By_Client)) {

                stmt.setString(1, rutina.getNombre());
                stmt.setString(2, rutina.getDescripcion());
                stmt.setInt(3, rutina.getCreadorCliente().getId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            rutina = null;
        }
        return rutina;
    }


    /**
     * Inserta una nueva rutina en la base de datos por un ususario cliente
     *
     * @param rutina Objeto Rutina con los datos de la rutina a insertar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Rutina insertRutinaByEmployee(Rutina rutina) {
        if (rutina != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_By_Employee)) {

                stmt.setString(1, rutina.getNombre());
                stmt.setString(2, rutina.getDescripcion());
                stmt.setInt(3, rutina.getCreadorEmpleado().getId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            rutina = null;
        }
        return rutina;
    }

    /**
     * Busca una rutina por su ID
     *
     * @param idRutina ID de la rutina a buscar
     * @return Objeto Rutina si se encuentra, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Rutina getById(int idRutina) {
        Rutina rutina = null;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_ID)) {

            stmt.setInt(1, idRutina);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rutina = mapearRutina(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rutina;
    }


    /**
     * Busca una rutina por su ID
     *
     * @param idRutina ID de la rutina a buscar
     * @return Objeto Rutina si se encuentra, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Rutina getByIdEager(int idRutina) {
        Rutina rutina = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idRutina);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rutina = mapearRutina(rs);
            }

            if (rutina != null) {
                rutina.setClientesAsignados(ClienteRutinaDAO.findByRutineEager(idRutina));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rutina;
    }

    /**
     * Obtiene todas las rutinas de la base de datos
     *
     * @return Lista de todas las rutinas
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static List<Rutina> getAll() {
        List<Rutina> rutinas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rutinas.add(mapearRutina(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public static List<Rutina> getByCreator(int idEmpleado) {
        List<Rutina> rutinas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CREATOR)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearRutina(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rutinas;
    }

    /**
     * Actualiza los datos de una rutina en la base de datos
     *
     * @param rutina Objeto Rutina con los datos actualizados
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static boolean updateRutina(Rutina rutina) {
        boolean updated = false;
        if (rutina != null && getById(rutina.getIdRutina()) != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

                stmt.setString(1, rutina.getNombre());
                stmt.setString(2, rutina.getDescripcion());
                stmt.setInt(3, rutina.getCreadorCliente().getId());
                stmt.setInt(4, rutina.getCreadorEmpleado().getId());
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stmt.setInt(6, rutina.getIdRutina());

                int filas = stmt.executeUpdate();
                updated = filas > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina una rutina de la base de datos
     *
     * @param rutina rutina a eliminar
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static boolean deleteRutina(Rutina rutina) {
        boolean deleted = false;
        if (rutina != null && getById(rutina.getIdRutina()) != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

                stmt.setInt(1, rutina.getIdRutina());
                stmt.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
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
            rutina.setCreadorCliente(cliente);
        }

        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            UsuarioEmpleado empleado = new UsuarioEmpleado();
            empleado.setId(idEmpleado);
            rutina.setCreadorEmpleado(empleado);
        }

        rutina.setCreatedAt(rs.getTimestamp("createdAt"));
        rutina.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return rutina;
    }


}
