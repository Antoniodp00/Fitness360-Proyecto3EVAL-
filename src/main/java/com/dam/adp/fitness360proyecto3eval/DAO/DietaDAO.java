package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las dietas en la base de datos.
 * Proporciona métodos para insertar, buscar, actualizar y eliminar dietas.
 */
public class DietaDAO {
    /**
     * Consulta SQL para insertar una nueva dieta
     */
    private static final String SQL_INSERT = "INSERT INTO Dieta (nombre, descripcion, archivo, idEmpleado, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Consulta SQL para buscar una dieta por su ID
     */
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Dieta WHERE idDieta = ?";

    /**
     * Consulta SQL para buscar una dieta por su nombre
     */
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM Dieta WHERE nombre = ?";

    /**
     * Consulta SQL para obtener todas las dietas
     */
    private static final String SQL_GET_ALL = "SELECT * FROM Dieta";

    private static final String SQL_GET_BY_CREATOR =
            "SELECT * FROM Dieta WHERE idEmpleado = ?";


    /**
     * Consulta SQL para actualizar los datos de una dieta
     */
    private static final String SQL_UPDATE = "UPDATE Dieta SET nombre = ?, descripcion = ?, archivo = ?, idEmpleado = ?, updatedAt = ? WHERE idDieta = ?";

    /**
     * Consulta SQL para eliminar una dieta
     */
    private static final String SQL_DELETE = "DELETE FROM Dieta WHERE idDieta = ?";

    /**
     * Método auxiliar para mapear un ResultSet a un objeto Dieta
     *
     * @param rs ResultSet con los datos de la dieta
     * @return Objeto Dieta con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private static Dieta mapearDieta(ResultSet rs) throws SQLException {
        Dieta dieta = new Dieta();

        dieta.setIdDieta(rs.getInt("idDieta"));
        dieta.setNombre(rs.getString("nombre"));
        dieta.setDescripcion(rs.getString("descripcion"));
        dieta.setArchivo(rs.getString("archivo"));
        dieta.setCreatedAt(rs.getTimestamp("createdAt"));
        dieta.setUpdatedAt(rs.getTimestamp("updatedAt"));

        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            UsuarioEmpleado empleado = new UsuarioEmpleado();
            empleado.setId(idEmpleado);
            dieta.setCreador(empleado);
        }

        dieta.setClientesAsignados(new ArrayList<>());

        return dieta;
    }


    /**
     * Obtiene todas las dietas de la base de datos
     *
     * @return Lista de todas las dietas
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static List<Dieta> getAll() {
        List<Dieta> dietas = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                Dieta dieta = mapearDieta(rs);
                dietas.add(dieta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dietas;
    }

    /**
     * Busca una dieta por su ID
     *
     * @param idDieta ID de la dieta a buscar
     * @return Objeto Dieta si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static Dieta getById(int idDieta) {
        Dieta dieta = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID);
            pstmt.setInt(1, idDieta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dieta = mapearDieta(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dieta;
    }


    /**
     * Busca una dieta por su nombre
     *
     * @param nombreDieta nombre de la dieta a buscar
     * @return Objeto Dieta si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static Dieta getByName(String nombreDieta) {
        Dieta dieta = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_NAME)) {

            pstmt.setString(1, nombreDieta);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dieta = mapearDieta(rs);
                    if (dieta.getCreador().getId() != 0) {
                        UsuarioEmpleado creador = UsuarioEmpleadoDAO.findById(dieta.getCreador().getId());
                        dieta.setCreador(creador);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dieta;
    }

    /**
     * Obtiene todas las dietas creadas por un empleado específico
     *
     * @param idEmpleado ID del empleado creador de las dietas
     * @return Lista de dietas creadas por el empleado especificado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static List<Dieta> getByCreator(int idEmpleado) {
        List<Dieta> dietas = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CREATOR)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dietas.add(mapearDieta(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dietas;
    }


    /**
     * Inserta una nueva dieta en la base de datos
     *
     * @param dieta Objeto Dieta con los datos de la dieta a insertar
     * @return El objeto Dieta insertado con su ID generado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static Dieta insertDieta(Dieta dieta) {
        if (dieta != null) {

            try (PreparedStatement stmt = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {

                stmt.setString(1, dieta.getNombre());
                stmt.setString(2, dieta.getDescripcion());
                stmt.setString(3, dieta.getArchivo());
                stmt.setInt(4, dieta.getCreador().getId());
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            dieta = null;
        }
        return dieta;
    }

    /**
     * Actualiza los datos de una dieta existente en la base de datos
     *
     * @param dieta Objeto Dieta con los nuevos datos a actualizar
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static void updateDieta(Dieta dieta) {
        if (dieta != null && getById(dieta.getIdDieta())!=null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

                stmt.setString(1, dieta.getNombre());
                stmt.setString(2, dieta.getDescripcion());
                stmt.setString(3, dieta.getArchivo());
                stmt.setInt(4, dieta.getCreador().getId());
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                stmt.setInt(6, dieta.getIdDieta());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Elimina una dieta de la base de datos
     *
     * @param dieta Objeto Dieta a eliminar
     * @return true si la dieta fue eliminada correctamente, false en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static boolean deleteDieta(Dieta dieta) {
        boolean deleted = false;
        if (dieta != null && getById(dieta.getIdDieta())!=null) {
            try(PreparedStatement pst= ConnectionDB.getConnection().prepareStatement(SQL_DELETE)){
                pst.setInt(1,dieta.getIdDieta());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

}
