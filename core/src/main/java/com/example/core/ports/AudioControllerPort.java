package com.example.core.ports;

import com.example.core.domain.AlarmPlaybackPlan;

import java.time.Duration;

/**
 * Puerto que abstrae el controlador de audio/plaback de la plataforma.
 */
public interface AudioControllerPort {
    void startPlayback(AlarmPlaybackPlan plan);
    void stopPlayback(String alarmId);
    void snooze(String alarmId, Duration duration);
}
