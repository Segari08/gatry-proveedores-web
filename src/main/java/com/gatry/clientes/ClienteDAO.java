
package com.gatry.clientes;

import com.gatry.proveedores.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Registrar un nuevo cliente.
    public void insertar(Cliente cliente) throws SQLException {

        String sql = "INSERT INTO Cliente "
                + "(nombre, telefono, correo, direccion, tipo_cliente) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getTelefono());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.setString(4, cliente.getDireccion());
            sentencia.setString(5, cliente.getTipoCliente());

            sentencia.executeUpdate();

            try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    cliente.setIdCliente(resultado.getInt(1));
                }
            }
        }
    }

    // Consultar todos los clientes registrados.
    public List<Cliente> consultarTodos() throws SQLException {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM Cliente ORDER BY id_cliente";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Cliente cliente = new Cliente(
                        resultado.getInt("id_cliente"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("correo"),
                        resultado.getString("direccion"),
                        resultado.getString("tipo_cliente")
                );

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    // Buscar un cliente por su identificador.
    public Cliente buscarPorId(int idCliente) throws SQLException {

        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {
                    return new Cliente(
                            resultado.getInt("id_cliente"),
                            resultado.getString("nombre"),
                            resultado.getString("telefono"),
                            resultado.getString("correo"),
                            resultado.getString("direccion"),
                            resultado.getString("tipo_cliente")
                    );
                }
            }
        }

        return null;
    }

    // Actualizar la información de un cliente.
    public boolean actualizar(Cliente cliente) throws SQLException {

        String sql = "UPDATE Cliente SET nombre = ?, telefono = ?, "
                + "correo = ?, direccion = ?, tipo_cliente = ? "
                + "WHERE id_cliente = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getTelefono());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.setString(4, cliente.getDireccion());
            sentencia.setString(5, cliente.getTipoCliente());
            sentencia.setInt(6, cliente.getIdCliente());

            return sentencia.executeUpdate() > 0;
        }
    }

    // Eliminar un cliente por su identificador.
    public boolean eliminar(int idCliente) throws SQLException {

        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            return sentencia.executeUpdate() > 0;
        }
    }
}
