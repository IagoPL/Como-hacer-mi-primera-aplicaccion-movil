package com.example.core.ports;

import com.example.core.domain.Alarm;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (interfaz) para persistir Alarm en distintas implementaciones.
 */
public interface AlarmRepository {
    Alarm save(Alarm alarm);
    Optional<Alarm> findById(String id);
    List<Alarm> findAll();
}
