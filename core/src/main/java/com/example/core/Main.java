package com.example.core;

import com.example.core.domain.Alarm;
import com.example.core.domain.AlarmOccurrence;
import com.example.core.service.NextTriggerCalculator;
import com.example.core.service.OccurrenceResolver;
import com.example.core.service.TrackQueueService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de prueba rápida para el módulo core.
 * Crea alarmas ficticias, calcula próxima ejecución y simula cola de reproducción.
 *
 * Esta clase es para uso didáctico y pruebas manuales durante el desarrollo del core.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Crear servicios
        NextTriggerCalculator calculator = new NextTriggerCalculator();
        OccurrenceResolver resolver = new OccurrenceResolver(calculator);
    TrackQueueService queue = new TrackQueueService();

        // Crear alarmas de ejemplo
        Alarm a1 = new Alarm("Despertar", LocalTime.now().plusMinutes(1));
        Alarm a2 = new Alarm("Ejercicio", LocalTime.now().plusHours(1));

    List<Alarm> alarms = Arrays.asList(a1, a2);

        // Calcular próximas ocurrencias y mostrar
        System.out.println("Próximas ocurrencias:");
        for (Alarm a : alarms) {
            AlarmOccurrence occ = resolver.resolve(a);
            System.out.println(occ.getAlarm().getLabel() + " -> " + occ.getWhen());
        }

    // Simular encolado manual usando TrackQueueService y resolver audio básico
    System.out.println("Simulando encolado manual de pista para la primera alarma...");
    // resolver audio base y crear una pista representativa
    System.out.println("Simulando: encolando pista representativa basada en label/ringtone");
    queue.enqueue(a1.getLabel() + "::default_ringtone");
    System.out.println("Cola: " + queue.snapshot());

    // Simular reproducción: sacar siguiente pista
    String next = queue.next();
    System.out.println("Reproduciendo: " + next);

    // Parar alarma (limpiar cola)
    queue.clear();
    System.out.println("Cola tras stop: " + queue.snapshot());
    }
}
