package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClienteDAO {

    private final static String SQL_ALL = "SELECT * FROM Cliente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cliente WHERE idCliente= ?";
    private final static String SQL_INSERT = "INSERT INTO Cliente (nombreUsuarioCliente) VALUES (?)";
    private final static String SQL_FIND_BY_NAME_USER = "SELECT * FROM Cliente WHERE nombreUsuarioCliente = ?";
    private final static String SQL_DISABLE = "UPDATE Cliente SET estado = ?, updatedAt = ? WHERE idCliente = ?";


    public static List<UsuarioCliente> getAll() {
        List<UsuarioCliente> clientes = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                UsuarioCliente cliente = new UsuarioCliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNombreUsuario(rs.getString("nombreUsuario"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("password"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                cliente.setSexo(Sexo.valueOf(rs.getString("sexo")));
                cliente.setAltura(rs.getDouble("altura"));
                cliente.setEstado(Estado.valueOf(rs.getString("estado")));
                cliente.setCreatedAt(rs.getTimestamp("createdAt"));
                cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
                cliente.setRutinasAsignadas(new ArrayList<>());
                cliente.setDietasAsignadas(new ArrayList<>());
                cliente.setRevisiones(new ArrayList<>());
                cliente.setTarifasContratadas(new ArrayList<>());
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectionDB.closeConnection();
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
                cliente = new UsuarioCliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNombreUsuario(rs.getString("nombreUsuario"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("password"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                cliente.setSexo(Sexo.valueOf(rs.getString("sexo")));
                cliente.setAltura(rs.getDouble("altura"));
                cliente.setEstado(Estado.valueOf(rs.getString("estado")));
                cliente.setCreatedAt(rs.getTimestamp("createdAt"));
                cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
                cliente.setRutinasAsignadas(new ArrayList<>());
                cliente.setDietasAsignadas(new ArrayList<>());
                cliente.setRevisiones(new ArrayList<>());
                cliente.setTarifasContratadas(new ArrayList<>());
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
                cliente = new UsuarioCliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNombreUsuario(rs.getString("nombreUsuario"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("password"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                cliente.setSexo(Sexo.valueOf(rs.getString("sexo")));
                cliente.setAltura(rs.getDouble("altura"));
                cliente.setEstado(Estado.valueOf(rs.getString("estado")));
                cliente.setCreatedAt(rs.getTimestamp("createdAt"));
                cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
                cliente.setRutinasAsignadas(new ArrayList<>());
                cliente.setDietasAsignadas(new ArrayList<>());
                cliente.setRevisiones(new ArrayList<>());
                cliente.setTarifasContratadas(new ArrayList<>());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public static UsuarioCliente insert(UsuarioCliente cliente) {
      if(cliente != null && finByUserName(cliente.getNombreUsuario()) == null){
          try(PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_INSERT)){
              pst.setString(1, cliente.getNombreUsuario());
              pst.executeUpdate();
          }catch (SQLException e) {
              throw new RuntimeException(e);
          }
      }else {
          cliente = null;
      }
      return cliente;
    }
    public static boolean disableUsuarioCliente(int id) {
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
}
