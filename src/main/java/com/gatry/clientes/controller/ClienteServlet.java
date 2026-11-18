
package com.gatry.clientes.controller;

import com.gatry.clientes.Cliente;
import com.gatry.clientes.ClienteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String editar = request.getParameter("editar");

        if (editar != null) {
            try {
                int idCliente = Integer.parseInt(editar);

                if (idCliente <= 0) {
                    throw new NumberFormatException();
                }

                Cliente cliente = clienteDAO.buscarPorId(idCliente);

                if (cliente == null) {
                    response.sendRedirect(
                            request.getContextPath()
                            + "/clientes?resultado=no-encontrado");
                    return;
                }

                request.setAttribute("clienteEditar", cliente);

            } catch (NumberFormatException e) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/clientes?resultado=id-invalido");
                return;

            } catch (SQLException e) {
                throw new ServletException(
                        "Error al consultar el cliente.", e);
            }
        }

        mostrarPagina(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        try {
            switch (accion == null ? "" : accion) {

                case "crear":
                    crearCliente(request, response);
                    break;

                case "actualizar":
                    actualizarCliente(request, response);
                    break;

                case "eliminar":
                    eliminarCliente(request, response);
                    break;

                default:
                    response.sendRedirect(
                            request.getContextPath()
                            + "/clientes?resultado=accion-invalida");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/clientes?resultado=id-invalido");

        } catch (IllegalArgumentException e) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/clientes?resultado=datos-invalidos");

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al ejecutar la operación del cliente.", e);
        }
    }

    private void crearCliente(HttpServletRequest request,
                              HttpServletResponse response)
            throws SQLException, IOException {

        Cliente cliente = obtenerDatosFormulario(request);

        clienteDAO.insertar(cliente);

        response.sendRedirect(
                request.getContextPath()
                + "/clientes?resultado=creado");
    }

    private void actualizarCliente(HttpServletRequest request,
                                   HttpServletResponse response)
            throws SQLException, IOException {

        int idCliente = Integer.parseInt(
                request.getParameter("idCliente"));

        if (idCliente <= 0) {
            throw new NumberFormatException();
        }

        Cliente cliente = obtenerDatosFormulario(request);
        cliente.setIdCliente(idCliente);

        boolean actualizado = clienteDAO.actualizar(cliente);

        response.sendRedirect(
                request.getContextPath()
                + (actualizado
                    ? "/clientes?resultado=actualizado"
                    : "/clientes?resultado=no-encontrado"));
    }

    private void eliminarCliente(HttpServletRequest request,
                                 HttpServletResponse response)
            throws SQLException, IOException {

        int idCliente = Integer.parseInt(
                request.getParameter("idCliente"));

        if (idCliente <= 0) {
            throw new NumberFormatException();
        }

        boolean eliminado = clienteDAO.eliminar(idCliente);

        response.sendRedirect(
                request.getContextPath()
                + (eliminado
                    ? "/clientes?resultado=eliminado"
                    : "/clientes?resultado=no-encontrado"));
    }

    private Cliente obtenerDatosFormulario(HttpServletRequest request) {

        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String tipoCliente = request.getParameter("tipoCliente");

        if (nombre == null || nombre.isBlank()
                || telefono == null || telefono.isBlank()
                || tipoCliente == null || tipoCliente.isBlank()) {

            throw new IllegalArgumentException(
                    "Los campos obligatorios deben estar completos.");
        }

        return new Cliente(
                nombre.trim(),
                telefono.trim(),
                correo == null || correo.isBlank()
                        ? null : correo.trim(),
                direccion == null || direccion.isBlank()
                        ? null : direccion.trim(),
                tipoCliente.trim()
        );
    }

    private void mostrarPagina(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {

        String resultado = request.getParameter("resultado");

        if (resultado != null) {
            switch (resultado) {
                case "creado":
                    request.setAttribute(
                            "mensajeExito", "Cliente registrado correctamente.");
                    break;

                case "actualizado":
                    request.setAttribute(
                            "mensajeExito", "Cliente actualizado correctamente.");
                    break;

                case "eliminado":
                    request.setAttribute(
                            "mensajeExito", "Cliente eliminado correctamente.");
                    break;

                case "no-encontrado":
                    request.setAttribute(
                            "mensajeError", "No se encontró el cliente solicitado.");
                    break;

                case "id-invalido":
                    request.setAttribute(
                            "mensajeError", "El identificador del cliente no es válido.");
                    break;

                case "datos-invalidos":
                    request.setAttribute(
                            "mensajeError", "Verifique los datos ingresados.");
                    break;

                case "accion-invalida":
                    request.setAttribute(
                            "mensajeError", "La operación solicitada no es válida.");
                    break;
            }
        }

        try {
            List<Cliente> clientes = clienteDAO.consultarTodos();

            request.setAttribute("clientes", clientes);

            request.getRequestDispatcher(
                    "/WEB-INF/views/clientes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al cargar los clientes.", e);
        }
    }
}
