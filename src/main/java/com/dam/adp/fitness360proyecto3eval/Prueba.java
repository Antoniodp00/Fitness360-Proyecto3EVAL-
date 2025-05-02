package com.dam.adp.fitness360proyecto3eval;

import com.dam.adp.fitness360proyecto3eval.DAO.DietaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.RutinaDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioClienteDAO;
import com.dam.adp.fitness360proyecto3eval.DAO.UsuarioEmpleadoDAO;
import com.dam.adp.fitness360proyecto3eval.model.*;

import java.util.Date;
import java.util.List;

public class Prueba {
    public static void main(String[] args) {
       /*  // Probar la conexión y realizar una consulta básica para obtener todos los usuarios
        System.out.println("Obteniendo todos los usuarios...");
        ConsultasBasicas.obtenerUsuarios(); // Consulta para obtener todos los usuarios*/

        // Probar la inserción de un nuevo usuario
      /*System.out.println("\nInsertando un nuevo usuario...");
        ConsultasBasicas.insertarCliente("Juanito","Juan", "Pérez", "juan.perez@example.com", "password123");*/

       /* // Probar la actualización de un usuario (asegúrate de que el idUsuario exista)
        System.out.println("\nActualizando correo de un usuario con idUsuario = 1...");
        ConsultasBasicas.actualizarUsuario(1, "juan.perez@nuevoemail.com");*/
       /* // Probar la eliminación de un usuario (asegúrate de que el idUsuario exista)
        System.out.println("\nEliminando usuario con idUsuario = 1...");
        ConsultasBasicas.eliminarUsuario(

        //PRUEBAS CLIENTEDAO
                // Test 1: Probar la inserción de un cliente
        String nombreUsuario = "testUser";
        String nombre = "Test";
        String apellidos = "Usuario";
        String correo = "test@correo.com";
        String password = "password123";
        String telefono = "123456789";
        Date fechaNacimiento = new Date();  // Fecha actual
        Sexo sexo = Sexo.M;  // Suponiendo que tienes un enum Sexo con MASCULINO, FEMENINO, etc.
        Estado estado = Estado.ACTIVO;  // Suponiendo que tienes un enum Estado con ACTIVO, INACTIVO, etc.
        double altura = 180.0;  // Altura en centímetros
        Date createdAt = new Date();  // Fecha actual de creación
        Date updatedAt = new Date();  // Fecha actual de actualización

        // Crear el cliente
        UsuarioCliente nuevoCliente = new UsuarioCliente(
                nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, estado, altura, createdAt, updatedAt
        );

                // Insertar cliente
                UsuarioCliente insertado = UsuarioClienteDAO.insert(nuevoCliente);
                if (insertado != null) {
                    System.out.println("Cliente insertado con éxito: " + insertado.getNombreUsuario());
                } else {
                    System.out.println("Error al insertar el cliente.");
                }

                // Test 2: Buscar cliente por ID
                UsuarioCliente cliente = UsuarioClienteDAO.findById(4);
                if (cliente != null) {
                    System.out.println("Cliente encontrado: " + cliente.getNombre() + " " + cliente.getApellidos());
                } else {
                    System.out.println("Cliente no encontrado.");
                }

                // Test 3: Buscar cliente por nombre de usuario
                UsuarioCliente clientePorNombre = UsuarioClienteDAO.finByUserName("testUser");
                if (clientePorNombre != null) {
                    System.out.println("Cliente encontrado por nombre de usuario: " + clientePorNombre.getNombreUsuario());
                } else {
                    System.out.println("Cliente no encontrado por nombre de usuario.");
                }

                // Test 4: Listar todos los clientes
                List<UsuarioCliente> clientes = UsuarioClienteDAO.getAll();
                System.out.println("Listado de clientes:");
                for (UsuarioCliente c : clientes) {
                    System.out.println(c.getNombreUsuario() + " - " + c.getNombre() + " " + c.getApellidos());
                }

                // Test 5: Deshabilitar un cliente
                boolean deshabilitado = UsuarioClienteDAO.disableUsuarioCliente(4);
                if (deshabilitado) {
                    System.out.println("Cliente deshabilitado con éxito.");
                } else {
                    System.out.println("Error al deshabilitar el cliente.");
                }

                // Test 6: Actualizar cliente
                cliente.setNombre("NuevoNombre");
                boolean actualizado = UsuarioClienteDAO.update(cliente);
                if (actualizado) {
                    System.out.println("Cliente actualizado con éxito.");
                } else {
                    System.out.println("Error al actualizar el cliente.");
                }*/

        //EmpleadoDAO
        // Crear un empleado para probar el método insert
        /*UsuarioEmpleado nuevoEmpleado = new UsuarioEmpleado(
                "empleadoTest",  // nombreUsuario
                "Juan",          // nombre
                "Pérez",         // apellidos
                "juan.perez@correo.com", // correo
                "password123",   // password
                "987654321",     // telefono
                new Date(),      // fechaNacimiento
                Sexo.M,  // sexo
                Estado.ACTIVO,   // estado
                new Date(),      // createdAt
                new Date(),      // updatedAt
                "Empleado de prueba",  // descripcion
                "Administrador", // rol
                Especialidad.ENTRENADOR // especialidad
        );

        // Probar insertar el empleado
        UsuarioEmpleado insertado = UsuarioEmpleadoDAO.insert(nuevoEmpleado);
        if (insertado != null) {
            System.out.println("Empleado insertado: " + insertado.getNombreUsuario());
        } else {
            System.out.println("No se pudo insertar el empleado.");
        }

        // Probar buscar un empleado por su ID
        int idEmpleado = 1; // Cambiar por el ID real del empleado insertado
        UsuarioEmpleado encontrado = UsuarioEmpleadoDAO.findById(idEmpleado);
        if (encontrado != null) {
            System.out.println("Empleado encontrado por ID: " + encontrado.getNombreUsuario());
        } else {
            System.out.println("Empleado no encontrado por ID.");
        }

        // Probar buscar un empleado por nombre de usuario
        String nombreUsuario = "empleadoTest";
        UsuarioEmpleado porUsuario = UsuarioEmpleadoDAO.finByUserName(nombreUsuario);
        if (porUsuario != null) {
            System.out.println("Empleado encontrado por nombre de usuario: " + porUsuario.getNombreUsuario());
        } else {
            System.out.println("Empleado no encontrado por nombre de usuario.");
        }

        // Probar obtener todos los empleados
        List<UsuarioEmpleado> empleados = UsuarioEmpleadoDAO.getAll();
        System.out.println("Empleados registrados:");
        for (UsuarioEmpleado emp : empleados) {
            System.out.println(emp.getNombreUsuario() + " - " + emp.getNombre() + " " + emp.getApellidos());
        }

        // Probar actualización de un empleado
        if (encontrado != null) {
            encontrado.setCorreo("nuevo.email@correo.com");
            boolean actualizado = UsuarioEmpleadoDAO.update(encontrado);
            if (actualizado) {
                System.out.println("Empleado actualizado: " + encontrado.getNombreUsuario());
            } else {
                System.out.println("No se pudo actualizar el empleado.");
            }
        }
        Probar desactivar un empleado
        UsuarioEmpleado encontrado = UsuarioEmpleadoDAO.findById(1);
        if (encontrado != null) {
            boolean desactivado = UsuarioEmpleadoDAO.disableUsuarioEmpleado(1);
            if (desactivado) {
                System.out.println("Empleado desactivado: " + encontrado.getNombreUsuario());
            } else {
                System.out.println("No se pudo desactivar el empleado.");
            }
        }

       /* UsuarioCliente cliente = UsuarioClienteDAO.findByIdEager(4);
        List<ClienteRutina> rutinas = cliente.getRutinasAsignadas();
        if (rutinas != null && !rutinas.isEmpty()) {
            System.out.println("Rutinas asignadas:");
            for (ClienteRutina cr : rutinas) {
                Rutina rutina = cr.getRutina();
                System.out.println("- " + rutina.getIdRutina() + ": " + rutina.getNombre());
                System.out.println("  Asignada desde " + cr.getFechaAsignacion() + " hasta " + cr.getFechaFin());
            }
        } else {
            System.out.println("No tiene rutinas asignadas.");
        }*/

/*
        UsuarioEmpleado empleado = UsuarioEmpleadoDAO.findByIdEager(1);
        List<Rutina> rutinas = empleado.getRutinasCreadas();
        if (rutinas != null && !rutinas.isEmpty()) {
            System.out.println("Rutinas asignadas:");
            for (Rutina r : rutinas) {
                System.out.println("- " + r.getIdRutina() + ": " + r.getNombre());
                System.out.println("  Asignada desde " + r.getCreatedAt());
            }
        } else {
            System.out.println("No tiene rutinas asignadas.");
        }*/


      /*  Rutina rutina = RutinaDAO.getByIdEager(9);
        List<ClienteRutina> clienteRutinas = rutina.getClientesAsignados();
        if (clienteRutinas!= null && !clienteRutinas.isEmpty() ) {
            System.out.println("Clientes con la rutina asignada:");
            for (ClienteRutina uC : clienteRutinas){
                UsuarioCliente cliente = uC.getCliente();
                System.out.println("- " + cliente.getId() + ": " + cliente.getNombreUsuario());
                System.out.println("  Asignada desde " + uC.getFechaAsignacion() + " hasta " + uC.getFechaFin());
            }
        }*/

        // Asegúrate de que este ID exista en tu base de datos
       /* UsuarioEmpleado creador = UsuarioEmpleadoDAO.findById(1);

        if (creador == null) {
            System.out.println("No se encontró un empleado con ID 1. Asegúrate de tener uno en la base de datos.");
            return;
        }

        // Crear una nueva dieta
        Dieta nuevaDieta = new Dieta();
        nuevaDieta.setNombre("Dieta Test");
        nuevaDieta.setDescripcion("Una dieta de prueba para testeo.");
        nuevaDieta.setArchivo("dieta_test.pdf");
        nuevaDieta.setCreador(creador);

        // Insertar dieta
        Dieta dietaInsertada = DietaDAO.insertDieta(nuevaDieta);
        System.out.println("Dieta insertada: " + dietaInsertada);
*/

        // Buscar por nombre
        Dieta encontrada = DietaDAO.getByName("Dieta Test");
        System.out.println("Dieta encontrada por nombre: " + encontrada);

        // Mostrar todas las dietas
        List<Dieta> todas = DietaDAO.getAll();
        System.out.println("Lista de todas las dietas:");
        for (Dieta d : todas) {
            System.out.println(d);
        }

        // Actualizar dieta
        if (encontrada != null) {
            encontrada.setDescripcion("Descripción actualizada.");
            DietaDAO.updateDieta(encontrada);
            System.out.println("Dieta actualizada: " + DietaDAO.getById(encontrada.getIdDieta()));
        }

        // Eliminar dieta
        if (encontrada != null) {
            boolean eliminada = DietaDAO.deleteDieta(encontrada);
            System.out.println("¿Dieta eliminada? " + eliminada);
        }
/*

                // Crear un cliente ficticio con ID 1 (debes asegurarte de que exista en la base de datos)
                UsuarioCliente cliente = new UsuarioCliente();
                cliente.setId(1); // ID real existente en la tabla UsuarioCliente

                // Crear una rutina
                Rutina rutina = new Rutina();
                rutina.setNombre("Rutina de prueba");
                rutina.setDescripcion("Rutina creada desde el main para prueba");
                rutina.setCreadorCliente(cliente);

                // Insertar rutina como cliente
                RutinaDAO.insertRutinaByClient(rutina);
                System.out.println("Rutina insertada correctamente.");

                // Recuperar rutina (este método no devuelve el ID insertado automáticamente, así que debes adaptarlo)
                Rutina rutinaEncontrada = RutinaDAO.getAll().stream()
                        .filter(r -> r.getNombre().equals("Rutina de prueba"))
                        .findFirst().orElse(null);

                if (rutinaEncontrada != null) {
                    System.out.println("Rutina encontrada: " + rutinaEncontrada.getNombre());

                    // Modificar descripción
                    rutinaEncontrada.setDescripcion("Descripción actualizada desde el main");

                    // Asignar un empleado ficticio (suponiendo ID 2)
                    UsuarioEmpleado empleado = new UsuarioEmpleado();
                    empleado.setId(2); // Asegúrate de que este ID existe
                    rutinaEncontrada.setCreadorEmpleado(empleado);

                    // También debe tener cliente asignado para evitar null
                    rutinaEncontrada.setCreadorCliente(cliente);

                    // Actualizar rutina
                    boolean actualizada = RutinaDAO.updateRutina(rutinaEncontrada);
                    System.out.println("¿Rutina actualizada? " + actualizada);

                    // Eliminar rutina
                    boolean eliminada = RutinaDAO.deleteRutina(rutinaEncontrada);
                    System.out.println("¿Rutina eliminada? " + eliminada);
                } else {
                    System.out.println("No se encontró la rutina para actualizar o eliminar.");
                }*/
    }
}