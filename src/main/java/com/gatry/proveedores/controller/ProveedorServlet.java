
package com.gatry.proveedores.controller;

import com.gatry.proveedores.Proveedor;
import com.gatry.proveedores.ProveedorDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/proveedores")
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String resultado = request.getParameter("resultado");

        if ("creado".equals(resultado)) {
            request.setAttribute("mensajeExito",
                    "Proveedor registrado correctamente.");
        } else if ("actualizado".equals(resultado)) {
            request.setAttribute("mensajeExito",
                    "Proveedor actualizado correctamente.");
        } else if ("eliminado".equals(resultado)) {
            request.setAttribute("mensajeExito",
                    "Proveedor eliminado correctamente.");
        } else if ("no-encontrado".equals(resultado)) {
            request.setAttribute("mensajeError",
                    "No se encontró el proveedor solicitado.");
        }

        String idEditar = request.getParameter("editar");

        if (idEditar != null) {
            try {
                int id = Integer.parseInt(idEditar);

                boolean encontrado = false;

                for (Proveedor proveedor : proveedorDAO.consultarTodos()) {
                    if (proveedor.getIdProveedor() == id) {
                        request.setAttribute("proveedorEditar", proveedor);
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    request.setAttribute("mensajeError",
                            "No se encontró el proveedor solicitado.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("mensajeError",
                        "Identificador de proveedor no válido.");

            } catch (SQLException e) {
                getServletContext().log(
                        "Error al buscar el proveedor para editar", e
                );

                request.setAttribute("mensajeError",
                        "No fue posible consultar el proveedor.");
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

        // Si no se especifica una acción, se interpreta como un registro.
        if (accion == null || accion.isBlank()) {
            accion = "crear";
        }

        try {
            switch (accion) {

                case "crear":
                    crearProveedor(request, response);
                    break;

                case "actualizar":
                    actualizarProveedor(request, response);
                    break;

                case "eliminar":
                    eliminarProveedor(request, response);
                    break;

                default:
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Acción no válida."
                    );
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError",
                    "El identificador del proveedor no es válido.");
            mostrarPagina(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("mensajeError", e.getMessage());
            mostrarPagina(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Error al procesar la operación de proveedores", e
            );

            request.setAttribute("mensajeError",
                    "No fue posible completar la operación.");
            mostrarPagina(request, response);
        }
    }

    private void crearProveedor(HttpServletRequest request,
                                HttpServletResponse response)
            throws SQLException, IOException {

        String nombre = request.getParameter("nombreProveedor");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");

        validarCampos(nombre, telefono, correo, direccion);

        Proveedor proveedor = new Proveedor(
                nombre.trim(),
                telefono.trim(),
                correo.trim(),
                direccion.trim()
        );

        proveedorDAO.insertar(proveedor);

        response.sendRedirect(
                request.getContextPath() + "/proveedores?resultado=creado"
        );
    }

    private void actualizarProveedor(HttpServletRequest request,
                                     HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("idProveedor"));

        String nombre = request.getParameter("nombreProveedor");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");

        validarCampos(nombre, telefono, correo, direccion);

        Proveedor proveedor = new Proveedor(
                nombre.trim(),
                telefono.trim(),
                correo.trim(),
                direccion.trim()
        );

        proveedor.setIdProveedor(id);

        boolean actualizado = proveedorDAO.actualizar(proveedor);

        String resultado = actualizado ? "actualizado" : "no-encontrado";

        response.sendRedirect(
                request.getContextPath() + "/proveedores?resultado=" + resultado
        );
    }

    private void eliminarProveedor(HttpServletRequest request,
                                   HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("idProveedor"));

        boolean eliminado = proveedorDAO.eliminar(id);

        String resultado = eliminado ? "eliminado" : "no-encontrado";

        response.sendRedirect(
                request.getContextPath() + "/proveedores?resultado=" + resultado
        );
    }

    private void validarCampos(String nombre, String telefono,
                               String correo, String direccion) {

        if (nombre == null || nombre.isBlank()
                || telefono == null || telefono.isBlank()
                || correo == null || correo.isBlank()
                || direccion == null || direccion.isBlank()) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios."
            );
        }
    }

    private void mostrarPagina(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Proveedor> proveedores =
                    proveedorDAO.consultarTodos();

            request.setAttribute("proveedores", proveedores);

        } catch (SQLException e) {
            getServletContext().log(
                    "Error al consultar proveedores", e
            );

            request.setAttribute("mensajeError",
                    "No fue posible consultar los proveedores.");
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/proveedores.jsp"
        ).forward(request, response);
    }
}
