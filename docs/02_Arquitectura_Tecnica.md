# 02_Arquitectura_Tecnica.md
## Arquitectura técnica detallada — Despertador multiplataforma (Android + Desktop) en Java

**Versión:** 1.0  
**Estado:** Base de referencia (implementación ejemplo)  
**Relacionada con:** `01_SRS.md` (este documento define el “CÓMO”; el SRS define el “QUÉ”)  

---

## 1. Objetivo de la arquitectura

Definir una arquitectura técnica que permita:
- Implementar el sistema descrito en `01_SRS.md`.
- Compartir lógica de negocio entre Android y Desktop (Java SE).
- Aislar dependencias de plataforma (Android SDK / Swing / audio / scheduler).
- Mantener el código evaluable, modular y mantenible en un contexto docente.

---

## 2. Principios y decisiones clave

### 2.1 Principios
1. **Separación estricta de responsabilidades**: UI ≠ lógica ≠ persistencia ≠ integración SO.
2. **Core independiente de plataforma**: el módulo core no importa `android.*` ni `javax.swing.*`.
3. **Ports & Adapters**: el core define interfaces (ports) y cada plataforma implementa adaptadores.
4. **Programación del “siguiente disparo”**: el sistema programa únicamente el trigger más cercano.
5. **Persistencia local obligatoria**: SQLite en ambas plataformas.

### 2.2 Restricciones técnicas (por contexto del curso)
- Lenguaje: **Java**
- Android: **SDK nativo** (sin frameworks de arquitectura).
- Desktop: **Java SE + Swing** (compatibilidad amplia).
- Persistencia: **SQLite** (Android con `SQLiteOpenHelper`, Desktop con JDBC).
- Audio local: Android con `MediaPlayer`. Desktop con `Clip` (WAV) y extensión opcional a MP3.

---

## 3. Estructura de repositorio y módulos (Gradle multi-módulo)

Estructura recomendada:

```

root/
core/
androidApp/
desktopApp/
docs/

```

### 3.1 Módulo `core` (Java puro)
Responsabilidad: modelos de dominio, reglas de negocio, casos de uso y contratos (ports).

**Prohibido en `core`:**
- Dependencias Android (`android.*`)
- Dependencias UI de escritorio (`javax.swing.*`)
- Dependencias directas a SQLiteOpenHelper, AlarmManager, MediaPlayer, etc.

### 3.2 Módulo `androidApp`
Responsabilidad: UI Android + adaptadores (DB, scheduler, audio, providers) + servicios Android.

### 3.3 Módulo `desktopApp`
Responsabilidad: UI Swing + adaptadores (DB, scheduler, audio, providers).

---

## 4. Capas y dependencias

### 4.1 Core (capas internas)
- **domain**: entidades, value objects, invariantes.
- **usecase**: casos de uso (orquestación y coordinación).
- **service**: servicios puros (cálculo de disparos, colas).
- **ports**: interfaces hacia el exterior (repos, scheduler, audio, providers, clock).
- **util**: Result/Errors, helpers.

### 4.2 Plataformas (capas)
- **ui**: Activities/Fragments (Android) o Frames/Dialogs (Desktop).
- **adapters**: implementaciones de ports (DB, scheduler, audio, music providers).
- **infra**: recursos, configuración, utilidades de plataforma.

### 4.3 Regla de dependencias
- `androidApp` → depende de `core`
- `desktopApp` → depende de `core`
- `core` → no depende de ninguna plataforma

---

## 5. Paquetes y clases recomendadas

### 5.1 `core` — paquetes
```

com.yourorg.alarmclock.core
domain/
Alarm.java
AlarmOccurrence.java
AudioConfig.java
Playlist.java
Track.java
enums/
ports/
ClockPort.java
AlarmRepositoryPort.java
PlaylistRepositoryPort.java
SchedulerPort.java
AudioControllerPort.java
MusicProviderPort.java
service/
NextTriggerCalculator.java
OccurrenceResolver.java
AudioConfigResolver.java
TrackQueueService.java
usecase/
SaveAlarmUseCase.java
DeleteAlarmUseCase.java
ToggleAlarmUseCase.java
TriggerFiredUseCase.java
StopAlarmUseCase.java
SnoozeUseCase.java
RecalculateScheduleUseCase.java
util/
Result.java
AppError.java

```

### 5.2 `androidApp` — paquetes
```

com.yourorg.alarmclock.android
ui/
MainActivity.java
AlarmEditActivity.java
OccurrenceActivity.java
MusicPickerActivity.java
RingingActivity.java
adapters/
db/
AndroidDbHelper.java
AndroidAlarmRepository.java
AndroidPlaylistRepository.java
scheduler/
AndroidSchedulerAdapter.java
AlarmReceiver.java
BootReceiver.java
audio/
AlarmSoundService.java
AndroidAudioController.java
provider/
LocalMusicProviderAndroid.java
SpotifyProviderAndroid.java
YouTubeProviderAndroid.java
di/ (manual)
AppContainer.java

```

### 5.3 `desktopApp` — paquetes
```

com.yourorg.alarmclock.desktop
ui/
MainFrame.java
AlarmEditorDialog.java
OccurrencePanel.java
MusicPickerDialog.java
RingingDialog.java
adapters/
db/
DesktopDb.java
DesktopAlarmRepository.java
DesktopPlaylistRepository.java
scheduler/
DesktopSchedulerAdapter.java
audio/
DesktopAudioController.java
provider/
LocalMusicProviderDesktop.java
SpotifyProviderDesktop.java
YouTubeProviderDesktop.java
di/
AppContainer.java

````

---

## 6. Contratos del core (Ports) — especificación técnica

### 6.1 `ClockPort`
Motivo: testabilidad y consistencia en cálculos temporales.
```java
public interface ClockPort {
    java.time.Instant now();
    java.time.ZoneId zoneId();
}
````

### 6.2 `AlarmRepositoryPort`

```java
public interface AlarmRepositoryPort {
    Alarm getAlarm(long alarmId);
    java.util.List<Alarm> listAlarms();

    long insertAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);
    void deleteAlarm(long alarmId);

    java.util.List<AlarmOccurrence> listOccurrences(long alarmId);
    long insertOccurrence(AlarmOccurrence occurrence);
    void updateOccurrence(AlarmOccurrence occurrence);
    void deleteOccurrence(long occurrenceId);
}
```

### 6.3 `PlaylistRepositoryPort`

Separa “playlists” y “tracks” para soportar local y externo.

```java
public interface PlaylistRepositoryPort {
    Playlist getPlaylist(String playlistKey);
    void savePlaylist(Playlist playlist);

    java.util.List<Track> listTracks(String playlistKey);
    void replaceTracks(String playlistKey, java.util.List<Track> tracks);
}
```

### 6.4 `SchedulerPort`

El core calcula el próximo disparo y pide programarlo.

```java
public interface SchedulerPort {
    void scheduleNext(ScheduledTrigger trigger);
    void cancelTrigger(String triggerId);
}
```

**`ScheduledTrigger` (core/domain o core/service):**

* `String triggerId` (único, derivado de occurrenceId)
* `long alarmId`
* `long occurrenceId`
* `Instant fireAt`

### 6.5 `MusicProviderPort`

Resuelve una configuración de audio en:

* Lista de tracks locales, o
* Acción externa (abrir app/URL)

```java
public interface MusicProviderPort {
    MusicResolution resolve(AudioConfig config);
}
```

**`MusicResolution`:**

* `enum Type { LOCAL_TRACKS, EXTERNAL_ACTION }`
* `List<TrackRef> tracks` (si local)
* `ExternalAction action` (si externo)

### 6.6 `AudioControllerPort`

```java
public interface AudioControllerPort {
    void startAlarmPlayback(AlarmPlaybackPlan plan);
    void stopPlayback();
    void snooze(long minutes);
    boolean isPlaying();
}
```

**`AlarmPlaybackPlan`:**

* `AlarmMetadata metadata` (nombre alarma, occurrenceId…)
* `MusicResolution resolution`
* flags: `shuffle`, `allowRepeat`
* opciones opcionales: `volume`, `fadeInMs`

---

## 7. Servicios puros (core/service)

### 7.1 `OccurrenceResolver`

Responsabilidad:

* Validar ocurrencias.
* Determinar si ocurrencia aplica a una fecha.
* Resolver la hora final (`timeOverride` o `alarm.baseTime`).

### 7.2 `AudioConfigResolver`

Responsabilidad:

* Aplicar reglas de herencia y prioridad (SRS).
* Producir un `AudioConfig` final válido o error.

### 7.3 `TrackQueueService`

Responsabilidad:

* Construir y administrar la cola (shuffle/no repeats/allow repeats).

**Reglas implementables:**

* Si `allowRepeat=false` y no quedan tracks → se devuelve “fin de cola”.
* Si `allowRepeat=true` y se agota → se vuelve a rellenar (con shuffle si aplica).

### 7.4 `NextTriggerCalculator`

Responsabilidad:

* Calcular el `ScheduledTrigger` más próximo entre todas las alarmas/ocurrencias habilitadas.

Salida:

* `Optional<ScheduledTrigger>`.

Reglas:

* Solo disparos futuros (`fireAt > now`).
* Si hay empate, prioridad por `fireAt` y luego por `occurrenceId` ascendente.

---

## 8. Casos de uso (core/usecase)

### 8.1 `SaveAlarmUseCase`

* Valida y guarda alarma + ocurrencias.
* Llama a `RecalculateScheduleUseCase`.

### 8.2 `ToggleAlarmUseCase`

* Activa/desactiva una alarma.
* Recalcula el siguiente disparo global.

### 8.3 `TriggerFiredUseCase`

Entrada: `occurrenceId`

* Carga alarma + ocurrencia.
* Resuelve `AudioConfig` final.
* Resuelve `MusicResolution` con `MusicProviderPort`.
* Construye `AlarmPlaybackPlan`.
* Inicia reproducción con `AudioControllerPort`.
* Solicita reprogramación del siguiente disparo (si el SRS lo exige tras el evento “STOP/SNOOZE”, se deja a Stop/Snooze).

### 8.4 `StopAlarmUseCase`

* Detiene audio.
* Marca estado interno si aplica.
* Recalcula el siguiente disparo global y programa.

### 8.5 `SnoozeUseCase`

* Detiene audio.
* Programa disparo de snooze (intervalo fijo).
* Mantiene programación regular intacta (SRS).

---

## 9. Flujo end-to-end (secuencia técnica)

### 9.1 Crear/editar alarma (común)

1. UI recoge datos.
2. UI ejecuta `SaveAlarmUseCase`.
3. UseCase guarda en repos.
4. UseCase ejecuta `RecalculateScheduleUseCase`.
5. Scheduler programa `scheduleNext(trigger)`.

### 9.2 Suena la alarma (Android)

1. `AlarmManager` dispara `AlarmReceiver`.
2. `AlarmReceiver` arranca `AlarmSoundService` (foreground).
3. Service ejecuta `TriggerFiredUseCase(occurrenceId)`.
4. Se reproduce audio / se abre acción externa.
5. Usuario pulsa STOP → `StopAlarmUseCase` → reprograma siguiente.

### 9.3 Suena la alarma (Desktop)

1. `DesktopSchedulerAdapter` lanza tarea.
2. Ejecuta `TriggerFiredUseCase(occurrenceId)`.
3. Muestra `RingingDialog`.
4. STOP → `StopAlarmUseCase` → reprograma siguiente.

---

## 10. Arquitectura Android (detallada)

### 10.1 Scheduling: `AndroidSchedulerAdapter`

* Implementa `SchedulerPort`.
* Usa `AlarmManager.setExactAndAllowWhileIdle(...)`.
* `PendingIntent` por `occurrenceId`.

### 10.2 Receivers

* `AlarmReceiver`: recibe disparo y arranca el service.
* `BootReceiver`: en `BOOT_COMPLETED` reprograma la siguiente alarma.
* (Opcional recomendado) `TimeChangeReceiver`: `TIME_CHANGED`, `TIMEZONE_CHANGED`.

### 10.3 Foreground Service: `AlarmSoundService`

Responsabilidad:

* Mantener ejecución mientras suena la alarma.
* Mostrar notificación persistente con acciones:

  * STOP
  * SNOOZE

### 10.4 Audio: `AndroidAudioController`

* Usa `MediaPlayer` con `AudioAttributes.USAGE_ALARM`.
* Maneja final de canción → pide siguiente track a `TrackQueueService`.

### 10.5 Selección música local

* Preferente: **Storage Access Framework (SAF)** para carpetas/archivos.
* Mantener URIs persistentes (`takePersistableUriPermission`) cuando aplique.
* Alternativa según versión: MediaStore para listar música.

---

## 11. Arquitectura Desktop (detallada)

### 11.1 Scheduling: `DesktopSchedulerAdapter`

* Implementa `SchedulerPort`.
* Usa `ScheduledExecutorService` single-thread.
* Cancela y reprograma tarea al cambiar `scheduleNext`.

### 11.2 Audio: `DesktopAudioController`

* Base: `javax.sound.sampled.Clip` para WAV.
* Extensión opcional MP3:

  * Dependencia externa permitida solo para audio, documentada.
* Misma lógica de cola que en Android (desde core).

### 11.3 Selección música local

* `JFileChooser` con:

  * selección múltiple de archivos
  * selección de directorios (si se habilita)
* Escaneo de carpeta para tracks soportados.

---

## 12. Persistencia (diseño técnico)

### 12.1 Estrategia

* SQLite con esquema compartido.
* Repositorios implementan mapeo fila↔entidad.
* Borrado en cascada manual:

  * delete alarm → delete occurrences → delete playlist links (si aplica)

### 12.2 Tablas mínimas (esqueleto)

* `alarms(id, name, enabled, base_time, base_audio_source, base_playlist_key, base_shuffle, base_allow_repeat, created_at)`
* `occurrences(id, alarm_id, enabled, days_of_week_mask, specific_date, time_override, audio_override_source, audio_override_playlist_key, audio_override_shuffle, audio_override_allow_repeat)`
* `playlists(playlist_key, name, source_type)`
* `tracks(id, playlist_key, uri, title, duration_ms, track_order)`

**Nota técnica:**

* `days_of_week_mask` usa bitmask (L..D) para simplificar persistencia.
* `playlist_key` puede ser `TEXT` para soportar ids externos.

---

## 13. Concurrencia y threading

### 13.1 Android

* DB en background thread (Executor).
* Service maneja audio fuera del hilo UI.
* UI solo para renderizado/interacción.

### 13.2 Desktop

* Scheduler thread separado.
* UI en EDT (Swing).
* Audio en hilo dedicado o callbacks del reproductor.

---

## 14. Manejo de errores (técnica, alineada con SRS)

Clasificación:

* Configuración inválida (sin audio final): error y desactivar ocurrencia/alarma según política.
* Recursos faltantes (archivo borrado): notificación y STOP automático.
* Externos (Spotify/YouTube): notificación + acción alternativa (abrir URL / mostrar mensaje).

Recomendación didáctica:

* Usar `Result<T>` en core para propagar errores sin excepciones no controladas.

---

## 15. Validación arquitectónica (checklist)

* [ ] `core` no contiene imports Android/Swing.
* [ ] Los ports están definidos en `core/ports`.
* [ ] Los adapters implementan ports en cada plataforma.
* [ ] Solo se programa un trigger futuro.
* [ ] Reprogramación tras STOP y tras BOOT (Android).
* [ ] Audio Android se ejecuta en ForegroundService.
* [ ] SQLite persistente y consistente en Android/Desktop.
* [ ] Errores visibles y comportamiento definido.

---

## 16. Plan de extensiones (niveles)

* Nivel 1: Alarmas + ocurrencias + música local
* Nivel 2: Shuffle + allowRepeat/noRepeat
* Nivel 3: Snooze + notificación + boot reprogram
* Nivel 4: Spotify/YouTube como `ExternalAction`
* Nivel 5: Mejoras (fade-in, volumen por alarma, UI avanzada)

---

## 17. Apéndice A — Contenedor de dependencias manual (sin framework)

Se recomienda crear un “container” por plataforma para construir dependencias:

* ClockPort (platform)
* Repositorios (platform)
* SchedulerPort (platform)
* Providers (platform)
* UseCases (core con inyección por constructor)

Esto evita singletons y facilita tests.
