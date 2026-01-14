package com.example.core.util;

import java.time.LocalDateTime;

/**
 * Utilidades de fecha/hora para el core. Se abstrae la lectura de la hora para facilitar pruebas.
 */
public final class DateUtils {
    private DateUtils() {}

    /**
     * Ahora. En pruebas se puede sustituir o hacer wrapper si se necesita.
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
