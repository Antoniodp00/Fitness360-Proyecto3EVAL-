package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las tarifas en la base de datos.
 * Proporciona constantes SQL para insertar, buscar, actualizar y eliminar tarifas.
 */
public class TarifaDAO implements GenericDAO<Tarifa> {
    /**
     * Consulta SQL para insertar una nueva tarifa
     */
    private static final String SQL_INSERT =
            "INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /**
     * Consulta SQL para buscar una tarifa por su ID
     */
    private static final String SQL_GET_BY_ID =
            "SELECT * FROM Tarifa WHERE idTarifa = ?";

    /**
     * Consulta SQL para obtener tarifas creadas por un empleado específico
     */
    private static final String SQL_GET_BY_CREATOR =
            "SELECT t.*, e.nombre AS nombreEmpleado, e.apellidos AS apellidosEmpleado " +
                    "FROM Tarifa t " +
                    "JOIN Empleado e ON t.idEmpleado = e.idEmpleado " +
                    "WHERE t.idEmpleado = ?";


    /**
     * Consulta SQL para obtener todas las tarifas
     */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Tarifa";

    /**
     * Consulta SQL para actualizar los datos de una tarifa
     */
    private static final String SQL_UPDATE =
            "UPDATE Tarifa SET nombre = ?, precio = ?, descripcion = ?, periodo = ?, idEmpleado = ?, updatedAt = ? WHERE idTarifa = ?";

    /**
     * Consulta SQL para eliminar una tarifa
     */
    private static final String SQL_DELETE =
            "DELETE FROM Tarifa WHERE idTarifa = ?";


    /**
     * Método auxiliar para mapear un ResultSet a un objeto Tarifa
     *
     * @param rs ResultSet con los datos de la tarifa
     * @return Objeto Tarifa con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private Tarifa mapearTarifa(ResultSet rs) throws SQLException {
        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(rs.getInt("idTarifa"));
        tarifa.setNombre(rs.getString("nombre"));
        tarifa.setPrecio(rs.getDouble("precio"));
        tarifa.setDescripcion(rs.getString("descripcion"));
        String periodoStr = rs.getString("periodo");
        if (periodoStr != null) {
            tarifa.setPeriodo(Periodo.valueOf(periodoStr));
        }
        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            UsuarioEmpleado empleado = new UsuarioEmpleado();
            empleado.setId(idEmpleado);
            tarifa.setCreador(empleado);
        }
        tarifa.setCreatedAt(rs.getTimestamp("createdAt"));
        tarifa.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return tarifa;
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto Tarifa
     *
     * @param rs ResultSet con los datos de la tarifa
     * @return Objeto Tarifa con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private Tarifa mapearTarifaEager(ResultSet rs) throws SQLException {
        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(rs.getInt("idTarifa"));
        tarifa.setNombre(rs.getString("nombre"));
        tarifa.setPrecio(rs.getDouble("precio"));
        tarifa.setDescripcion(rs.getString("descripcion"));
        String periodoStr = rs.getString("periodo");
        if (periodoStr != null) {
            tarifa.setPeriodo(Periodo.valueOf(periodoStr));
        }
        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            UsuarioEmpleado empleado = new UsuarioEmpleado();
            empleado.setId(idEmpleado);
            empleado.setNombre(rs.getString("nombreEmpleado"));
            empleado.setApellidos(rs.getString("apellidosEmpleado"));
            tarifa.setCreador(empleado);
        }
        tarifa.setCreatedAt(rs.getTimestamp("createdAt"));
        tarifa.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return tarifa;
    }

    /**
     * Obtiene todas las tarifas de la base de datos
     *
     * @return Lista de todas las tarifas
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    @Override
    public List<Tarifa> getAll() {
        List<Tarifa> tarifas = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                Tarifa tarifa = mapearTarifa(rs);
                tarifas.add(tarifa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tarifas;
    }

    /**
     * Busca una tarifa por su ID
     *
     * @param id ID de la tarifa a buscar
     * @return Objeto Tarifa si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    @Override
    public Tarifa getById(int id) {
        Tarifa tarifa = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_GET_BY_ID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tarifa = mapearTarifa(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tarifa;
    }

    /**
     * Obtiene todas las tarifas creadas por un empleado específico
     *
     * @param idEmpleado ID del empleado creador de las tarifas
     * @return Lista de tarifas creadas por el empleado especificado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public List<Tarifa> getByCreator(int idEmpleado) {
        List<Tarifa> tarifas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CREATOR)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tarifas.add(mapearTarifa(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tarifas;
    }

    /**
     * Obtiene todas las tarifas creadas por un empleado específico verison eager
     *
     * @param idEmpleado ID del empleado creador de las tarifas
     * @return Lista de tarifas creadas por el empleado especificado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public List<Tarifa> getByCreatorEager(int idEmpleado) {
        List<Tarifa> tarifas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CREATOR)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tarifas.add(mapearTarifa(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tarifas;
    }

    /**
     * Inserta una nueva tarifa en la base de datos
     *
     * @param entity Objeto Tarifa con los datos de la tarifa a insertar
     * @return El objeto Tarifa insertado con su ID generado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    @Override
    public Tarifa insert(Tarifa entity) {
        if (entity != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, entity.getNombre());
                stmt.setDouble(2, entity.getPrecio());
                stmt.setString(3, entity.getDescripcion());
                stmt.setString(4, entity.getPeriodo().name());
                stmt.setInt(5, entity.getCreador().getId());

                stmt.executeUpdate();

                // Obtener el ID generado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setIdTarifa(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            entity = null;
        }
        return entity;
    }

    /**
     * Actualiza los datos de una tarifa existente en la base de datos
     *
     * @param entity Objeto Tarifa con los nuevos datos a actualizar
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    @Override
    public boolean update(Tarifa entity) {
        boolean updated = false;
        if (entity != null && getById(entity.getIdTarifa()) != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

                stmt.setString(1, entity.getNombre());
                stmt.setDouble(2, entity.getPrecio());
                stmt.setString(3, entity.getDescripcion());
                stmt.setString(4, entity.getPeriodo().name());
                stmt.setInt(5, entity.getCreador().getId());
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stmt.setInt(7, entity.getIdTarifa());

                stmt.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Elimina una tarifa de la base de datos
     *
     * @param entity Objeto Tarifa a eliminar
     * @return true si la tarifa fue eliminada correctamente, false en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    @Override
    public boolean delete(Tarifa entity) {
        boolean deleted = false;
        if (entity != null && getById(entity.getIdTarifa()) != null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_DELETE)) {
                pst.setInt(1, entity.getIdTarifa());
                pst.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
