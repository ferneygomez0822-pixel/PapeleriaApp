📘 INFORME DEL PROYECTO – SISTEMA DE PAPELERÍA
1. Introducción

El presente proyecto consiste en el desarrollo de un Sistema de Información para la gestión de una Papelería, desarrollado en Java, utilizando el patrón arquitectónico MVC (Modelo – Vista – Controlador) e implementando el patrón DAO (Data Access Object) para la comunicación con la base de datos.

El propósito de esta aplicación es optimizar la gestión de productos, clientes, usuarios y facturación dentro de un negocio de papelería, proporcionando herramientas que permitan automatizar y organizar la información para mejorar la eficiencia operativa.

2. Planteamiento del Problema

Las papelerías pequeñas normalmente registran sus ventas, inventario y clientes de manera manual o mediante herramientas básicas como hojas de cálculo, lo que genera:
Pérdida de información.
Errores en cálculos de facturación.
Dificultad para gestionar stock.
Problemas en la organización de productos por categorías.
Procesos lentos y poco confiables.
Por lo anterior, surge la necesidad de desarrollar un sistema que permita gestionar la papelería de forma rápida, segura y eficiente.

3. Justificación del Proyecto

Este sistema se justifica porque:
Permite automatizar tareas repetitivas como registrar productos, clientes o facturas.
Evita errores manuales y mejora la precisión de los datos.
Facilita el control del inventario y la organización por categorías.
Mejora la experiencia del usuario mediante una interfaz clara y amigable.
Utiliza buenas prácticas de desarrollo como MVC y DAO, lo que convierte el sistema en un software escalable y mantenible.

4. Objetivos
4.1 Objetivo General

Desarrollar un sistema de gestión para una papelería que permita controlar productos, clientes, usuarios y facturación mediante una aplicación organizada bajo el modelo MVC.

4.2 Objetivos Específicos

Implementar una base de datos en MySQL para almacenar la información.
Crear módulos independientes para la gestión de productos, categorías, clientes y usuarios.
Diseñar interfaces gráficas amigables utilizando Java Swing.
Desarrollar un módulo de facturación funcional.
Aplicar el patrón DAO para la gestión de datos.
Garantizar una arquitectura limpia y bien organizada.

5. Alcance del Proyecto

El sistema permite:
Gestionar categorías de productos.
Registrar y administrar productos.
Registrar y consultar clientes.
Registrar usuarios del sistema.
Crear facturas y registrar compras.
Manejo de stock por producto.
Visualización y consulta de información en tablas.

No incluye:
Control de acceso por roles avanzados.
Reportes PDF automáticos.
Conexión en red o modo multiusuario.
Control contable completo.

6. Arquitectura del Sistema
   
6.1 Uso de MVC (Modelo – Vista – Controlador)

Se utilizó este patrón porque permite:
Separar la lógica del negocio de la interfaz.
Facilitar mantenibilidad y escalabilidad.
Evitar código mezclado entre vistas y consultas SQL.

Componentes:
Modelo: clases que representan los datos (Producto, Cliente, Usuario…).
Vista: formularios (FrmProductos, FrmClientes, FrmFacturas…).
Controlador/DAO: conecta vistas y modelos con la base de datos.

6.2 Uso de DAO (Data Access Object)

El patrón DAO permite manejar la base de datos con clases especializadas.

Cada entidad tiene su propio DAO:
CategoriaDAO
ProductoDAO
ClienteDAO
UsuarioDAO
FacturaDAO

Cada DAO incluye:
Insertar
Actualizar
Eliminar
Listar
Buscar
Esto evita mezclar SQL con la interfaz y hace que el código sea más profesional.

7. Tecnologías Utilizadas
   
| Tecnología              | Uso                         |
| ----------------------- | --------------------------- |
| **Java SE**             | Desarrollo de la aplicación |
| **Java Swing**          | Interfaz gráfica            |
| **MySQL**               | Base de datos               |
| **JDBC**                | Conexión entre Java y MySQL |
| **NetBeans / IntelliJ** | Desarrollo del proyecto     |
| **MVC + DAO**           | Arquitectura del sistema    |

8. Estructura del Proyecto

El proyecto está organizado en paquetes:

8.1 Paquete papeleria.model (Modelos)

Clases del modelo:
Categoria.java
Producto.java
Cliente.java
Factura.java
FacturaItem.java
Usuario.java
Representan los datos reales de la papelería.

8.2 Paquete papeleria.dao (Acceso a Datos)

Incluye interfaces e implementaciones:
CategoriaDAO.java
CategoriaDAOImpl.java
ProductoDAO.java
ProductoDAOImpl.java
ClienteDAO.java
ClienteDAOImpl.java
FacturaDAO.java
FacturaDAOImpl.java
UsuarioDAO.java
Cada clase se comunica con la base de datos mediante consultas SQL.

8.3 Paquete papeleria.database

Contiene:

DBConnection.java
Su función es:
Establecer conexión con MySQL.
Manejar credenciales y parámetros.
Devolver un objeto Connection reutilizable.

8.4 Paquete papeleria.ui (User Interface)

Formularios:
FrmClientes.java
FrmProductos.java
FrmFacturas.java
FrmUsuarios.java
FrmAcercaDe.java
Principal.java
Permiten gestionar los datos a través de una interfaz amigable

9. Funcionalidades Principales
    
✔ Gestión de Categorías

Crear, editar y eliminar categorías.
Asociar categorías a productos.

✔ Gestión de Productos

Registrar productos con nombre, precio, stock y categoría.
Actualizar stock automáticamente.

✔ Gestión de Clientes

Registrar datos personales.
Consultar y editar información.

✔ Gestión de Usuarios

Usuarios del sistema con credenciales.

✔ Facturación

Crear facturas con múltiples productos.
Cálculo automático del total.
Registro de fecha, cliente y productos.

10. Resultados Esperados

El sistema permitirá:
Mayor orden y rapidez en la gestión de la papelería.
Reducción de errores manuales.
Inventario más controlado.
Mejor organización de información.
Gestión sencilla mediante interfaz gráfica

11. Conclusión

El proyecto cumple con el objetivo de implementar un sistema eficiente para la gestión de una papelería, demostrando conocimientos sólidos en Java, patrones de diseño, manejo de base de datos y diseño de interfaces.
Gracias a la implementación de MVC y DAO, el código del sistema es escalable, fácil de mantener y profesional.
Este sistema representa una solución funcional para pequeñas empresas que requieren mejorar la administración de inventario, clientes y facturación sin depender de software costoso.




Es una solución ideal para pequeñas empresas que no cuentan con sistemas complejos o costosos.
