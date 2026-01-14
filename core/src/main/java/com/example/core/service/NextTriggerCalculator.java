package com.example.core.service;

import com.example.core.domain.Alarm;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Servicio puro que calcula el próximo momento en el que una alarma debe dispararse.
 *
 * Implementación sencilla: compara la hora de la alarma con la hora actual y devuelve
 * hoy o mañana según corresponda. No gestiona repetición compleja (se puede extender).
 */
public class NextTriggerCalculator {

    /**
     * Calcula el próximo LocalDateTime en que la alarma debe dispararse.
     *
     * @param alarm alarma a evaluar
     * @return fecha y hora de la próxima ocurrencia
     */
    public LocalDateTime calculateNextTrigger(Alarm alarm) {
    LocalTime alarmTime = alarm.getBaseTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayAtAlarm = LocalDateTime.of(now.toLocalDate(), alarmTime);
        if (alarm.isEnabled() && !todayAtAlarm.isBefore(now)) {
            return todayAtAlarm;
        }
        // Simple: devolver mañana a la misma hora
        return todayAtAlarm.plusDays(1);
    }
}
