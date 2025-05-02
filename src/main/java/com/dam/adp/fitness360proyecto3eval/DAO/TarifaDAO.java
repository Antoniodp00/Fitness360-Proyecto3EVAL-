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
public class TarifaDAO {
    /** Consulta SQL para insertar una nueva tarifa */
    private static final String SQL_INSERT =
            "INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una tarifa por su ID */
    private static final String SQL_GET_BY_ID =
            "SELECT * FROM Tarifa WHERE idTarifa = ?";

    private static final String SQL_GET_BY_CREATOR =
            "SELECT * FROM Tarifa WHERE idEmpleado = ?";

    /** Consulta SQL para obtener todas las tarifas */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Tarifa";

    /** Consulta SQL para actualizar los datos de una tarifa */
    private static final String SQL_UPDATE =
            "UPDATE Tarifa SET nombre = ?, precio = ?, descripcion = ?, periodo = ?, idEmpleado = ?, updatedAt = ? WHERE idTarifa = ?";

    /** Consulta SQL para eliminar una tarifa */
    private static final String SQL_DELETE =
            "DELETE FROM Tarifa WHERE idTarifa = ?";


    private static Tarifa mapearTarifa(ResultSet rs) throws SQLException {
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

    public static List<Tarifa> getAll() {

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

    public static Tarifa getById(int idTarifa) {
        Tarifa tarifa = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_GET_BY_ID);
            pstmt.setInt(1, idTarifa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tarifa = mapearTarifa(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tarifa;
    }

    public static List<Tarifa> getByCreator(int idEmpleado) {
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

    public static Tarifa insertTarifa(Tarifa tarifa) {
        if (tarifa != null) {

            try (PreparedStatement stmt = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {

                stmt.setString(1, tarifa.getNombre());
                stmt.setDouble(2, tarifa.getPrecio());
                stmt.setString(3, tarifa.getDescripcion());
                stmt.setString(4, tarifa.getPeriodo().name());
                stmt.setInt(5, tarifa.getCreador().getId());
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));


                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            tarifa = null;
        }
        return tarifa;
    }

    public static void updateTarifa(Tarifa tarifa) {
        if (tarifa != null && getById(tarifa.getIdTarifa())!=null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

                stmt.setString(1, tarifa.getNombre());
                stmt.setDouble(2, tarifa.getPrecio());
                stmt.setString(3, tarifa.getDescripcion());
                stmt.setString(4, tarifa.getPeriodo().name());
                stmt.setInt(5, tarifa.getCreador().getId());
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean deleteTarifa(Tarifa tarifa) {
        boolean deleted = false;
        if (tarifa != null && getById(tarifa.getIdTarifa())!=null) {
            try(PreparedStatement pst= ConnectionDB.getConnection().prepareStatement(SQL_DELETE)){
                pst.setInt(1,tarifa.getIdTarifa());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }


}
