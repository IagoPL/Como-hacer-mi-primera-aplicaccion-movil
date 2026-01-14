package com.example.core.usecase;

import com.example.core.domain.OccurrenceInstance;
import com.example.core.ports.AudioControllerPort;
import com.example.core.ports.SchedulerPort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso para posponer (snooze) una alarma. No altera las reglas originales,
 * simplemente programa una nueva ocurrencia temporal y notifica al controlador de audio.
 */
public class SnoozeUseCase {
    private final SchedulerPort scheduler;
    private final AudioControllerPort audioController;

    public SnoozeUseCase(SchedulerPort scheduler, AudioControllerPort audioController) {
        this.scheduler = scheduler;
        this.audioController = audioController;
    }

    /**
     * Posponer la alarma identificada por alarmId durante la duración indicada.
     */
    public void snooze(String alarmId, Duration duration) {
        // notificar al controlador (por ejemplo para bajar y reprogramar UI)
        audioController.snooze(alarmId, duration);
        // crear una instancia de ocurrencia para scheduling: ahora + duration
        LocalDateTime when = LocalDateTime.now().plus(duration);
        OccurrenceInstance instance = new OccurrenceInstance(UUID.randomUUID().toString(), null, when, true);
        scheduler.scheduleOccurrence(instance);
    }
}
