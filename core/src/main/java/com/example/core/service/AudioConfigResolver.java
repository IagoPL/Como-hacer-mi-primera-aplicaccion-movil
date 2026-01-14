package com.example.core.service;

import com.example.core.domain.Alarm;
import com.example.core.domain.AudioConfig;

/**
 * Servicio que determina la configuración de audio que debe usarse para una alarma.
 * Ahora devuelve un objeto {@link AudioConfigResolution} que indica éxito o fallo.
 */
public class AudioConfigResolver {

    public AudioConfigResolution resolve(Alarm alarm) {
        // Prioridad: rule override > alarm.baseAudioConfig > failure
        if (alarm == null) {
            return AudioConfigResolution.failure(AudioConfigResolution.Error.NO_AUDIO_CONFIG);
        }
        // Si la alarma tiene configuración base, retornarla
        AudioConfig base = alarm.getBaseAudioConfig();
        if (base != null) {
            return AudioConfigResolution.success(base);
        }
        // No hay configuración: devolver fallo explícito
        return AudioConfigResolution.failure(AudioConfigResolution.Error.NO_AUDIO_CONFIG);
    }
}
