package com.dam.adp.fitness360proyecto3eval.DAO;

public class DietaDAO {
    private static final String SQL_INSERT =
            "INSERT INTO Dieta (nombre, descripcion, archivo, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Dieta WHERE idDieta = ?";

    private static final String SQL_GET_ALL =
            "SELECT * FROM Dieta";

    private static final String SQL_UPDATE =
            "UPDATE Dieta SET nombre = ?, descripcion = ?, archivo = ?, idEmpleado = ?, updatedAt = ? WHERE idDieta = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Dieta WHERE idDieta = ?";

}
