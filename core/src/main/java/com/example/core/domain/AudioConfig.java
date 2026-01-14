package com.example.core.domain;

/**
 * Value object simple que describe configuración de audio para reproducir cuando suene la alarma.
 */
public class AudioConfig {
    private final int volume; // 0-100
    private final String ringtone; // identificador de tono

    public AudioConfig(int volume, String ringtone) {
        if (volume < 0 || volume > 100) throw new IllegalArgumentException("volume must be 0-100");
        this.volume = volume;
        this.ringtone = ringtone;
    }

    public int getVolume() {
        return volume;
    }

    public String getRingtone() {
        return ringtone;
    }

    @Override
    public String toString() {
        return "AudioConfig{" +
                "volume=" + volume +
                ", ringtone='" + ringtone + '\'' +
                '}';
    }
}
