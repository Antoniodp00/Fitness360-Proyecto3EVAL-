package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClienteDAO {

    private final static String SQL_ALL = "SELECT * FROM Cliente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cliente WHERE idCliente= ?";
    private final static String SQL_FIND_BY_NAME_USER = "SELECT * FROM Cliente WHERE nombreUsuario = ?";
    private final static String SQL_INSERT ="INSERT INTO Cliente (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, altura, estado, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";;
    private final static String SQL_DISABLE = "UPDATE Cliente SET estado = ?, updatedAt = ? WHERE idCliente = ?";
    private static final String SQL_UPDATE = "UPDATE Cliente SET nombreUsuario = ?, nombre = ?, apellidos = ?, correo = ?, password = ?, telefono = ?, fechaNacimiento = ?, sexo = ?, altura = ?, estado = ?, updatedAt = ? WHERE idCliente = ?";


    private static UsuarioCliente mapearCliente(ResultSet rs) throws SQLException {
        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(rs.getInt("idCliente"));
        cliente.setNombreUsuario(rs.getString("nombreUsuario"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setPassword(rs.getString("password"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));

        String sexoStr = rs.getString("sexo");
        if (sexoStr != null) {
            cliente.setSexo(Sexo.valueOf(sexoStr));
        }
        cliente.setAltura(rs.getDouble("altura"));
        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            cliente.setEstado(Estado.valueOf(estadoStr));
        }

        cliente.setCreatedAt(rs.getTimestamp("createdAt"));
        cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return cliente;
    }


    public static List<UsuarioCliente> getAll() {
        List<UsuarioCliente> clientes = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                UsuarioCliente cliente = mapearCliente(rs);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clientes;
    }

    public static UsuarioCliente findById(int idCliente) {
        UsuarioCliente cliente = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID);
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public static UsuarioCliente finByUserName(String nombreUsuario) {
        UsuarioCliente cliente = null;

        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_NAME_USER);
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public static UsuarioCliente insert(UsuarioCliente cliente) {
        if (cliente != null && finByUserName(cliente.getNombreUsuario()) == null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setString(1, cliente.getNombreUsuario());
                pst.setString(2, cliente.getNombre());
                pst.setString(3, cliente.getApellidos());
                pst.setString(4, cliente.getCorreo());
                pst.setString(5, cliente.getPassword());
                pst.setString(6, cliente.getTelefono());
                pst.setDate(7, cliente.getFechaNacimiento() != null ? new java.sql.Date(cliente.getFechaNacimiento().getTime()) : null);
                pst.setString(8, cliente.getSexo() != null ? cliente.getSexo().name() : null);
                pst.setDouble(9, cliente.getAltura());
                pst.setString(10, Estado.ACTIVO.name());
                pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            cliente = null;
        }
        return cliente;
    }

    public static boolean disableUsuarioCliente(int id) {
        boolean disabled = false;
        if (findById(id) != null) {
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

    public static boolean update(UsuarioCliente cliente) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, cliente.getNombreUsuario());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellidos());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getPassword());
            stmt.setString(6, cliente.getTelefono());
            stmt.setDate(7, cliente.getFechaNacimiento() != null ? new java.sql.Date(cliente.getFechaNacimiento().getTime()) : null);
            stmt.setString(8, cliente.getSexo() != null ? cliente.getSexo().name() : null);
            stmt.setDouble(9, cliente.getAltura());
            stmt.setString(10, cliente.getEstado() != null ? cliente.getEstado().name() : null);
            stmt.setTimestamp(11, new Timestamp(System.currentTimeMillis())); // updatedAt
            stmt.setInt(12, cliente.getId()); // idCliente

            int filas = stmt.executeUpdate();
            actualizado = filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualizado;
    }


}
