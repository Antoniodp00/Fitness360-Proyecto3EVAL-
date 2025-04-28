package com.dam.adp.fitness360proyecto3eval.DAO;

public class RutinaDAO {
    private static final String SQL_INSERT =
            "INSERT INTO Rutina (nombre, descripcion, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Rutina WHERE idRutina = ?";

    private static final String SQL_GET_ALL =
            "SELECT * FROM Rutina";

    private static final String SQL_UPDATE =
            "UPDATE Rutina SET nombre = ?, descripcion = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRutina = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Rutina WHERE idRutina = ?";

}
