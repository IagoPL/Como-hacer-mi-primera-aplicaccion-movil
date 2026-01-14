package com.example.core.domain;

import java.util.List;

/**
 * Plan que describe qué reproducir cuando una alarma dispara.
 * Contiene lista de pistas resueltas, audio config y metadatos mínimos.
 */
public class AlarmPlaybackPlan {
    private final String alarmId;
    private final List<TrackRef> tracks;
    private final AudioConfig audioConfig;

    public AlarmPlaybackPlan(String alarmId, List<TrackRef> tracks, AudioConfig audioConfig) {
        this.alarmId = alarmId;
        this.tracks = tracks;
        this.audioConfig = audioConfig;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public List<TrackRef> getTracks() {
        return tracks;
    }

    public AudioConfig getAudioConfig() {
        return audioConfig;
    }

    @Override
    public String toString() {
        return "AlarmPlaybackPlan{" +
                "alarmId='" + alarmId + '\'' +
                ", tracks=" + tracks +
                ", audioConfig=" + audioConfig +
                '}';
    }
}
