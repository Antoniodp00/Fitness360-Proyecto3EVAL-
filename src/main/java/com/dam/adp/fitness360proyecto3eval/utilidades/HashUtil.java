package com.dam.adp.fitness360proyecto3eval.utilidades;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {
    private static final int COSTO_HASH = 12; // Nivel de seguridad vs rendimiento

    /**
     * Genera un hash seguro para la contraseña proporcionada.
     *
     * @param password La contraseña en texto plano.
     * @return Un string con el hash generado.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COSTO_HASH));
    }

    /**
     * Verifica si la contraseña ingresada coincide con el hash almacenado.
     *
     * @param password La contraseña en texto plano.
     * @param hash El hash almacenado.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public static boolean verificarPassword(String password, String hash) {
        boolean coincide = false;
        if (password != null && hash != null) {
            coincide = BCrypt.checkpw(password, hash);
        }
        return coincide;
    }
}
