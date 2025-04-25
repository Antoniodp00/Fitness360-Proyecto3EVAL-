package com.dam.adp.fitness360proyecto3eval.baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos utilizando el patrón Singleton.
 * Lee la configuración de conexión desde un archivo XML y proporciona métodos
 * para obtener y cerrar la conexión a la base de datos.
 */
public class ConnectionDB {
    /** Nombre del archivo XML que contiene la configuración de conexión */
    private final static String FILE = "connection.xml";
    /** Objeto de conexión a la base de datos */
    private static Connection con;
    /** Instancia única de la clase ConnectionDB (patrón Singleton) */
    private static ConnectionDB _instance;

    /**
     * Constructor privado que inicializa la conexión a la base de datos.
     * Lee las propiedades de conexión desde un archivo XML y establece la conexión.
     */
    private ConnectionDB() {
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
        try {
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    /**
     * Obtiene una conexión a la base de datos.
     * Si no existe una instancia de ConnectionDB, la crea.
     * 
     * @return Objeto Connection para interactuar con la base de datos
     */
    public static Connection getConnection() {
        if (_instance == null) {
            _instance = new ConnectionDB();
        }
        return con;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
