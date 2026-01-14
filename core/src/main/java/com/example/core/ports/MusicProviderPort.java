package com.example.core.ports;

import com.example.core.domain.Alarm;
import com.example.core.domain.AudioConfig;
import com.example.core.domain.TrackRef;

import java.util.List;

/**
 * Puerto para resolver pistas a partir de una alarma y su configuración de audio.
 */
public interface MusicProviderPort {
    List<TrackRef> resolveTracks(Alarm alarm, AudioConfig config);
}
