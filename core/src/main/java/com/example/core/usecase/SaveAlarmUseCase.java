package com.example.core.usecase;

import com.example.core.domain.Alarm;
import com.example.core.ports.AlarmRepository;

/**
 * Caso de uso para guardar una alarma a través del puerto de persistencia.
 */
public class SaveAlarmUseCase {
    private final AlarmRepository repository;

    public SaveAlarmUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public Alarm save(Alarm alarm) {
        return repository.save(alarm);
    }
}
