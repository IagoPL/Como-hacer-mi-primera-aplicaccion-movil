package com.example.core.usecase;

import com.example.core.service.TrackQueueService;

/**
 * Caso de uso que detiene una alarma en curso (simulación): limpia la cola de reproducción.
 */
public class StopAlarmUseCase {
    private final TrackQueueService queueService;

    public StopAlarmUseCase(TrackQueueService queueService) {
        this.queueService = queueService;
    }

    public void stop() {
        queueService.clear();
    }
}
