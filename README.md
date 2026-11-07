# Gatry – Módulo web de gestión de proveedores

## 1. Descripción del proyecto

Módulo web desarrollado para **Gatry Security & Technology**, orientado a la gestión de proveedores dentro del sistema de administración de clientes, proyectos, cotizaciones, servicios técnicos e instalaciones de seguridad electrónica.

Este módulo permite administrar la información básica de los proveedores mediante operaciones CRUD (Crear, Consultar, Actualizar y Eliminar), utilizando una aplicación web conectada a una base de datos MySQL.

## 2. Funcionalidades

- Registrar nuevos proveedores.
- Consultar los proveedores almacenados en la base de datos.
- Actualizar la información de proveedores existentes.
- Eliminar proveedores registrados.
- Validar los campos obligatorios de los formularios.
- Mostrar mensajes de confirmación y error.
- Proteger la salida de datos dinámicos mediante escape de caracteres HTML.

## 3. Tecnologías utilizadas

| Tecnología | Aplicación |
|---|---|
| Java 21 | Lenguaje de programación |
| Jakarta Servlet 6.0 | Control de solicitudes HTTP |
| Jakarta Server Pages (JSP) 3.1 | Generación de vistas dinámicas |
| JDBC | Conexión y acceso a datos |
| MySQL | Sistema gestor de base de datos |
| Apache Maven | Gestión de dependencias y construcción |
| Apache Tomcat 10.1 | Servidor de aplicaciones |
| HTML5 | Estructura de las vistas |
| CSS3 | Presentación e interfaz gráfica |
| Git | Control de versiones |

## 4. Arquitectura del módulo

El proyecto utiliza una separación de responsabilidades entre las clases encargadas de la conexión, el acceso a datos, el modelo, el controlador y la vista.

```text
gatry-proveedores-web/
│
├── pom.xml
│
├── .gitignore
│
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── gatry/
        │           └── proveedores/
        │               ├── Conexion.java
        │               ├── Proveedor.java
        │               ├── ProveedorDAO.java
        │               │
        │               └── controller/
        │                   └── ProveedorServlet.java
        │
        └── webapp/
            └── WEB-INF/
                └── views/
                    └── proveedores.jsp
```

### Responsabilidad de los componentes

- **Conexion.java:** establece la conexión con la base de datos MySQL.
- **Proveedor.java:** representa la entidad proveedor y sus atributos.
- **ProveedorDAO.java:** implementa las operaciones de acceso y manipulación de datos.
- **ProveedorServlet.java:** recibe y procesa las solicitudes HTTP mediante los métodos GET y POST.
- **proveedores.jsp:** presenta los formularios y la información de los proveedores.

## 5. Requisitos del entorno

Para ejecutar el proyecto se requiere:

- JDK 21 o compatible con la versión configurada.
- Apache Maven 3.8.7 o superior.
- Apache Tomcat 10.1.
- MySQL Server.
- Base de datos `gatry_db`.
- Controlador JDBC de MySQL, administrado mediante Maven.

## 6. Configuración de la conexión

La aplicación utiliza las siguientes variables de entorno para establecer la conexión con MySQL:

| Variable | Descripción |
|---|---|
| `GATRY_DB_USER` | Usuario de conexión a MySQL |
| `GATRY_DB_PASSWORD` | Contraseña del usuario de conexión |

Estas variables deben configurarse en el entorno donde se ejecute Tomcat.

**Importante:** las credenciales no se almacenan directamente en el código fuente ni deben publicarse en el repositorio.

La base de datos utilizada por el módulo es `gatry_db`. La tabla requerida para su funcionamiento es `Proveedor`, de acuerdo con la estructura de la base de datos del proyecto Gatry.

## 7. Construcción del proyecto

Ubicarse en el directorio raíz del proyecto y ejecutar:

```bash
mvn clean package
```

Este comando limpia los archivos generados anteriormente, compila el código fuente y construye el archivo WAR.

El artefacto generado se encuentra en:

```text
target/gatry-proveedores-web.war
```

## 8. Despliegue en Apache Tomcat

Copiar el archivo WAR al directorio de aplicaciones de Tomcat:

```bash
sudo cp target/gatry-proveedores-web.war /var/lib/tomcat10/webapps/
```

Reiniciar el servicio:

```bash
sudo systemctl restart tomcat10
```

Verificar su estado:

```bash
sudo systemctl status tomcat10 --no-pager
```

## 9. Acceso a la aplicación

Una vez desplegada correctamente, la aplicación está disponible en:

```text
http://localhost:8080/gatry-proveedores-web/proveedores
```

## 10. Pruebas realizadas

Se realizaron pruebas funcionales sobre las operaciones principales del módulo:

- **Crear:** registro de nuevos proveedores.
- **Consultar:** visualización de los proveedores almacenados.
- **Actualizar:** modificación de los datos de un proveedor.
- **Eliminar:** eliminación de un proveedor mediante confirmación.

También se verificó la compilación del proyecto con Maven y su despliegue en Apache Tomcat.

## 11. Control de versiones

El proyecto utiliza Git para el seguimiento de cambios y la administración de versiones del código fuente.

Se excluyen del control de versiones los archivos generados durante la construcción, las configuraciones locales de los editores y los archivos que puedan contener credenciales.

## 12. Proyecto

**Nombre:** Gatry Security & Technology
**Módulo:** Gestión de proveedores
**Tecnología principal:** Java con Jakarta Servlets y JSP
**Base de datos:** MySQL
