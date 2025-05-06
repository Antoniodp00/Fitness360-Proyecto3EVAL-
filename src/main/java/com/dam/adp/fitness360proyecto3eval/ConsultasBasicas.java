package com.dam.adp.fitness360proyecto3eval;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsultasBasicas {
    public static void insertarCliente(String nombreUsuario, String nombre, String apellidos, String correo, String password) {
        String query = "INSERT INTO Cliente (nombreUsuario, nombre, apellidos, correo, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, nombre);
            stmt.setString(3, apellidos);
            stmt.setString(4, correo);
            stmt.setString(5, password);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " fila(s) insertada(s)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
