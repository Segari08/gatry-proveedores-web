
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gatry.clientes.Cliente" %>

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
    <title>Clientes | Gatry</title>

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

        input, select {
            padding: 8px;
            width: 280px;
            max-width: 100%;
            box-sizing: border-box;
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
            text-decoration: none;
            padding: 9px 14px;
            border-radius: 5px;
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

    <h1>Gestión de clientes</h1>
    <p>Gatry Security &amp; Technology</p>

    <%
        String mensajeExito =
                (String) request.getAttribute("mensajeExito");

        String mensajeError =
                (String) request.getAttribute("mensajeError");

        Cliente clienteEditar =
                (Cliente) request.getAttribute("clienteEditar");

        List<Cliente> clientes =
                (List<Cliente>) request.getAttribute("clientes");
    %>

    <% if (mensajeExito != null) { %>
        <p class="exito"><%= escaparHtml(mensajeExito) %></p>
    <% } %>

    <% if (mensajeError != null) { %>
        <p class="error"><%= escaparHtml(mensajeError) %></p>
    <% } %>

    <% if (clienteEditar == null) { %>

        <h2>Registrar cliente</h2>

        <form action="${pageContext.request.contextPath}/clientes"
              method="post">

            <input type="hidden" name="accion" value="crear">

            <p>
                <label for="nombre">Nombre del cliente</label><br>
                <input type="text" id="nombre" name="nombre"
                       maxlength="100" required>
            </p>

            <p>
                <label for="telefono">Teléfono</label><br>
                <input type="tel" id="telefono" name="telefono"
                       maxlength="15" required>
            </p>

            <p>
                <label for="correo">Correo electrónico</label><br>
                <input type="email" id="correo" name="correo"
                       maxlength="100">
            </p>

            <p>
                <label for="direccion">Dirección</label><br>
                <input type="text" id="direccion" name="direccion"
                       maxlength="150">
            </p>

            <p>
                <label for="tipoCliente">Tipo de cliente</label><br>
                <select id="tipoCliente" name="tipoCliente" required>
                    <option value="">Seleccione una opción</option>
                    <option value="Residencial">Residencial</option>
                    <option value="Comercial">Comercial</option>
                    <option value="Empresarial">Empresarial</option>
                </select>
            </p>

            <button type="submit" class="btn-guardar">
                Registrar cliente
            </button>
        </form>

    <% } else { %>

        <h2>Actualizar cliente</h2>

        <form action="${pageContext.request.contextPath}/clientes"
              method="post">

            <input type="hidden" name="accion" value="actualizar">

            <input type="hidden" name="idCliente"
                   value="<%= clienteEditar.getIdCliente() %>">

            <p>
                <label for="nombre">Nombre del cliente</label><br>
                <input type="text" id="nombre" name="nombre"
                       maxlength="100"
                       value="<%= escaparHtml(clienteEditar.getNombre()) %>"
                       required>
            </p>

            <p>
                <label for="telefono">Teléfono</label><br>
                <input type="tel" id="telefono" name="telefono"
                       maxlength="15"
                       value="<%= escaparHtml(clienteEditar.getTelefono()) %>"
                       required>
            </p>

            <p>
                <label for="correo">Correo electrónico</label><br>
                <input type="email" id="correo" name="correo"
                       maxlength="100"
                       value="<%= escaparHtml(clienteEditar.getCorreo()) %>">
            </p>

            <p>
                <label for="direccion">Dirección</label><br>
                <input type="text" id="direccion" name="direccion"
                       maxlength="150"
                       value="<%= escaparHtml(clienteEditar.getDireccion()) %>">
            </p>

            <p>
                <label for="tipoCliente">Tipo de cliente</label><br>
                <select id="tipoCliente" name="tipoCliente" required>
                    <option value="Residencial"
                        <%= "Residencial".equals(clienteEditar.getTipoCliente())
                                ? "selected" : "" %>>
                        Residencial
                    </option>

                    <option value="Comercial"
                        <%= "Comercial".equals(clienteEditar.getTipoCliente())
                                ? "selected" : "" %>>
                        Comercial
                    </option>

                    <option value="Empresarial"
                        <%= "Empresarial".equals(clienteEditar.getTipoCliente())
                                ? "selected" : "" %>>
                        Empresarial
                    </option>
                </select>
            </p>

            <button type="submit" class="btn-guardar">
                Guardar cambios
            </button>

            <a href="${pageContext.request.contextPath}/clientes"
               class="btn-cancelar">
                Cancelar
            </a>
        </form>

    <% } %>

    <h2>Clientes registrados</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Teléfono</th>
                <th>Correo</th>
                <th>Dirección</th>
                <th>Tipo</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>

            <% if (clientes != null && !clientes.isEmpty()) {
                for (Cliente cliente : clientes) { %>

                <tr>
                    <td><%= cliente.getIdCliente() %></td>
                    <td><%= escaparHtml(cliente.getNombre()) %></td>
                    <td><%= escaparHtml(cliente.getTelefono()) %></td>
                    <td><%= escaparHtml(cliente.getCorreo()) %></td>
                    <td><%= escaparHtml(cliente.getDireccion()) %></td>
                    <td><%= escaparHtml(cliente.getTipoCliente()) %></td>

                    <td>
                        <div class="acciones">

                            <a class="btn-editar"
                               href="${pageContext.request.contextPath}/clientes?editar=<%= cliente.getIdCliente() %>">
                                Editar
                            </a>

                            <form class="formulario-eliminar"
                                  action="${pageContext.request.contextPath}/clientes"
                                  method="post"
                                  onsubmit="return confirm('¿Está seguro de eliminar este cliente?');">

                                <input type="hidden" name="accion"
                                       value="eliminar">

                                <input type="hidden" name="idCliente"
                                       value="<%= cliente.getIdCliente() %>">

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
                    <td colspan="7">No hay clientes registrados.</td>
                </tr>

            <% } %>

        </tbody>
    </table>

</main>
</body>
</html>
