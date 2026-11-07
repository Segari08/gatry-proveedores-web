
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gatry.proveedores.Proveedor" %>

<%!
    private String escaparHtml(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Proveedores | Gatry</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #F5F7FA;
            color: #212529;
            margin: 0;
            padding: 30px;
        }

        main {
            max-width: 1100px;
            margin: auto;
            background: #FFFFFF;
            padding: 25px;
            border-radius: 10px;
        }

        h1 {
            color: #082C54;
        }

        input {
            padding: 8px;
            width: 280px;
            max-width: 100%;
        }

        button {
            padding: 9px 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-guardar {
            background: #082C54;
            color: white;
        }

        .btn-editar {
            background: #0B5CAD;
            color: white;
        }

        .btn-eliminar {
            background: #B42318;
            color: white;
        }

        .btn-cancelar {
            background: #BFC5CC;
            color: #212529;
            text-decoration: none;
            padding: 9px 14px;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #082C54;
            color: white;
        }

        .error {
            color: #b42318;
            background: #fee4e2;
            padding: 12px;
            border-radius: 5px;
        }

        .exito {
            color: #067647;
            background: #dcfae6;
            padding: 12px;
            border-radius: 5px;
        }

        .acciones {
            display: flex;
            gap: 8px;
            align-items: center;
        }

        .formulario-eliminar {
            display: inline;
        }
    </style>
</head>

<body>
<main>

    <h1>Gestión de proveedores</h1>
    <p>Gatry Security &amp; Technology</p>

    <%
        String mensajeExito =
                (String) request.getAttribute("mensajeExito");

        String mensajeError =
                (String) request.getAttribute("mensajeError");

        Proveedor proveedorEditar =
                (Proveedor) request.getAttribute("proveedorEditar");

        List<Proveedor> proveedores =
                (List<Proveedor>) request.getAttribute("proveedores");
    %>

    <% if (mensajeExito != null) { %>
        <p class="exito"><%= escaparHtml(mensajeExito) %></p>
    <% } %>

    <% if (mensajeError != null) { %>
        <p class="error"><%= escaparHtml(mensajeError) %></p>
    <% } %>

    <% if (proveedorEditar == null) { %>

        <h2>Registrar proveedor</h2>

        <form action="${pageContext.request.contextPath}/proveedores"
              method="post">

            <input type="hidden" name="accion" value="crear">

            <p>
                <label for="nombreProveedor">Nombre del proveedor</label><br>
                <input type="text" id="nombreProveedor"
                       name="nombreProveedor" required>
            </p>

            <p>
                <label for="telefono">Teléfono</label><br>
                <input type="tel" id="telefono"
                       name="telefono" required>
            </p>

            <p>
                <label for="correo">Correo electrónico</label><br>
                <input type="email" id="correo"
                       name="correo" required>
            </p>

            <p>
                <label for="direccion">Dirección</label><br>
                <input type="text" id="direccion"
                       name="direccion" required>
            </p>

            <button type="submit" class="btn-guardar">
                Registrar proveedor
            </button>
        </form>

    <% } else { %>

        <h2>Actualizar proveedor</h2>

        <form action="${pageContext.request.contextPath}/proveedores"
              method="post">

            <input type="hidden" name="accion" value="actualizar">

            <input type="hidden" name="idProveedor"
                   value="<%= proveedorEditar.getIdProveedor() %>">

            <p>
                <label for="nombreProveedor">Nombre del proveedor</label><br>
                <input type="text" id="nombreProveedor"
                       name="nombreProveedor"
                       value="<%= escaparHtml(proveedorEditar.getNombreProveedor()) %>"
                       required>
            </p>

            <p>
                <label for="telefono">Teléfono</label><br>
                <input type="tel" id="telefono"
                       name="telefono"
                       value="<%= escaparHtml(proveedorEditar.getTelefono()) %>"
                       required>
            </p>

            <p>
                <label for="correo">Correo electrónico</label><br>
                <input type="email" id="correo"
                       name="correo"
                       value="<%= escaparHtml(proveedorEditar.getCorreo()) %>"
                       required>
            </p>

            <p>
                <label for="direccion">Dirección</label><br>
                <input type="text" id="direccion"
                       name="direccion"
                       value="<%= escaparHtml(proveedorEditar.getDireccion()) %>"
                       required>
            </p>

            <button type="submit" class="btn-guardar">
                Guardar cambios
            </button>

            <a href="${pageContext.request.contextPath}/proveedores"
               class="btn-cancelar">
                Cancelar
            </a>
        </form>

    <% } %>

    <h2>Proveedores registrados</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Proveedor</th>
                <th>Teléfono</th>
                <th>Correo</th>
                <th>Dirección</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>

            <% if (proveedores != null && !proveedores.isEmpty()) {
                for (Proveedor proveedor : proveedores) { %>

                <tr>
                    <td><%= proveedor.getIdProveedor() %></td>
                    <td><%= escaparHtml(proveedor.getNombreProveedor()) %></td>
                    <td><%= escaparHtml(proveedor.getTelefono()) %></td>
                    <td><%= escaparHtml(proveedor.getCorreo()) %></td>
                    <td><%= escaparHtml(proveedor.getDireccion()) %></td>

                    <td>
                        <div class="acciones">

                            <a class="btn-editar"
                               href="${pageContext.request.contextPath}/proveedores?editar=<%= proveedor.getIdProveedor() %>"
                               style="text-decoration:none; padding:9px 14px; border-radius:5px;">
                                Editar
                            </a>

                            <form class="formulario-eliminar"
                                  action="${pageContext.request.contextPath}/proveedores"
                                  method="post"
                                  onsubmit="return confirm('¿Está seguro de eliminar este proveedor?');">

                                <input type="hidden" name="accion"
                                       value="eliminar">

                                <input type="hidden" name="idProveedor"
                                       value="<%= proveedor.getIdProveedor() %>">

                                <button type="submit" class="btn-eliminar">
                                    Eliminar
                                </button>
                            </form>

                        </div>
                    </td>
                </tr>

            <%  }
               } else { %>

                <tr>
                    <td colspan="6">No hay proveedores registrados.</td>
                </tr>

            <% } %>

        </tbody>
    </table>

</main>
</body>
</html>
