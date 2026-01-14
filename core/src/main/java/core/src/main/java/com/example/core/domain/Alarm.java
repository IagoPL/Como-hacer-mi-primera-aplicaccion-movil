package com.example.core.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidad que representa una alarma de alto nivel.
 *
 * Cambios respecto a la versión simple:
 * - mantengo una configuración base de audio (`baseAudioConfig`).
 * - una alarma contiene múltiples reglas de ocurrencia (`OccurrenceRule`).
 * - la entidad sigue siendo la raíz de agregación para persistencia.
 */
public class Alarm {
    private final String id;
    private String label;
    private LocalTime baseTime; // hora base si no está override en reglas
    private boolean enabled;
    private AudioConfig baseAudioConfig;
    private final List<OccurrenceRule> rules = new ArrayList<>();

    public Alarm(String label, LocalTime baseTime) {
        this(UUID.randomUUID().toString(), label, baseTime, true, null);
    }

    public Alarm(String id, String label, LocalTime baseTime, boolean enabled, AudioConfig baseAudioConfig) {
        this.id = id;
        this.label = label;
        this.baseTime = baseTime;
        this.enabled = enabled;
        this.baseAudioConfig = baseAudioConfig;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalTime getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(LocalTime baseTime) {
        this.baseTime = baseTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AudioConfig getBaseAudioConfig() {
        return baseAudioConfig;
    }

    public void setBaseAudioConfig(AudioConfig baseAudioConfig) {
        this.baseAudioConfig = baseAudioConfig;
    }

    /**
     * Reglas de ocurrencia asociadas a esta alarma. Cada regla describe cuándo se repite
     * (días de la semana, fecha específica, overrides de hora/audio y enabled por regla).
     */
    public List<OccurrenceRule> getRules() {
        return rules;
    }

    public void addRule(OccurrenceRule rule) {
        this.rules.add(rule);
    }

    public void removeRule(OccurrenceRule rule) {
        this.rules.remove(rule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return Objects.equals(id, alarm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", baseTime=" + baseTime +
                ", enabled=" + enabled +
                ", rules=" + rules +
                '}';
    }
}
