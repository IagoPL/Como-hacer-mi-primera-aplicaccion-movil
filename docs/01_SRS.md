# SRS – Software Requirements Specification

## Aplicación de despertador multiplataforma en Java

---

## 1. Introducción

### 1.1 Propósito

Este documento especifica de manera formal y completa los requisitos funcionales y no funcionales del sistema **Aplicación de Despertador Multiplataforma en Java**.

El documento está destinado a:

* Estudiantes desarrolladores del proyecto.
* Docentes evaluadores.
* Desarrolladores de la implementación de referencia.

Este documento será la **fuente única de verdad** sobre el comportamiento esperado del sistema.

---

### 1.2 Alcance

El sistema es una aplicación de despertador que permite crear alarmas configurables con múltiples repeticiones y fuentes de audio, funcionando tanto en dispositivos móviles Android como en ordenadores de escritorio, compartiendo la lógica de negocio principal.

---

### 1.3 Definiciones y acrónimos

* **Alarma**: Configuración base que define un comportamiento de despertador.
* **Repetición / Ocurrencia**: Ejecución concreta de una alarma en una fecha u horario específico.
* **Fuente de audio**: Origen de la música reproducida por una alarma.
* **Core**: Módulo de lógica de negocio compartido entre plataformas.
* **Scheduler**: Componente encargado de planificar ejecuciones futuras.

---

## 2. Descripción general del sistema

### 2.1 Perspectiva del producto

El sistema se compone de:

* Un núcleo compartido escrito en Java puro.
* Una aplicación Android nativa.
* Una aplicación de escritorio Java SE.

El sistema **no depende de servicios externos obligatorios** para su funcionamiento básico.

---

### 2.2 Funciones del sistema

* Gestión completa de alarmas.
* Programación temporal automática.
* Reproducción de audio local.
* Integración con servicios externos.
* Persistencia local de datos.
* Ejecución en segundo plano (Android).

---

### 2.3 Restricciones

* Lenguaje: Java.
* Sin frameworks multiplataforma.
* Persistencia local obligatoria.
* Compatibilidad Android y Desktop.

---

## 3. Modelo de datos (definición formal)

### 3.1 Entidad: Alarm

| Campo           | Tipo        | Obligatorio | Descripción         |
| --------------- | ----------- | ----------- | ------------------- |
| id              | long        | Sí          | Identificador único |
| name            | String      | Sí          | Nombre de la alarma |
| enabled         | boolean     | Sí          | Estado de la alarma |
| baseTime        | LocalTime   | Sí          | Hora base           |
| baseAudioConfig | AudioConfig | No          | Configuración base  |
| createdAt       | Instant     | Sí          | Fecha de creación   |

Relación:

* Una alarma **contiene 0..N repeticiones**.

---

### 3.2 Entidad: AlarmOccurrence

| Campo         | Tipo           | Obligatorio | Descripción       |
| ------------- | -------------- | ----------- | ----------------- |
| id            | long           | Sí          | Identificador     |
| alarmId       | long           | Sí          | Alarma asociada   |
| daysOfWeek    | Set<DayOfWeek> | No          | Días de ejecución |
| specificDate  | LocalDate      | No          | Fecha concreta    |
| timeOverride  | LocalTime      | No          | Hora propia       |
| audioOverride | AudioConfig    | No          | Audio propio      |
| enabled       | boolean        | Sí          | Estado            |

Regla:

* Debe existir **daysOfWeek o specificDate**, pero no ambos.

---

### 3.3 Entidad: AudioConfig

| Campo       | Tipo          | Obligatorio |
| ----------- | ------------- | ----------- |
| sourceType  | enum          | Sí          |
| playlistId  | long / String | Sí          |
| shuffle     | boolean       | Sí          |
| allowRepeat | boolean       | Sí          |

---

### 3.4 Entidad: Playlist

| Campo      | Tipo   |
| ---------- | ------ |
| id         | long   |
| name       | String |
| sourceType | enum   |

---

### 3.5 Entidad: Track

| Campo      | Tipo   |
| ---------- | ------ |
| id         | long   |
| uri        | String |
| title      | String |
| durationMs | long   |

---

## 4. Reglas de herencia y prioridad

1. Si una repetición define un valor → **sobrescribe** el de la alarma.
2. Si no define un valor → **hereda** de la alarma.
3. Si ninguno define valor → se considera **configuración inválida**.
4. Cambios en la alarma base afectan solo a repeticiones **sin override**.

---

## 5. Requisitos funcionales

### RF-01 Gestión de alarmas

El sistema deberá permitir crear, modificar, eliminar, activar y desactivar alarmas.

---

### RF-02 Gestión de repeticiones

El sistema deberá permitir asociar múltiples repeticiones a una alarma, cada una con configuración independiente.

---

### RF-03 Algoritmo de planificación

El sistema deberá:

1. Calcular todas las ejecuciones válidas futuras.
2. Programar **solo la ejecución más próxima**.
3. Tras dispararse, calcular y programar la siguiente.

---

### RF-04 Ejecución de alarma

Cuando una alarma se dispare:

1. Se iniciará inmediatamente la reproducción.
2. Se mostrará interfaz de control.
3. La reproducción continuará hasta interacción del usuario.

---

### RF-05 Reproducción local

El sistema deberá reproducir música local respetando:

* Orden
* Aleatoriedad
* Reglas de repetición

---

### RF-06 Integración Spotify

Si la fuente es Spotify:

* Se abrirá el cliente oficial.
* Si no está disponible, la alarma fallará con notificación visible.

---

### RF-07 Integración YouTube

Si la fuente es YouTube:

* Se abrirá la URL configurada.
* No se garantiza reproducción automática.

---

### RF-08 Snooze

Si el usuario pospone:

* Se reprogramará la alarma tras un intervalo fijo.
* No se alterará la programación regular.

---

## 6. Estados del sistema

### 6.1 Estados de una alarma

* INACTIVE
* SCHEDULED
* RINGING
* SNOOZED
* COMPLETED

---

## 7. Persistencia

### 7.1 Datos persistidos

* Alarmas
* Repeticiones
* Playlists
* Configuraciones

### 7.2 Datos no persistidos

* Cola de reproducción activa
* Estado temporal de ejecución

---

## 8. Gestión de errores

| Situación             | Comportamiento              |
| --------------------- | --------------------------- |
| Música no encontrada  | Notificar y cancelar        |
| Spotify no disponible | Notificar y cancelar        |
| Permisos denegados    | Notificar y desactivar      |
| Reinicio del sistema  | Reprogramar automáticamente |

---

## 9. Interfaces del core (contrato)

### AlarmScheduler

* schedule(Occurrence)
* cancel(Occurrence)

### AudioPlayer

* play(AudioConfig)
* stop()

### MusicProvider

* resolveTracks(AudioConfig)

---

## 10. Requisitos no funcionales

* Persistencia garantizada
* Bajo consumo
* Modularidad estricta
* Código documentado

---

## 11. Criterios de aceptación y evaluación

Obligatorio:

* RF-01 a RF-05
* Persistencia
* Core desacoplado

Opcional:

* Spotify
* YouTube
* Snooze avanzado

---

## 12. Resultado esperado

El sistema final deberá ser:

* Funcional
* Comprensible
* Extensible
* Correctamente documentado

---

### ✅ Conclusión

Este SRS:

* Es **cerrado**
* Es **implementable sin interpretación**
* Es **válido como base de referencia**
* Permite que desarrolles **una app modelo completa**
