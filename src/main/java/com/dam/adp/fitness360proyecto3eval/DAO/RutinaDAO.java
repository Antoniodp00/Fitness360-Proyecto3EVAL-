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
public class RutinaDAO implements GenericDAO<Rutina> {
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

    private static final String SQL_FIND_BY_NAME =
            "SELECT * FROM Rutina WHERE nombre = ?";

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
     * Consulta SQL para actualizar los datos de una rutina creada por un cliente
     */
    private static final String SQL_UPDATE_BY_CLIENT =
            "UPDATE Rutina SET nombre = ?, descripcion = ?, updatedAt = ? WHERE idRutina = ? AND idCliente IS NOT NULL";

    /**
     * Consulta SQL para actualizar los datos de una rutina creada por un empleado
     */
    private static final String SQL_UPDATE_BY_EMPLOYEE =
            "UPDATE Rutina SET nombre = ?, descripcion = ?, updatedAt = ? WHERE idRutina = ? AND idEmpleado IS NOT NULL";

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
    public Rutina insertRutinaByClient(Rutina rutina) {
        if (rutina != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_By_Client, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, rutina.getNombre());
                stmt.setString(2, rutina.getDescripcion());
                stmt.setInt(3, rutina.getCreadorCliente().getId());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rutina.setIdRutina(generatedKeys.getInt(1)); // ✅ asigna el ID generado
                    } else {
                        throw new SQLException("No se generó ID para la rutina.");
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
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
    public Rutina insertRutinaByEmployee(Rutina rutina) {
        if (rutina != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_By_Employee, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, rutina.getNombre());
                stmt.setString(2, rutina.getDescripcion());
                stmt.setInt(3, rutina.getCreadorEmpleado().getId());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rutina.setIdRutina(generatedKeys.getInt(1)); // ✅ asignar ID generado
                    } else {
                        throw new SQLException("No se generó ID para la rutina.");
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
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
    public Rutina getById(int idRutina) {
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


    public Rutina getByName(String nombreRutina) {
        Rutina rutina = null;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_NAME)) {

            stmt.setString(1, nombreRutina);

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

    @Override
    public Rutina insert(Rutina entity) {
        return null;
    }


    /**
     * Busca una rutina por su ID
     *
     * @param idRutina ID de la rutina a buscar
     * @return Objeto Rutina si se encuentra, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public Rutina getByIdEager(int idRutina) {
        ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();
        Rutina rutina = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idRutina);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rutina = mapearRutina(rs);
            }

            if (rutina != null) {
                rutina.setClientesAsignados(clienteRutinaDAO.findByRutineEager(idRutina));
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
    public List<Rutina> getAll() {
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
    public List<Rutina> getByCreator(int idEmpleado) {
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
    public boolean update(Rutina rutina) {
        boolean updated = false;
        if (rutina != null && getById(rutina.getIdRutina()) != null) {
            try (Connection conn = ConnectionDB.getConnection()) {
                PreparedStatement stmt;

                // Determinar qué tipo de actualización realizar basado en el creador de la rutina
                if (rutina.getCreadorCliente() != null && rutina.getCreadorEmpleado() == null) {
                    // Rutina creada por un cliente
                    stmt = conn.prepareStatement(SQL_UPDATE_BY_CLIENT);
                    stmt.setString(1, rutina.getNombre());
                    stmt.setString(2, rutina.getDescripcion());
                    stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    stmt.setInt(4, rutina.getIdRutina());
                } else if (rutina.getCreadorEmpleado() != null && rutina.getCreadorCliente() == null) {
                    // Rutina creada por un empleado
                    stmt = conn.prepareStatement(SQL_UPDATE_BY_EMPLOYEE);
                    stmt.setString(1, rutina.getNombre());
                    stmt.setString(2, rutina.getDescripcion());
                    stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    stmt.setInt(4, rutina.getIdRutina());
                } else {
                    // Actualización completa
                    stmt = conn.prepareStatement(SQL_UPDATE);
                    stmt.setString(1, rutina.getNombre());
                    stmt.setString(2, rutina.getDescripcion());

                    // Manejar caso donde creadorCliente es null
                    if (rutina.getCreadorCliente() != null) {
                        stmt.setInt(3, rutina.getCreadorCliente().getId());
                    } else {
                        stmt.setNull(3, Types.INTEGER);
                    }

                    // Manejar caso donde creadorEmpleado es null
                    if (rutina.getCreadorEmpleado() != null) {
                        stmt.setInt(4, rutina.getCreadorEmpleado().getId());
                    } else {
                        stmt.setNull(4, Types.INTEGER);
                    }

                    stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    stmt.setInt(6, rutina.getIdRutina());
                }

                int filas = stmt.executeUpdate();
                updated = filas > 0;
                stmt.close();
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
    public boolean delete(Rutina rutina) {
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
    private Rutina mapearRutina(ResultSet rs) throws SQLException {
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
