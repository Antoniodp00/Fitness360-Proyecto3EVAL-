package com.dam.adp.fitness360proyecto3eval;

public class Prueba {
    public static void main(String[] args) {
       /*  // Probar la conexión y realizar una consulta básica para obtener todos los usuarios
        System.out.println("Obteniendo todos los usuarios...");
        ConsultasBasicas.obtenerUsuarios(); // Consulta para obtener todos los usuarios*/

        // Probar la inserción de un nuevo usuario
        System.out.println("\nInsertando un nuevo usuario...");
        ConsultasBasicas.insertarCliente("Juanito","Juan", "Pérez", "juan.perez@example.com", "password123");

       /* // Probar la actualización de un usuario (asegúrate de que el idUsuario exista)
        System.out.println("\nActualizando correo de un usuario con idUsuario = 1...");
        ConsultasBasicas.actualizarUsuario(1, "juan.perez@nuevoemail.com");*/
       /* // Probar la eliminación de un usuario (asegúrate de que el idUsuario exista)
        System.out.println("\nEliminando usuario con idUsuario = 1...");
        ConsultasBasicas.eliminarUsuario(1);*/
    }
}
