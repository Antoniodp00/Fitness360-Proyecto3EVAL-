package com.dam.adp.fitness360proyecto3eval.baseDatos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Clase que almacena las propiedades de conexión a la base de datos.
 * Esta clase está diseñada para ser serializada/deserializada desde/hacia XML
 * utilizando JAXB (Java Architecture for XML Binding).
 * Contiene información como servidor, puerto, nombre de la base de datos,
 * usuario y contraseña necesarios para establecer una conexión.
 */
@XmlRootElement(name="connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionProperties implements Serializable {
    /** Identificador de versión para la serialización */
    private static final long serialVersionUID = 1L;
    /** Dirección del servidor de base de datos */
    private String server;
    /** Puerto del servidor de base de datos */
    private String port;
    /** Nombre de la base de datos */
    private String dataBase;
    /** Nombre de usuario para la conexión */
    private String user;
    /** Contraseña para la conexión */
    private String password;

    /**
     * Constructor con parámetros para inicializar todas las propiedades de conexión.
     * 
     * @param server Dirección del servidor de base de datos
     * @param password Contraseña para la conexión
     * @param user Nombre de usuario para la conexión
     * @param database Nombre de la base de datos
     * @param port Puerto del servidor de base de datos
     */
    public ConnectionProperties(String server, String password, String user, String database, String port) {
        this.server = server;
        this.password = password;
        this.user = user;
        this.dataBase = database;
        this.port = port;
    }

    /**
     * Constructor por defecto sin parámetros.
     * Requerido para la deserialización XML con JAXB.
     */
    public ConnectionProperties() {
    }

    /**
     * Obtiene la dirección del servidor de base de datos.
     * 
     * @return La dirección del servidor
     */
    public String getServer() {
        return server;
    }

    /**
     * Obtiene la contraseña para la conexión a la base de datos.
     * 
     * @return La contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el nombre de usuario para la conexión a la base de datos.
     * 
     * @return El nombre de usuario
     */
    public String getUser() {
        return user;
    }

    /**
     * Obtiene el nombre de la base de datos.
     * 
     * @return El nombre de la base de datos
     */
    public String getDataBase() {
        return dataBase;
    }

    /**
     * Obtiene el puerto del servidor de base de datos.
     * 
     * @return El puerto
     */
    public String getPort() {
        return port;
    }

    /**
     * Genera una representación en cadena de texto de las propiedades de conexión.
     * Útil para depuración.
     * 
     * @return Cadena de texto con todas las propiedades de conexión
     */
    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", database='" + dataBase + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Genera la URL JDBC para la conexión a la base de datos.
     * 
     * @return URL JDBC completa para conectar a la base de datos
     */
    public String getURL() {
        return "jdbc:mysql://" + server + ":" + port + "/" + dataBase;
    }
}
