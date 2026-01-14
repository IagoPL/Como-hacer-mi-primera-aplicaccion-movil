package com.example.core.usecase;

import com.example.core.domain.Alarm;
import com.example.core.domain.AlarmPlaybackPlan;
import com.example.core.domain.TrackRef;
import com.example.core.service.AudioConfigResolution;
import com.example.core.service.AudioConfigResolver;
import com.example.core.ports.AudioControllerPort;
import com.example.core.ports.MusicProviderPort;

import java.util.List;

/**
 * Caso de uso que se ejecuta cuando una alarma dispara. Construye un {@link AlarmPlaybackPlan}
 * delegando la resolución de audio y la resolución de pistas, y entrega el plan al
 * {@link com.example.core.ports.AudioControllerPort} para que la plataforma lo reproduzca.
 */
public class TriggerFiredUseCase {
    private final MusicProviderPort musicProvider;
    private final AudioConfigResolver audioResolver;
    private final AudioControllerPort audioController;

    public TriggerFiredUseCase(MusicProviderPort musicProvider,
                               AudioConfigResolver audioResolver,
                               AudioControllerPort audioController) {
        this.musicProvider = musicProvider;
        this.audioResolver = audioResolver;
        this.audioController = audioController;
    }

    /**
     * Maneja el evento de disparo de la alarma: resuelve config, resuelve pistas y solicita
     * al controlador de audio iniciar la reproducción.
     */
    public void onTrigger(Alarm alarm) {
        AudioConfigResolution resolved = audioResolver.resolve(alarm);
        if (!resolved.isSuccess()) {
            // Por ahora, no iniciamos reproducción si no hay config válida.
            // En el futuro podríamos emitir un evento de error o usar un default explícito.
            return;
        }

        List<TrackRef> tracks = musicProvider.resolveTracks(alarm, resolved.getConfig());
        AlarmPlaybackPlan plan = new AlarmPlaybackPlan(alarm.getId(), tracks, resolved.getConfig());
        audioController.startPlayback(plan);
    }
}
