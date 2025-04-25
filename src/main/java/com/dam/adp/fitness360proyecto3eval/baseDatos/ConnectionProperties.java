package com.dam.adp.fitness360proyecto3eval.baseDatos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String server;
    private String port;
    private String dataBase;
    private String user;
    private String password;


    public ConnectionProperties(String server, String password, String user, String database, String port) {
        this.server = server;
        this.password = password;
        this.user = user;
        this.dataBase = database;
        this.port = port;
    }

    public ConnectionProperties() {

    }

    public String getServer() {
        return server;
    }


    public String getPassword() {
        return password;
    }



    public String getUser() {
        return user;
    }



    public String getDataBase() {
        return dataBase;
    }


    public String getPort() {
        return port;
    }


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

    public String getURL() {
        return "jdbc:mysql://" + server + ":" + port + "/" + dataBase;
    }
}
