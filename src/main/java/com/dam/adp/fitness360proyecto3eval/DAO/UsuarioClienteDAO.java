package com.dam.adp.fitness360proyecto3eval.DAO;

import com.dam.adp.fitness360proyecto3eval.baseDatos.ConnectionDB;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con los usuarios clientes en la base de datos.
 * Proporciona métodos para obtener, buscar, insertar, actualizar y desactivar usuarios clientes.
 */
public class UsuarioClienteDAO implements GenericDAO<UsuarioCliente>{

    /**
     * Consulta SQL para obtener todos los clientes
     */
    private final static String SQL_ALL = "SELECT * FROM Cliente";

    /**
     * Consulta SQL para buscar un cliente por su ID
     */
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cliente WHERE idCliente= ?";

    /**
     * Consulta SQL para buscar un cliente por su nombre de usuario
     */
    private final static String SQL_FIND_BY_NAME_USER = "SELECT * FROM Cliente WHERE nombreUsuario = ?";

    /**
     * Consulta SQL para buscar un cliente por su correo electrónico
     */
    private final static String SQL_FIND_BY_EMAIL = "SELECT * FROM Cliente WHERE correo = ?";

    /**
     * Consulta SQL para insertar un nuevo cliente
     */
    private final static String SQL_INSERT = "INSERT INTO Cliente (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, altura, estado, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Consulta SQL para desactivar un cliente
     */
    private final static String SQL_DISABLE = "UPDATE Cliente SET estado = ?, updatedAt = ? WHERE idCliente = ?";

    /**
     * Consulta SQL para actualizar los datos de un cliente
     */
    private static final String SQL_UPDATE = "UPDATE Cliente SET nombreUsuario = ?, nombre = ?, apellidos = ?, correo = ?, password = ?, telefono = ?, fechaNacimiento = ?, sexo = ?, altura = ?, estado = ?, updatedAt = ? WHERE idCliente = ?";

    /**
     * Consulta SQL para eliminar un cliente
     */
    private static final String SQL_DELETE = "DELETE FROM Cliente WHERE idCliente = ?";

    private static final String SQL_GET_CLIENTES_POR_TARIFA = "SELECT c.* " +
            "FROM Cliente c " +
            "JOIN ClienteTarifa ct ON c.idCliente = ct.idCliente " +
            "JOIN Tarifa t ON ct.idTarifa = t.idTarifa " +
            "WHERE t.idEmpleado = ? AND ct.estado = 'ACTIVA' GROUP BY c.nombre";

    /**
     * Método auxiliar para mapear un ResultSet a un objeto UsuarioCliente
     *
     * @param rs ResultSet con los datos del cliente
     * @return Objeto UsuarioCliente con los datos mapeados
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private UsuarioCliente mapearCliente(ResultSet rs) throws SQLException {
        UsuarioCliente cliente = new UsuarioCliente();
        cliente.setId(rs.getInt("idCliente"));
        cliente.setNombreUsuario(rs.getString("nombreUsuario"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setPassword(rs.getString("password"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));

        String sexoStr = rs.getString("sexo");
        if (sexoStr != null) {
            cliente.setSexo(Sexo.valueOf(sexoStr));
        }
        cliente.setAltura(rs.getDouble("altura"));
        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            cliente.setEstado(Estado.valueOf(estadoStr));
        }
        cliente.setCreatedAt(rs.getTimestamp("createdAt"));
        cliente.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return cliente;
    }


    /**
     * Obtiene todos los clientes de la base de datos
     *
     * @return Lista de todos los clientes
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public  List<UsuarioCliente> getAll() {
        List<UsuarioCliente> clientes = new ArrayList<>();

        try (Connection con = ConnectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {

            while (rs.next()) {
                UsuarioCliente cliente = mapearCliente(rs);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clientes;
    }

    /**
     * Busca un cliente por su ID
     *
     * @param idCliente ID del cliente a buscar
     * @return Objeto UsuarioCliente si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioCliente getById(int idCliente) {
        UsuarioCliente cliente = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    /**
     * Busca un cliente por su ID y carga sus rutinas asignadas (carga ansiosa/eager loading)
     *
     * @param idCliente ID del cliente a buscar
     * @return Objeto UsuarioCliente con sus rutinas asignadas si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioCliente findByIdEager(int idCliente) {
        ClienteRutinaDAO clienteRutinaDAO = new ClienteRutinaDAO();
        UsuarioCliente cliente = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_ID)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }

            // Ahora que el cliente está mapeado, obtenemos las rutinas asignadas
            if (cliente != null) {
                cliente.setRutinasAsignadas(clienteRutinaDAO.findByClientEager(idCliente));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }


    /**
     * Busca un cliente por su nombre de usuario
     *
     * @param nombreUsuario Nombre de usuario del cliente a buscar
     * @return Objeto UsuarioCliente si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioCliente findByUserName(String nombreUsuario) {
        UsuarioCliente cliente = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_NAME_USER)) {

            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    /**
     * Busca un cliente por su correo electrónico
     *
     * @param correo Correo electrónico del cliente a buscar
     * @return Objeto UsuarioCliente si se encuentra, null en caso contrario
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioCliente findByEmail(String correo) {
        UsuarioCliente cliente = null;

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    /**
     * Inserta un nuevo cliente en la base de datos
     *
     * @param cliente Objeto UsuarioCliente con los datos del cliente a insertar
     * @return El objeto UsuarioCliente insertado si la operación fue exitosa
     * @throws com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException Si el nombre de usuario o correo ya existe
     * @throws RuntimeException Si ocurre un error al acceder a la base de datos
     */
    public UsuarioCliente insert(UsuarioCliente cliente) {
        if (cliente == null) {
            return null;
        }

        // Verificar si el nombre de usuario ya existe
        UsuarioCliente clienteExistente = findByUserName(cliente.getNombreUsuario());
        if (clienteExistente != null) {
            throw new com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException("El nombre de usuario ya existe");
        }

        // Verificar si el correo ya existe
        clienteExistente = findByEmail(cliente.getCorreo());
        if (clienteExistente != null) {
            throw new com.dam.adp.fitness360proyecto3eval.exceptions.UsuarioYaExisteException("El correo electrónico ya existe");
        }

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, cliente.getNombreUsuario());
            pst.setString(2, cliente.getNombre());
            pst.setString(3, cliente.getApellidos());
            pst.setString(4, cliente.getCorreo());
            pst.setString(5, cliente.getPassword());
            pst.setString(6, cliente.getTelefono());
            pst.setDate(7, cliente.getFechaNacimiento() != null ? new java.sql.Date(cliente.getFechaNacimiento().getTime()) : null);
            pst.setString(8, cliente.getSexo() != null ? cliente.getSexo().name() : null);
            pst.setDouble(9, cliente.getAltura());
            pst.setString(10, Estado.ACTIVO.name());
            pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    /**
     * Desactiva un cliente en la base de datos cambiando su estado a INACTIVO
     *
     * @param id ID del cliente a desactivar
     * @return true si el cliente fue desactivado correctamente, false si el cliente no existe o hubo un error
     */
    public boolean disableUsuarioCliente(int id) {
        boolean disabled = false;
        if (getById(id) != null) {
            try (Connection con = ConnectionDB.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_DISABLE)) {

                pst.setString(1, Estado.INACTIVO.name());
                pst.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                pst.setInt(3, id);
                pst.executeUpdate();
                disabled = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return disabled;
    }

    /**
     * Actualiza los datos de un cliente en la base de datos
     *
     * @param cliente Objeto UsuarioCliente con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(UsuarioCliente cliente) {
        boolean actualizado = false;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, cliente.getNombreUsuario());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellidos());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, com.dam.adp.fitness360proyecto3eval.utilidades.HashUtil.hashPassword(cliente.getPassword()));
            stmt.setString(6, cliente.getTelefono());
            stmt.setDate(7, cliente.getFechaNacimiento() != null ? new java.sql.Date(cliente.getFechaNacimiento().getTime()) : null);
            stmt.setString(8, cliente.getSexo() != null ? cliente.getSexo().name() : null);
            stmt.setDouble(9, cliente.getAltura());
            stmt.setString(10, cliente.getEstado() != null ? cliente.getEstado().name() : null);
            stmt.setTimestamp(11, new Timestamp(System.currentTimeMillis())); // updatedAt
            stmt.setInt(12, cliente.getId()); // idCliente

            int filas = stmt.executeUpdate();
            actualizado = filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }

    public List<UsuarioCliente> findClientesByEmpleadoTarifa(int idEmpleado) {
        List<UsuarioCliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_CLIENTES_POR_TARIFA)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioCliente cliente = mapearCliente(rs);
                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    /**
     * Elimina un cliente de la base de datos
     *
     * @param cliente Objeto UsuarioCliente a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(UsuarioCliente cliente) {

        boolean deleted = false;
        if (cliente != null && getById(cliente.getId()) != null) {
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

                stmt.setInt(1, cliente.getId());
                stmt.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
