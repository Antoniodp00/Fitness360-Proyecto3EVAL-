package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con los usuarios empleados en la base de datos.
 * Proporciona métodos para obtener, buscar, insertar, actualizar y desactivar usuarios empleados.
 */
public class UsuarioEmpleadoDAO implements GenericDAO<UsuarioEmpleado> {

    /** Consulta SQL para insertar un nuevo empleado */
    private static final String SQL_INSERT = "INSERT INTO Empleado (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, descripcion, rol, especialidad, estado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /** Consulta SQL para buscar un empleado por su ID */
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Empleado WHERE idEmpleado = ?";

    /** Consulta SQL para buscar un empleado por su nombre de usuario */
    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM Empleado WHERE nombreUsuario = ?";

    /** Consulta SQL para buscar un empleado por su correo electrónico */
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM Empleado WHERE correo = ?";

    /** Consulta SQL para obtener todos los empleados */
    private static final String SQL_GET_ALL = "SELECT * FROM Empleado";

    /** Consulta SQL para actualizar los datos de un empleado */
    private static final String SQL_UPDATE = "UPDATE Empleado SET nombreUsuario = ?, nombre = ?, apellidos = ?, correo = ?, password = ?, telefono = ?, fechaNacimiento = ?, sexo = ?, descripcion = ?, rol = ?, especialidad = ?, estado = ?, updatedAt = ? WHERE idEmpleado = ?";

    /** Consulta SQL para desactivar un empleado */
    private static final String SQL_DISABLE = "UPDATE Empleado SET estado = ?, updatedAt = ? WHERE idEmpleado = ?";

    /** Consulta SQL para eliminar un empleado */
    private static final String SQL_DELETE = "DELETE FROM Empleado WHERE idEmpleado = ?";

    /** Consulta SQL para obtener las dietas creadas por un empleado */
    private static final String SQL_GET_DIETAS_CREADAS = "SELECT * FROM Dieta WHERE idCreador = ?";

    /**
     * Método auxiliar para mapear un ResultSet a un objeto UsuarioEmpleado
     * 
     * @param rs ResultSet con los datos del empleado
     * @return Objeto UsuarioEmpleado con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private UsuarioEmpleado mapearEmpleado(ResultSet rs) throws SQLException {
        UsuarioEmpleado empleado = new UsuarioEmpleado();
        empleado.setId(rs.getInt("idEmpleado"));
        empleado.setNombreUsuario(rs.getString("nombreUsuario"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidos(rs.getString("apellidos"));
        empleado.setCorreo(rs.getString("correo"));
        empleado.setPassword(rs.getString("password"));
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setFechaNacimiento(rs.getDate("fechaNacimiento"));

        String sexoStr = rs.getString("sexo");
        if (sexoStr != null) {
            empleado.setSexo(Sexo.valueOf(sexoStr));
        }

        empleado.setDescripcion(rs.getString("descripcion"));
        empleado.setRol(rs.getString("rol"));

        String especialidadStr = rs.getString("especialidad");
        if (especialidadStr != null) {
            empleado.setEspecialidad(Especialidad.valueOf(especialidadStr));
        }

        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            empleado.setEstado(Estado.valueOf(estadoStr));
        }

        empleado.setCreatedAt(rs.getTimestamp("createdAt"));
        empleado.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return empleado;
    }

    /**
     * Obtiene todos los empleados de la base de datos
     * 
     * @return Lista de todos los empleados
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public List<UsuarioEmpleado> getAll() {
        List<UsuarioEmpleado> empleados = new ArrayList<>();

        try (Connection con = ConnectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_GET_ALL)) {

            while (rs.next()) {
                UsuarioEmpleado empleado = mapearEmpleado(rs);
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empleados;
    }

    /**
     * Busca un empleado por su ID
     * 
     * @param idEmpleado ID del empleado a buscar
     * @return Objeto UsuarioEmpleado si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado getById(int idEmpleado) {

        UsuarioEmpleado empleado = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idEmpleado);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = mapearEmpleado(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    /**
     * Busca un empleado por su ID y carga sus rutinas creadas (carga ansiosa/eager loading)
     * 
     * @param idEmpleado ID del empleado a buscar
     * @return Objeto UsuarioEmpleado con sus rutinas creadas si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado findByIdEager(int idEmpleado) {
        RutinaDAO rutinaDAO = new RutinaDAO();
        UsuarioEmpleado empleado = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idEmpleado);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = mapearEmpleado(rs);
            }

            if (empleado != null) {
                empleado.setRutinasCreadas(rutinaDAO.getByCreator(idEmpleado));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }


    /**
     * Busca un empleado por su nombre de usuario
     * 
     * @param nombreUsuario Nombre de usuario del empleado a buscar
     * @return Objeto UsuarioEmpleado si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado findByUserName(String nombreUsuario) {
        RutinaDAO rutinaDAO = new RutinaDAO();
        UsuarioEmpleado empleado = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_USERNAME)) {

            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado =mapearEmpleado(rs);
            }

            if (empleado != null) {
                empleado.setRutinasCreadas(rutinaDAO.getByCreator(empleado.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    /**
     * Busca un empleado por su nombre de usuario
     *
     * @param nombreUsuario Nombre de usuario del empleado a buscar
     * @return Objeto UsuarioEmpleado si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado finByUserNameEager(String nombreUsuario) {
        UsuarioEmpleado empleado = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_USERNAME)) {

            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = mapearEmpleado(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    /**
     * Busca un empleado por su correo electrónico
     *
     * @param correo Correo electrónico del empleado a buscar
     * @return Objeto UsuarioEmpleado si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado findByEmail(String correo) {
        UsuarioEmpleado empleado = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = mapearEmpleado(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    /**
     * Inserta un nuevo empleado en la base de datos
     * 
     * @param empleado Objeto UsuarioEmpleado con los datos del empleado a insertar
     * @return El objeto UsuarioEmpleado insertado si la operación fue exitosa
     * @throws com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException Si el nombre de usuario o correo ya existe
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioEmpleado insert(UsuarioEmpleado empleado) {
        if (empleado == null) {
            return null;
        }

        // Verificar si el nombre de usuario ya existe
        UsuarioEmpleado empleadoExistente = findByUserName(empleado.getNombreUsuario());
        if (empleadoExistente != null) {
            throw new com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException("El nombre de usuario ya existe");
        }

        // Verificar si el correo ya existe
        empleadoExistente = findByEmail(empleado.getCorreo());
        if (empleadoExistente != null) {
            throw new com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException("El correo electrónico ya existe");
        }

        try(Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){

            pst.setString(1, empleado.getNombreUsuario());
            pst.setString(2, empleado.getNombre());
            pst.setString(3, empleado.getApellidos());
            pst.setString(4, empleado.getCorreo());
            pst.setString(5, empleado.getPassword());
            pst.setString(6, empleado.getTelefono());
            pst.setDate(7, empleado.getFechaNacimiento() != null ? new java.sql.Date(empleado.getFechaNacimiento().getTime()) : null);
            pst.setString(8, empleado.getSexo() != null ? empleado.getSexo().name() : null);
            pst.setString(9, empleado.getDescripcion());
            pst.setString(10, empleado.getRol());
            pst.setString(11, empleado.getEspecialidad() != null ? empleado.getEspecialidad().name() : null);
            pst.setString(12, Estado.ACTIVO.name());
            pst.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    empleado.setId(generatedKeys.getInt(1));
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empleado;
    }

    /**
     * Desactiva un empleado en la base de datos cambiando su estado a INACTIVO
     * 
     * @param id ID del empleado a desactivar
     * @return true si el empleado fue desactivado correctamente, false si el empleado no existe o hubo un error
     */
    public boolean disableUsuarioEmpleado(int id) {

        boolean disabled = false;
        if(getById(id) != null) {
            try (Connection con = ConnectionDB.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_DISABLE)) {

                pst.setString(1, Estado.INACTIVO.name());
                pst.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                pst.setInt(3, id);
                pst.executeUpdate();
                disabled = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return disabled;
    }

    /**
     * Actualiza los datos de un empleado en la base de datos
     * 
     * @param empleado Objeto UsuarioEmpleado con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(UsuarioEmpleado empleado) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, empleado.getNombreUsuario());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidos());
            stmt.setString(4, empleado.getCorreo());
            stmt.setString(5, com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil.hashPassword(empleado.getPassword()));
            stmt.setString(6, empleado.getTelefono());
            stmt.setDate(7, empleado.getFechaNacimiento() != null ? new java.sql.Date(empleado.getFechaNacimiento().getTime()) : null);
            stmt.setString(8, empleado.getSexo() != null ? empleado.getSexo().name() : null);
            stmt.setString(9, empleado.getDescripcion());
            stmt.setString(10, empleado.getRol());
            stmt.setString(11, empleado.getEspecialidad() != null ? empleado.getEspecialidad().name() : null);
            stmt.setString(12, empleado.getEstado() != null ? empleado.getEstado().name() : null);
            stmt.setTimestamp(13, new Timestamp(System.currentTimeMillis())); // updatedAt
            stmt.setInt(14, empleado.getId()); // idEmpleado

            int filas = stmt.executeUpdate();
            actualizado = filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }

    /**
     * Elimina un empleado de la base de datos
     *
     * @param empleado Objeto UsuarioEmpleado a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(UsuarioEmpleado empleado) {
        boolean deleted = false;
        if (empleado != null && getById(empleado.getId()) != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

                stmt.setInt(1, empleado.getId());
                stmt.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
