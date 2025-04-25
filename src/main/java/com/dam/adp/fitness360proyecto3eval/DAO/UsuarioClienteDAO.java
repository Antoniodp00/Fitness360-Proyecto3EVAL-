package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.Estado;
import com.dam.adp.fitness360proyecto3eval.model.Sexo;
import com.dam.adp.fitness360proyecto3eval.model.UsuarioCliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClienteDAO {

    private final static String SQL_ALL = "SELECT * FROM Cliente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cliente WHERE idCliente= ?";
    private final static String SQL_INSERT = "INSERT INTO Cliente (nombreUsuarioCliente) VALUES (?)";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Cliente WHERE nombreUsuarioCliente = ?";
    private final static String SQL_DISABLE = "UPDATE Cliente SET estado = 'inactivo' WHERE idCliente = ?";


    public static List<UsuarioCliente> getAll() {
        List<UsuarioCliente> clientes = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                UsuarioCliente cliente = new UsuarioCliente();
                cliente.setIdUsuario(rs.getInt("idCliente"));
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
                cliente.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                cliente.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectionDB.closeConnection();
        return clientes;
    }
}
