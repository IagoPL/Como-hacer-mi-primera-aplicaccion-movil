package com.example.core.domain;

/**
 * Referencia a una pista de audio (puede ser URI local o identificador externo).
 */
public class TrackRef {
    private final String uri;

    public TrackRef(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "TrackRef{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
