package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las revisiones físicas de los clientes en la base de datos.
 * Proporciona constantes SQL para insertar, buscar, actualizar y eliminar revisiones.
 */
public class RevisionDAO implements GenericDAO<Revision> {
    /** Consulta SQL para insertar una nueva revisión física */
    private static final String SQL_INSERT =
            "INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, imagen, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una revisión por su ID */
    private static final String SQL_GET_BY_ID =
            "SELECT * FROM Revision WHERE idRevision = ?";

    /** Consulta SQL para buscar revisiones por id empleado */
    private static final String SQL_GET_BY_IDEMPLEADO =
            "SELECT r.*, c.*, e.* " +
                    "FROM Revision r " +
                    "JOIN Cliente c ON r.idCliente = c.idCliente " +
                    "JOIN Empleado e ON r.idEmpleado = e.idEmpleado " +
                    "WHERE r.idEmpleado = ?";


    /** Consulta SQL para buscar revisiones por id empleado */
    private static final String SQL_GET_BY_USERNAME_EMPLEADO_DATE_USERNAME_CLIENT =
            "SELECT r.*, c.*, e.* " +
                    "FROM Revision r " +
                    "JOIN Cliente c ON r.idCliente = c.idCliente " +
                    "JOIN Empleado e ON r.idEmpleado = e.idEmpleado " +
                    "WHERE r.nombreUsuario = ? AND r.fecha = ? AND c.nombreUsuario = ?";


    /** Consulta SQL para buscar revisiones por cliente */
    private static final String SQL_GET_BY_CLIENTE =
            "SELECT r.*, c.*, e.* " +
                    "FROM Revision r " +
                    "JOIN Cliente c ON r.idCliente = c.idCliente " +
                    "JOIN Empleado e ON r.idEmpleado = e.idEmpleado " +
                    "WHERE r.idCliente = ?";


    /** Consulta SQL para obtener todas las revisiones */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Revision";

    /** Consulta SQL para actualizar los datos de una revisión */
    private static final String SQL_UPDATE =
            "UPDATE Revision SET fecha = ?, peso = ?, grasa = ?, musculo = ?, mPecho = ?, mCintura = ?, mCadera = ?, observaciones = ?, imagen = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRevision = ?";

    /** Consulta SQL para eliminar una revisión */
    private static final String SQL_DELETE =
            "DELETE FROM Revision WHERE idRevision = ?";


    /**
     * Mapea un ResultSet a un objeto Revision
     * 
     * @param rs ResultSet con los datos a mapear
     * @return Objeto Revision con todos los datos
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private  Revision mapearRevision(ResultSet rs) throws SQLException {
        Revision revision = new Revision();
        revision.setIdRevision(rs.getInt("idRevision"));
        revision.setFecha(rs.getObject("fecha", Date.class));
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

        revision.setCreatedAt(rs.getObject("createdAt", Date.class));
        revision.setUpdatedAt(rs.getObject("updatedAt", Date.class));

        return revision;
    }

    /**
     * Mapea un ResultSet a un objeto Revision version Eager
     *
     * @param rs ResultSet con los datos a mapear
     * @return Objeto Revision con todos los datos
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private  Revision mapearRevisionEager(ResultSet rs) throws SQLException {

        Revision revision = new Revision();
        revision.setIdRevision(rs.getInt("idRevision"));
        revision.setFecha(rs.getObject("fecha", Date.class));
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
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidos(rs.getString("apellidos"));
        revision.setCliente(cliente);

        int idEmpleado = rs.getInt("idEmpleado");
        UsuarioEmpleado empleado = new UsuarioEmpleado();
        empleado.setId(idEmpleado);
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidos(rs.getString("apellidos"));
        revision.setEmpleado(empleado);

        revision.setCreatedAt(rs.getObject("createdAt", Date.class));
        revision.setUpdatedAt(rs.getObject("updatedAt", Date.class));

        return revision;
    }
    /**
     * Obtiene todas las revisiones de la base de datos
     * 
     * @return Lista con todas las revisiones
     */
    public List<Revision> getAll() {
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

    /**
     * Busca una revisión por su ID
     * 
     * @param idRevision ID de la revisión a buscar
     * @return Objeto Revision si se encuentra, null en caso contrario
     */
    public  Revision getById(int idRevision) {
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

    /**
     * Obtiene todas las revisiones realizadas por un empleado específico
     * 
     * @param idEmpleado ID del empleado creador
     * @return Lista de revisiones realizadas por el empleado
     */
    public  List<Revision> getByCreator(int idEmpleado) {
        List<Revision> revisiones = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_IDEMPLEADO)) {

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

    /**
     * Obtiene todas las revisiones realizadas por un empleado específico
     *
     * @param nombreUsuario ID del empleado creador
     * @return Lista de revisiones realizadas por el empleado
     */
    public  Revision getByUserNameCreator(String nombreUsuario, Date fecha, String nombreUsuarioCliente) {
        Revision revision = new Revision();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_USERNAME_EMPLEADO_DATE_USERNAME_CLIENT)) {


            stmt.setString(1, nombreUsuario);
            stmt.setDate(2, (java.sql.Date) fecha);
            stmt.setString(3, nombreUsuarioCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                   revision = mapearRevision(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revision;
    }

    /**
     * Obtiene todas las revisiones realizadas por un empleado específico version Eager
     *
     * @param idEmpleado ID del empleado creador
     * @return Lista de revisiones realizadas por el empleado
     */
    public  List<Revision> getByCreatorEager(int idEmpleado) {
        List<Revision> revisiones = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_IDEMPLEADO)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revisiones.add(mapearRevisionEager(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revisiones;
    }

    /**
     * Obtiene todas las revisiones de un cliente específico
     * 
     * @param idCliente ID del cliente
     * @return Lista de revisiones del cliente
     */
    public  List<Revision> getByClient(int idCliente) {
        List<Revision> revisiones = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CLIENTE)) {

            stmt.setInt(1, idCliente);

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

    /**
     * Obtiene todas las revisiones de un cliente específico verison Eager
     *
     * @param idCliente ID del cliente
     * @return Lista de revisiones del cliente
     */
    public  List<Revision> getByClientEager(int idCliente) {
        List<Revision> revisiones = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_CLIENTE)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revisiones.add(mapearRevisionEager(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revisiones;
    }

    /**
     * Inserta una nueva revisión en la base de datos
     * 
     * @param revision Objeto Revision con los datos a insertar
     * @return El objeto Revision insertado, o null si hubo un error
     */
    public  Revision insert(Revision revision) {
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

    /**
     * Actualiza los datos de una revisión existente en la base de datos
     * 
     * @param revision Objeto Revision con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public  boolean update(Revision revision) {
        boolean actualizado = false;
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
                stmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                stmt.setInt(13, revision.getIdRevision());

                int filas = stmt.executeUpdate();
                actualizado = filas > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return actualizado;
    }

    /**
     * Elimina una revisión de la base de datos
     * 
     * @param revision Objeto Revision a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public  boolean delete(Revision revision) {
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
