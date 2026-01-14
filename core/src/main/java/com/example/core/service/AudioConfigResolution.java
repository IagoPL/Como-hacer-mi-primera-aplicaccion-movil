package com.example.core.service;

import com.example.core.domain.AudioConfig;

/**
 * Resultado de la resolución de configuración de audio.
 * Utilizamos un objeto de resultado para poder indicar errores (por ejemplo: NO_AUDIO_CONFIG).
 */
public class AudioConfigResolution {
    public enum Error {
        NONE,
        NO_AUDIO_CONFIG
    }

    private final AudioConfig config;
    private final Error error;

    private AudioConfigResolution(AudioConfig config, Error error) {
        this.config = config;
        this.error = error;
    }

    public static AudioConfigResolution success(AudioConfig config) {
        return new AudioConfigResolution(config, Error.NONE);
    }

    public static AudioConfigResolution failure(Error error) {
        return new AudioConfigResolution(null, error);
    }

    public boolean isSuccess() {
        return error == Error.NONE && config != null;
    }

    public AudioConfig getConfig() {
        return config;
    }

    public Error getError() {
        return error;
    }
}
