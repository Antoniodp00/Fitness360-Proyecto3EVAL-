package com.dam.adp.fitness360proyecto3eval.model;

/**
 * Enumeración que representa las posibles especialidades de los empleados en el sistema.
 * Un empleado puede ser entrenador, dietista o ambos.
 */
public enum Especialidad {
    ENTRENADOR,
    DIETISTA,
    AMBOS;

    @Override
    public String toString() {
        return switch (this) {
            case ENTRENADOR -> "Entrenador Personal";
            case DIETISTA -> "Dietista";
            case AMBOS -> "Entrenador Personal y Dietista";
        };
    }
}
