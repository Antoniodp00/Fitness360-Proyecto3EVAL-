package com.dam.adp.fitness360proyecto3eval;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase utilitaria que proporciona métodos básicos para realizar consultas
 * directas a la base de datos sin utilizar las clases DAO.
 * 
 * Esta clase es principalmente para operaciones simples o de prueba.
 * Para operaciones complejas o en producción, se recomienda utilizar
 * las clases DAO específicas que proporcionan una capa de abstracción
 * y manejo de errores más robusta.
 */
public class ConsultasBasicas {

    /** Logger para registro de eventos */
    private static final Logger logger = LoggerFactory.getLogger(ConsultasBasicas.class);

    /**
     * Inserta un nuevo cliente en la base de datos con la información básica proporcionada.
     * 
     * @param nombreUsuario Nombre de usuario único para el cliente
     * @param nombre Nombre real del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Dirección de correo electrónico del cliente
     * @param password Contraseña del cliente (debería estar encriptada antes de llamar a este método)
     */
    public static void insertarCliente(String nombreUsuario, String nombre, String apellidos, String correo, String password) {
        String query = "INSERT INTO Cliente (nombreUsuario, nombre, apellidos, correo, password) VALUES (?, ?, ?, ?, ?)";

        logger.debug("Intentando insertar cliente: {}", nombreUsuario);

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, nombre);
            stmt.setString(3, apellidos);
            stmt.setString(4, correo);
            stmt.setString(5, password);
            int rowsAffected = stmt.executeUpdate();

            logger.info("Cliente insertado correctamente. Filas afectadas: {}", rowsAffected);

        } catch (SQLException e) {
            logger.error("Error al insertar cliente: {}", e.getMessage(), e);
        }
    }
}
