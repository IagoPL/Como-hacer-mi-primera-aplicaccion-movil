package com.example.core.domain;

import java.time.LocalDateTime;

/**
 * Representa una instancia calculada (una ocurrencia concreta) que apunta a la rule que la generó.
 *
 * Esto separa la regla declarativa (OccurrenceRule) de la instancia calculada (fecha/hora concreta)
 * y permite persistir/editar reglas sin confundir con ocurrencias ya programadas.
 */
public class OccurrenceInstance {
    private final String id;
    private final OccurrenceRule rule;
    private final LocalDateTime when;
    private final boolean enabled;

    public OccurrenceInstance(String id, OccurrenceRule rule, LocalDateTime when, boolean enabled) {
        this.id = id;
        this.rule = rule;
        this.when = when;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public OccurrenceRule getRule() {
        return rule;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "OccurrenceInstance{" +
                "id='" + id + '\'' +
                ", rule=" + rule +
                ", when=" + when +
                ", enabled=" + enabled +
                '}';
    }
}
