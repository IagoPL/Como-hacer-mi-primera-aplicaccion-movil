package com.example.core.ports;

import com.example.core.domain.TrackRef;

import java.util.List;

/**
 * Repositorio para recuperar playlists o listas de pistas guardadas.
 */
public interface PlaylistRepositoryPort {
    List<TrackRef> getPlaylist(String playlistId);
}
