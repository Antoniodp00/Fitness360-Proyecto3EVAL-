package com.dam.adp.fitness360proyecto3eval.DAO;

public class TarifaDAO {
    private static final String SQL_INSERT =
            "INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Tarifa WHERE idTarifa = ?";

    private static final String SQL_GET_ALL =
            "SELECT * FROM Tarifa";

    private static final String SQL_UPDATE =
            "UPDATE Tarifa SET nombre = ?, precio = ?, descripcion = ?, periodo = ?, idEmpleado = ?, updatedAt = ? WHERE idTarifa = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Tarifa WHERE idTarifa = ?";

}
