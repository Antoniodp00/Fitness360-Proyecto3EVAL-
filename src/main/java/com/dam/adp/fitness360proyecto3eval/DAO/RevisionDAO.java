package com.dam.adp.fitness360proyecto3eval.DAO;

public class RevisionDAO {
    private static final String SQL_INSERT =
            "INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, imagen, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Revision WHERE idRevision = ?";

    private static final String SQL_GET_ALL =
            "SELECT * FROM Revision";

    private static final String SQL_UPDATE =
            "UPDATE Revision SET fecha = ?, peso = ?, grasa = ?, musculo = ?, mPecho = ?, mCintura = ?, mCadera = ?, observaciones = ?, imagen = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRevision = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Revision WHERE idRevision = ?";

}
