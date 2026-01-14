package com.example.core.ports;

import com.example.core.domain.OccurrenceInstance;

/**
 * Puerto que permite programar o cancelar ocurrencias en el scheduler de la plataforma.
 */
public interface SchedulerPort {
    void scheduleOccurrence(OccurrenceInstance instance);
    void cancel(String alarmId);
}
