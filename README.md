# Gatry – Módulos web de gestión de proveedores y clientes

## 1. Descripción del proyecto

Aplicación web desarrollada para **Gatry Security & Technology**, orientada a la administración de información de proveedores y clientes dentro del sistema de gestión de servicios de seguridad electrónica.

Actualmente, el proyecto cuenta con dos módulos CRUD (Crear, Consultar, Actualizar y Eliminar), conectados a una base de datos MySQL.

## 2. Funcionalidades

### Módulo de proveedores
- Registrar proveedores.
- Consultar proveedores registrados.
- Actualizar información de proveedores.
- Eliminar proveedores.

### Módulo de clientes
- Registrar clientes.
- Consultar clientes registrados.
- Buscar clientes por identificador.
- Actualizar información de clientes.
- Eliminar clientes.

Ambos módulos incluyen validación de datos, mensajes de resultado y protección de la salida dinámica mediante escape de caracteres HTML.

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
| Git y GitHub | Control de versiones y repositorio remoto |

## 4. Arquitectura del proyecto

El proyecto utiliza una arquitectura MVC (Modelo-Vista-Controlador), con separación de responsabilidades entre los modelos, los objetos de acceso a datos, los controladores y las vistas.

```text
gatry-proveedores-web/
│
├── pom.xml
├── .gitignore
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── gatry/
        │           ├── proveedores/
        │           │   ├── Conexion.java
        │           │   ├── Proveedor.java
        │           │   ├── ProveedorDAO.java
        │           │   └── controller/
        │           │       └── ProveedorServlet.java
        │           │
        │           └── clientes/
        │               ├── Cliente.java
        │               ├── ClienteDAO.java
        │               └── controller/
        │                   └── ClienteServlet.java
        │
        └── webapp/
            └── WEB-INF/
                └── views/
                    ├── proveedores.jsp
                    └── clientes.jsp
```

### Responsabilidad de los componentes

- **Conexion.java:** establece la conexión con MySQL.
- **Proveedor.java y Cliente.java:** representan los modelos de datos de cada módulo.
- **ProveedorDAO.java y ClienteDAO.java:** implementan las operaciones de acceso y manipulación de datos mediante JDBC.
- **ProveedorServlet.java y ClienteServlet.java:** reciben y procesan las solicitudes HTTP.
- **proveedores.jsp y clientes.jsp:** presentan los formularios y la información de cada módulo.

## 5. Requisitos del entorno

- JDK 21.
- Apache Maven 3.8.7 o superior.
- Apache Tomcat 10.1.
- MySQL Server.
- Base de datos `gatry_db`.
- Controlador JDBC de MySQL administrado mediante Maven.

## 6. Configuración de la conexión

La aplicación utiliza variables de entorno para conectarse a MySQL:

| Variable | Descripción |
|---|---|
| `GATRY_DB_USER` | Usuario de conexión a MySQL |
| `GATRY_DB_PASSWORD` | Contraseña del usuario de conexión |

Las credenciales deben configurarse en el entorno donde se ejecute Tomcat. No deben almacenarse directamente en el código fuente ni publicarse en el repositorio.

Los módulos utilizan las tablas `Proveedor` y `Cliente` de la base de datos `gatry_db`.

## 7. Construcción del proyecto

Desde el directorio raíz, ejecutar:

```bash
mvn clean package
```

El comando limpia los archivos generados anteriormente, compila el código y construye el archivo WAR.

Artefacto generado:

```text
target/gatry-proveedores-web.war
```

## 8. Despliegue en Apache Tomcat

Copiar el archivo WAR al directorio de aplicaciones:

```bash
sudo cp target/gatry-proveedores-web.war /var/lib/tomcat10/webapps/
```

Reiniciar Tomcat:

```bash
sudo systemctl restart tomcat10
```

Verificar el servicio:

```bash
sudo systemctl status tomcat10 --no-pager
```

## 9. Acceso a los módulos

Una vez desplegada la aplicación:

**Gestión de proveedores:**

```text
http://localhost:8080/gatry-proveedores-web/proveedores
```

**Gestión de clientes:**

```text
http://localhost:8080/gatry-proveedores-web/clientes
```

## 10. Pruebas realizadas

Se verificó la construcción del proyecto mediante Maven y su despliegue en Apache Tomcat.

También se realizaron pruebas funcionales de las operaciones CRUD:

- **Proveedores:** operaciones de registro, consulta, actualización y eliminación.
- **Clientes:** operaciones de registro, consulta, actualización y eliminación.

En el módulo de clientes se comprobó la modificación de información y la eliminación de un registro de prueba.

## 11. Estándares y buenas prácticas

Durante el desarrollo se aplicaron las siguientes prácticas:

- Separación de responsabilidades mediante MVC.
- Uso de clases DAO para las operaciones de persistencia.
- Uso de `PreparedStatement` para ejecutar consultas parametrizadas.
- Administración de recursos JDBC mediante `try-with-resources`.
- Validación de datos recibidos desde los formularios.
- Escape de caracteres HTML para presentar datos dinámicos.
- Uso de variables de entorno para las credenciales de conexión.
- Organización de paquetes y clases por módulo.

## 12. Control de versiones

El proyecto utiliza Git para registrar los cambios y GitHub como repositorio remoto.

Se excluyen del control de versiones los archivos generados durante la construcción, configuraciones locales y archivos que puedan contener credenciales.

Repositorio:

https://github.com/Segari08/gatry-proveedores-web.git

## 13. Información del proyecto

**Proyecto:** Gatry Security & Technology
**Módulos desarrollados:** Gestión de proveedores y gestión de clientes
**Backend:** Java, Jakarta Servlets y JSP
**Persistencia:** MySQL mediante JDBC
**Servidor:** Apache Tomcat 10.1
**Construcción:** Apache Maven
