package com.example.core;

import com.example.core.domain.Alarm;
import com.example.core.service.NextTriggerCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test simple que comprueba que el cálculo del siguiente trigger devuelve una fecha.
 */
public class NextTriggerCalculatorTest {

    @Test
    public void calculateNextTrigger_notNull() {
        NextTriggerCalculator calc = new NextTriggerCalculator();
        Alarm alarm = new Alarm("Prueba", LocalTime.now().plusMinutes(5));
        assertNotNull(calc.calculateNextTrigger(alarm));
    }
}
package com.example.core;

import com.example.core.domain.Alarm;
import com.example.core.service.NextTriggerCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test simple que comprueba que el cálculo del siguiente trigger devuelve una fecha.
 */
public class NextTriggerCalculatorTest {

    @Test
    public void calculateNextTrigger_notNull() {
        NextTriggerCalculator calc = new NextTriggerCalculator();
        Alarm alarm = new Alarm("Prueba", LocalTime.now().plusMinutes(5));
        assertNotNull(calc.calculateNextTrigger(alarm));
    }
}
