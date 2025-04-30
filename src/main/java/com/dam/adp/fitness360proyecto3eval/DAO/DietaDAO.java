package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.ClienteDieta;
import com.dam.adp.fitness360proyecto3eval.model.Dieta;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioEmpleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las dietas en la base de datos.
 * Proporciona métodos para insertar, buscar, actualizar y eliminar dietas.
 */
public class DietaDAO {
    /** Consulta SQL para insertar una nueva dieta */
    private static final String SQL_INSERT = "INSERT INTO Dieta (nombre, descripcion, archivo, idEmpleado, createdAt, updatedAt) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una dieta por su ID */
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Dieta WHERE idDieta = ?";

    /** Consulta SQL para buscar una dieta por su nombre */
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM Dieta WHERE nombre = ?";

    /** Consulta SQL para obtener todas las dietas */
    private static final String SQL_GET_ALL = "SELECT * FROM Dieta";

    /** Consulta SQL para actualizar los datos de una dieta */
    private static final String SQL_UPDATE = "UPDATE Dieta SET nombre = ?, descripcion = ?, archivo = ?, idEmpleado = ?, updatedAt = ? WHERE idDieta = ?";

    /** Consulta SQL para eliminar una dieta */
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
        int idEmpleado = rs.getInt("idEmpleado");
        if (idEmpleado != 0) {
            UsuarioEmpleado creador = UsuarioEmpleadoDAO.findById(idEmpleado);
            dieta.setCreador(creador);
        }
       dieta.setClientesAsignados(new ArrayList<ClienteDieta>());
        dieta.setCreatedAt(rs.getTimestamp("createdAt"));
        dieta.setUpdatedAt(rs.getTimestamp("updatedAt"));

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
        ConnectionDB.closeConnection();
        return dietas;
    }

    /**
     * Busca una dieta por su ID
     * 
     * @param idDieta ID de la dieta a buscar
     * @return Objeto Dieta si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static Dieta findById(int idDieta) {
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
     * Inserta una nueva dieta en la base de datos
     * 
     * @param dieta Objeto Dieta con los datos de la dieta a insertar
     * @return El objeto Dieta insertado con su ID generado
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public static Dieta insert(Dieta dieta) {
        String SQL_INSERT = "INSERT INTO Dieta (nombre, descripcion, archivo, idEmpleado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            // Establece los valores para los parámetros de la consulta
            pst.setString(1, dieta.getNombre());
            pst.setString(2, dieta.getDescripcion());  // Suponiendo que no sea nulo
            pst.setString(3, dieta.getArchivo());  // Suponiendo que no sea nulo
            if (dieta.getCreador() != null) {
                pst.setInt(4, dieta.getCreador().getId());  // Asumiendo que `getCreador()` retorna un `UsuarioEmpleado` válido
            } else {
                pst.setNull(4, Types.INTEGER);  // Si el creador es nulo, se establece NULL
            }

            // Ejecuta la inserción
            int affectedRows = pst.executeUpdate();

            // Si se insertaron filas, recupera el ID generado para la dieta
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dieta.setIdDieta(generatedKeys.getInt(1));  // Asigna el ID generado a la dieta
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar la dieta", e);  // Mensaje de error más claro
        }
        return dieta;
    }

}
