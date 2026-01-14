package com.example.core.service;

import com.example.core.domain.Alarm;
import com.example.core.domain.AlarmOccurrence;

import java.time.LocalDateTime;

/**
 * Servicio que resuelve la próxima ocurrencia a partir de una alarma usando
 * el {@link NextTriggerCalculator}.
 */
public class OccurrenceResolver {
    private final NextTriggerCalculator calculator;

    public OccurrenceResolver(NextTriggerCalculator calculator) {
        this.calculator = calculator;
    }

    public AlarmOccurrence resolve(Alarm alarm) {
        LocalDateTime next = calculator.calculateNextTrigger(alarm);
        return new AlarmOccurrence(alarm, next);
    }
}
