package com.dam.adp.fitness360proyecto3eval.exceptions;

/**
 * Excepción personalizada para manejar errores de validación en la aplicación.
 * Esta clase extiende de RuntimeException en lugar de Exception por las siguientes razones:
 * 
 * 1. Las excepciones RuntimeException son "unchecked" (no verificadas):
 *    - No requieren ser declaradas en la firma del método con "throws"
 *    - No obligan a un manejo explícito con try-catch
 *    - Reducen la verbosidad del código
 * 
 * 2. Los errores de validación suelen ser:
 *    - Predecibles y recuperables
 *    - Parte del flujo normal de la aplicación
 *    - Situaciones que el programador debería prever
 * 
 * 3. Si heredara de Exception (checked):
 *    - Obligaría a manejar la excepción en cada punto donde se realice una validación
 *    - Aumentaría la complejidad del código con múltiples bloques try-catch
 *    - Dificultaría la propagación de errores hasta las capas superiores
 */
public class ValidacionException extends RuntimeException {
    
    /**
     * Constructor que crea una nueva excepción de validación con un mensaje específico.
     * 
     * @param mensaje El mensaje que describe el error de validación ocurrido
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}