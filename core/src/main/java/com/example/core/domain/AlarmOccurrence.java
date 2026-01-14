package com.example.core.domain;

import java.time.LocalDateTime;

/**
 * Representa una ocurrencia concreta de una alarma: la alarma y la fecha/hora en la que ocurrirá.
 */
public class AlarmOccurrence {
    private final Alarm alarm;
    private final LocalDateTime when;

    public AlarmOccurrence(Alarm alarm, LocalDateTime when) {
        this.alarm = alarm;
        this.when = when;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    @Override
    public String toString() {
        return "AlarmOccurrence{" +
                "alarm=" + alarm +
                ", when=" + when +
                '}';
    }
}
