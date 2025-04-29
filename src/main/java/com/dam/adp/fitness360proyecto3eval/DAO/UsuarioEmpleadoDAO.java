package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UsuarioEmpleadoDAO {

    private static final String SQL_INSERT = "INSERT INTO Empleado (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, descripcion, rol, especialidad, estado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT * FROM Empleado WHERE idEmpleado = ?";

    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM Empleado WHERE nombreUsuario = ?";

    private static final String SQL_GET_ALL = "SELECT * FROM Empleado";

    private static final String SQL_UPDATE = "UPDATE Empleado SET nombreUsuario = ?, nombre = ?, apellidos = ?, correo = ?, password = ?, telefono = ?, fechaNacimiento = ?, sexo = ?, descripcion = ?, rol = ?, especialidad = ?, estado = ?, updatedAt = ? WHERE idEmpleado = ?";

    private static final String SQL_DISABLE = "UPDATE Empleado SET estado = 'INACTIVO', updatedAt = NOW() WHERE idEmpleado = ?";

    private static final String SQL_GET_RUTINAS_CREADAS = "SELECT * FROM Rutina WHERE idCreador = ?";

    private static final String SQL_GET_DIETAS_CREADAS = "SELECT * FROM Dieta WHERE idCreador = ?";

    private static UsuarioEmpleado mapearEmpleado(ResultSet rs) throws SQLException {
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

    public static List<UsuarioEmpleado> getAll() {
        List<UsuarioEmpleado> empleados = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                UsuarioEmpleado empleado = mapearEmpleado(rs);
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectionDB.closeConnection();
        return empleados;
    }

    public static UsuarioEmpleado findById(int idEmpleado) {
        UsuarioEmpleado empleado = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID);
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


    public static UsuarioEmpleado finByUserName(String nombreUsuario) {
        UsuarioEmpleado empleado = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_USERNAME);
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

    public static UsuarioEmpleado insert(UsuarioEmpleado empleado) {
        if(empleado != null && finByUserName(empleado.getNombreUsuario()) == null){
            try(PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)){
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
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            empleado = null;
        }
        return empleado;
    }
    public static boolean disableUsuarioEmpleado(int id) {
        boolean disabled = false;
        if(findById(id) != null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_DISABLE)) {
                pst.setString(1, Estado.INACTIVO.name());
                pst.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                pst.setInt(3, id);
                pst.executeUpdate();
                disabled = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return disabled;
    }
    public static boolean update(UsuarioEmpleado empleado) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, empleado.getNombreUsuario());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellidos());
            stmt.setString(4, empleado.getCorreo());
            stmt.setString(5, empleado.getPassword());
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
            e.printStackTrace();
        }
        return actualizado;
    }


}
