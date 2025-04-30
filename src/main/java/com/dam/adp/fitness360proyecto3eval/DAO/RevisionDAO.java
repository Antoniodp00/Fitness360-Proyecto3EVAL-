package com.dam.adp.fitness360proyecto3eval.DAO;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con las revisiones físicas de los clientes en la base de datos.
 * Proporciona constantes SQL para insertar, buscar, actualizar y eliminar revisiones.
 */
public class RevisionDAO {
    /** Consulta SQL para insertar una nueva revisión física */
    private static final String SQL_INSERT =
            "INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, imagen, idCliente, idEmpleado, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

    /** Consulta SQL para buscar una revisión por su ID */
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Revision WHERE idRevision = ?";

    /** Consulta SQL para obtener todas las revisiones */
    private static final String SQL_GET_ALL =
            "SELECT * FROM Revision";

    /** Consulta SQL para actualizar los datos de una revisión */
    private static final String SQL_UPDATE =
            "UPDATE Revision SET fecha = ?, peso = ?, grasa = ?, musculo = ?, mPecho = ?, mCintura = ?, mCadera = ?, observaciones = ?, imagen = ?, idCliente = ?, idEmpleado = ?, updatedAt = ? WHERE idRevision = ?";

    /** Consulta SQL para eliminar una revisión */
    private static final String SQL_DELETE =
            "DELETE FROM Revision WHERE idRevision = ?";

}
