package com.example.core.ports;

import java.time.LocalDateTime;

/**
 * Puerto para obtener la hora actual. Permite sustituir por simuladores en tests.
 */
public interface ClockPort {
    LocalDateTime now();
}
