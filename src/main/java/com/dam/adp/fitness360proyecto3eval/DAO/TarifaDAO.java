package com.dam.adp.fitness360proyecto3eval.DAO;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las tarifas en la base de datos.
 * Proporciona constantes SQL para insertar, buscar, actualizar y eliminar tarifas.
 */
public class TarifaDAO {
    /** Consulta SQL para insertar una nueva tarifa */
    private static final String SQL_INSERT =
            "INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una tarifa por su ID */
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Tarifa WHERE idTarifa = ?";

    /** Consulta SQL para obtener todas las tarifas */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Tarifa";

    /** Consulta SQL para actualizar los datos de una tarifa */
    private static final String SQL_UPDATE =
            "UPDATE Tarifa SET nombre = ?, precio = ?, descripcion = ?, periodo = ?, idEmpleado = ?, updatedAt = ? WHERE idTarifa = ?";

    /** Consulta SQL para eliminar una tarifa */
    private static final String SQL_DELETE =
            "DELETE FROM Tarifa WHERE idTarifa = ?";

}
