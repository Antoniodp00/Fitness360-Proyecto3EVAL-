

# Fitness360

## Descripción
Fitness360 es una aplicación de escritorio desarrollada en Java con JavaFX, diseñada para la gestión integral de rutinas de entrenamiento físico y dietas personalizadas para usuarios de gimnasios. La aplicación permite a los profesionales del fitness (entrenadores y dietistas) crear, administrar y asignar planes personalizados a sus clientes, mientras que los clientes pueden consultar, crear y seguir sus planes a través de una interfaz intuitiva.

## Características principales
- **Gestión de usuarios**: Registro e inicio de sesión para clientes y profesionales con diferentes niveles de acceso.
- **Creación y gestión de rutinas**: Permite crear, modificar y eliminar rutinas de entrenamiento personalizadas.
- **Gestión de dietas**: Creación y asignación de planes alimenticios adaptados a cada cliente.
- **Seguimiento físico**: Registro y monitorización del progreso físico de los clientes mediante revisiones periódicas.
- **Sistema de tarifas**: Gestión de diferentes planes económicos y suscripciones.
- **Interfaz intuitiva**: Diseño moderno y fácil de usar con sistema de pestañas para organizar la información.

## Tecnologías utilizadas
- **Java 17**: Lenguaje de programación principal (versión LTS).
- **JavaFX**: Framework para la creación de interfaces gráficas.
- **JDBC**: API para la conexión con bases de datos relacionales.
- **MySQL**: Sistema de gestión de bases de datos.
- **Logback**: Sistema de logging para registro de eventos y errores.
- **Maven**: Gestión de dependencias y construcción del proyecto.

## Requisitos previos
- Java JDK 17 o superior
- MySQL Server 8.0 o superior
- Maven

## Instalación
1. **Clonar el repositorio**:
   ```
   git clone https://github.com/tu-usuario/Fitness360-Proyecto3EVAL.git
   cd Fitness360-Proyecto3EVAL
   ```

2. **Configurar la base de datos**:
   - Ejecutar el script SQL ubicado en la carpeta `BBDD` para crear la estructura de tablas.
   - Opcionalmente, ejecutar el script de datos de prueba para poblar la base de datos.

3. **Configurar la conexión**:
   - Editar el archivo `connection.xml` con los parámetros de conexión a tu servidor MySQL.

4. **Compilar y ejecutar**:
   -  Ejecutar directamente desde tu IDE favorito.

## Estructura del proyecto
- **src/main/java/com/dam/adp/fitness360proyecto3eval/**
  - **DAO/**: Clases para acceso a datos (Data Access Objects).
  - **baseDatos/**: Gestión de conexiones a la base de datos.
  - **controller/**: Controladores JavaFX para la interfaz gráfica.
  - **exceptions/**: Excepciones personalizadas.
  - **model/**: Clases de entidad que representan los objetos del dominio.
  - **utilidades/**: Clases auxiliares y utilidades.

- **src/main/resources/com/dam/adp/fitness360proyecto3eval/**
  - **css/**: Hojas de estilo para la interfaz gráfica.
  - **fxml/**: Archivos FXML que definen la estructura de las pantallas.

## Uso
1. Inicia la aplicación.
2. En la pantalla de inicio de sesión, introduce tus credenciales o regístrate como nuevo usuario.
3. Según tu perfil (cliente o profesional), accederás a diferentes funcionalidades:
   - **Profesionales**: Podrán crear y asignar rutinas, dietas y gestionar clientes.
   - **Clientes**: Podrán consultar sus planes asignados, crear sus propias rutinas y ver su progreso.

## Arquitectura
El proyecto sigue el patrón de arquitectura MVC (Modelo-Vista-Controlador):
- **Modelo**: Clases que representan las entidades del sistema y la lógica de negocio.
- **Vista**: Interfaces gráficas definidas mediante archivos FXML.
- **Controlador**: Gestiona la interacción entre el modelo y la vista.

## Documentación
Para más información sobre el proyecto, consulta los siguientes documentos:
- [Documentación completa](Documentacion/Documentacion_Fitness360.md)
- [Backlog del proyecto](Documentacion/Backlog_Fitness360.md)


## Autor
Antonio Delgado Portero

---

© 2025 Fitness360. Todos los derechos reservados.
