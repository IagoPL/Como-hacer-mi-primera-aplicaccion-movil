package com.example.core.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Servicio que simula una cola de pistas a reproducir para la alarma.
 * Implementación en memoria, adecuada para pruebas y ejemplos didácticos.
 */
public class TrackQueueService {
    private final Deque<String> queue = new ArrayDeque<>();

    /** Añade una pista a la cola. */
    public void enqueue(String trackId) {
        queue.addLast(trackId);
    }

    /** Obtiene y elimina la siguiente pista. */
    public String next() {
        return queue.pollFirst();
    }

    /** Limpia la cola. */
    public void clear() {
        queue.clear();
    }

    /** Tamaño actual de la cola. */
    public int size() {
        return queue.size();
    }

    /** Copia inmutable del estado de la cola para inspección */
    public List<String> snapshot() {
        return Collections.unmodifiableList(new ArrayList<>(queue));
    }
}
