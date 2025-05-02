package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las revisiones físicas de los clientes en la base de datos.
 * Proporciona constantes SQL para insertar, buscar, actualizar y eliminar revisiones.
 */
public class RevisionDAO {
    /** Consulta SQL para insertar una nueva revisión física */
    private static final String SQL_INSERT =
            "INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, imagen, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una revisión por su ID */
    private static final String SQL_GET_BY_ID =
            "SELECT * FROM Revision WHERE idRevision = ?";

    private static final String SQL_GET_BY_EMPLEADO =
            "SELECT * FROM Revision WHERE idEmpleado = ?";

    /** Consulta SQL para obtener todas las revisiones */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Revision";

    /** Consulta SQL para actualizar los datos de una revisión */
    private static final String SQL_UPDATE =
            "UPDATE Revision SET fecha = ?, peso = ?, grasa = ?, musculo = ?, mPecho = ?, mCintura = ?, mCadera = ?, observaciones = ?, imagen = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRevision = ?";

    /** Consulta SQL para eliminar una revisión */
    private static final String SQL_DELETE =
            "DELETE FROM Revision WHERE idRevision = ?";

    private static Revision mapearRevision(ResultSet rs) throws SQLException {
        Revision revision = new Revision();
        revision.setIdRevision(rs.getInt("idRevision"));
        revision.setFecha(rs.getObject("fecha", LocalDate.class));
        revision.setPeso(rs.getDouble("peso"));
        revision.setGrasa(rs.getDouble("grasa"));
        revision.setMusculo(rs.getDouble("musculo"));
        revision.setmPecho(rs.getDouble("mPecho"));
        revision.setmCintura(rs.getDouble("mCintura"));
        revision.setmCadera(rs.getDouble("mCadera"));
        revision.setObservaciones(rs.getString("observaciones"));
        revision.setImagen(rs.getString("imagen"));


        int idCliente = rs.getInt("idCliente");
        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(idCliente);
        revision.setCliente(cliente);

        int idEmpleado = rs.getInt("idEmpleado");
        UsuarioEmpleado empleado = new UsuarioEmpleado();
        empleado.setId(idEmpleado);
        revision.setEmpleado(empleado);

        revision.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
        revision.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));

        return revision;
    }
    public static List<Revision> getAll() {

        List<Revision> revisiones = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                Revision revision = mapearRevision(rs);
                revisiones.add(revision);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revisiones;
    }

    public static Revision getById(int idRevision) {
        Revision revision = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_GET_BY_ID);
            pstmt.setInt(1, idRevision);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = mapearRevision(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revision;
    }

    public static List<Revision> getByCreator(int idEmpleado) {
        List<Revision> revisiones = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_EMPLEADO)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revisiones.add(mapearRevision(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revisiones;
    }

    public static Revision insertTarifa(Revision revision) {
        if (revision != null) {

            try (PreparedStatement stmt = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {

                stmt.setObject(1, revision.getFecha());
                stmt.setDouble(2, revision.getPeso());
                stmt.setDouble(3, revision.getGrasa());
                stmt.setDouble(4, revision.getMusculo());
                stmt.setDouble(5, revision.getmPecho());
                stmt.setDouble(6, revision.getmCintura());
                stmt.setDouble(7, revision.getmCadera());
                stmt.setString(8, revision.getObservaciones());
                stmt.setString(9, revision.getImagen());
                stmt.setInt(10, revision.getCliente().getId());
                stmt.setInt(11, revision.getEmpleado().getId());
                stmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(13, new Timestamp(System.currentTimeMillis()));

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            revision = null;
        }
        return revision;
    }

    public static void updateTarifa(Revision revision) {
        if (revision != null && getById(revision.getIdRevision())!=null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

                stmt.setObject(1, revision.getFecha());
                stmt.setDouble(2, revision.getPeso());
                stmt.setDouble(3, revision.getGrasa());
                stmt.setDouble(4, revision.getMusculo());
                stmt.setDouble(5, revision.getmPecho());
                stmt.setDouble(6, revision.getmCintura());
                stmt.setDouble(7, revision.getmCadera());
                stmt.setString(8, revision.getObservaciones());
                stmt.setString(9, revision.getImagen());
                stmt.setInt(10, revision.getCliente().getId());
                stmt.setInt(11, revision.getEmpleado().getId());
                stmt.setTimestamp(13, new Timestamp(System.currentTimeMillis()));


                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean deleteTarifa(Revision revision) {
        boolean deleted = false;
        if (revision != null && getById(revision.getIdRevision())!=null) {
            try(PreparedStatement pst= ConnectionDB.getConnection().prepareStatement(SQL_DELETE)){
                pst.setInt(1,revision.getIdRevision());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

}
