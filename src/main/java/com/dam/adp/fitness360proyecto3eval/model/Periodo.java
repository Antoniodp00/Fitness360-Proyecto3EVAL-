package com.dam.adp.fitness360proyecto3eval.model;

/**
 * Enumeración que representa los posibles periodos de tiempo para tarifas o suscripciones.
 * Los periodos pueden ser mensual, trimestral, anual o único (pago único).
 */
public enum Periodo {
    MENSUAL,
    TRIMESTRAL,
    ANUAL,
    UNICO;

    @Override
    public String toString() {
        return switch (this) {
            case MENSUAL -> "Mensual";
            case TRIMESTRAL -> "Trimestral";
            case ANUAL -> "Anual";
            case UNICO -> "Unico";
        };
    }
}
