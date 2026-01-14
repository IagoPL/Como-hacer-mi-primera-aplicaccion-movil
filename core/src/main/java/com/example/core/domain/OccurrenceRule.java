package com.example.core.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

/**
 * Regla que describe cómo se repite una alarma.
 *
 * Características mínimas:
 * - días de la semana (por simplicidad usamos java.time.DayOfWeek dentro de un Set)
 * - fecha específica opcional (si está, la regla es para esa fecha)
 * - override de hora opcional
 * - override de audio opcional
 * - enabled por regla
 */
public class OccurrenceRule {
    private final String id;
    private final Set<java.time.DayOfWeek> daysOfWeek; // vacío si no aplica
    private final LocalDate specificDate; // opcional
    private final LocalTime timeOverride; // opcional
    private final AudioConfig audioOverride; // opcional
    private final boolean enabled;

    public OccurrenceRule(String id, Set<java.time.DayOfWeek> daysOfWeek, LocalDate specificDate,
                          LocalTime timeOverride, AudioConfig audioOverride, boolean enabled) {
        this.id = id;
        this.daysOfWeek = daysOfWeek == null ? EnumSet.noneOf(java.time.DayOfWeek.class) : daysOfWeek;
        this.specificDate = specificDate;
        this.timeOverride = timeOverride;
        this.audioOverride = audioOverride;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public Set<java.time.DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalDate getSpecificDate() {
        return specificDate;
    }

    public LocalTime getTimeOverride() {
        return timeOverride;
    }

    public AudioConfig getAudioOverride() {
        return audioOverride;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "OccurrenceRule{" +
                "id='" + id + '\'' +
                ", daysOfWeek=" + daysOfWeek +
                ", specificDate=" + specificDate +
                ", timeOverride=" + timeOverride +
                ", audioOverride=" + audioOverride +
                ", enabled=" + enabled +
                '}';
    }
}
