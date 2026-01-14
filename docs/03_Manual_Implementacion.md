# Documentación funcional y técnica

## Aplicación de despertador multiplataforma en Java

---

## 1. Descripción general del proyecto

El proyecto consiste en desarrollar una **aplicación de despertador** escrita íntegramente en **Java**, sin el uso de frameworks externos de desarrollo de aplicaciones móviles o multiplataforma.

La aplicación deberá ser **compatible tanto con dispositivos móviles Android como con ordenadores**, compartiendo la lógica de negocio principal y diferenciando únicamente la capa de interfaz y la interacción con el sistema operativo.

La app permitirá al usuario:

* Crear y gestionar alarmas.
* Configurar repeticiones de alarmas de forma individual.
* Asociar a cada alarma una o varias fuentes de audio.
* Reproducir música local o proveniente de plataformas externas.
* Controlar el comportamiento de la reproducción (aleatorio, repetición, etc.).

El proyecto está orientado a un **contexto educativo**, priorizando la claridad, la arquitectura y el entendimiento del sistema por encima de la optimización o el uso de tecnologías avanzadas.

---

## 2. Objetivos del sistema

### 2.1 Objetivo principal

Desarrollar un despertador funcional y extensible que permita comprender:

* La programación orientada a objetos en Java.
* La separación de responsabilidades.
* La persistencia de datos.
* La planificación de tareas en el tiempo.
* La reproducción de audio.
* La adaptación del mismo sistema a diferentes plataformas.

### 2.2 Objetivos secundarios

* Introducir integraciones con servicios externos reales.
* Manejar permisos y restricciones del sistema operativo.
* Diseñar una arquitectura reutilizable.
* Fomentar la documentación técnica clara.

---

## 3. Alcance del proyecto

### 3.1 Incluido en el proyecto

* Aplicación Android nativa en Java.
* Aplicación de escritorio Java (Java SE).
* Persistencia local de datos.
* Reproducción de audio local.
* Integración básica con Spotify y YouTube.
* Documentación completa del sistema.

### 3.2 No incluido

* Sincronización en la nube.
* Soporte iOS.
* Streaming de audio externo sin SDK oficial.
* Backend remoto.
* Publicación en tiendas oficiales.

---

## 4. Usuarios del sistema

El sistema está diseñado para un **usuario final genérico**, sin perfiles diferenciados.
Se asume que el usuario:

* Sabe usar un móvil u ordenador.
* Tiene música almacenada localmente o acceso a plataformas externas.
* Desea personalizar el comportamiento de sus alarmas.

---

## 5. Conceptos clave del dominio

### 5.1 Alarma

Una **alarma** representa una configuración base que define:

* Un nombre o etiqueta.
* Una hora principal.
* Un estado (activa/inactiva).
* Una configuración de audio base.
* Un conjunto de repeticiones asociadas.

La alarma **no es un único disparo**, sino un **contenedor de comportamiento**.

---

### 5.2 Repetición (Ocurrencia)

Una **repetición** es una ejecución concreta de una alarma.

Cada repetición puede:

* Activarse en días concretos de la semana o en una fecha específica.
* Tener su propia hora (opcional).
* Sobrescribir la configuración de audio de la alarma.
* Tener reglas propias de reproducción.

Esto permite que:

> Una misma alarma se comporte de forma diferente según el día.

---

### 5.3 Fuente de audio

Una fuente de audio define **de dónde proviene la música** que se reproducirá cuando suene la alarma.

Tipos:

1. Música local.
2. Spotify.
3. YouTube.

Cada fuente tiene restricciones técnicas distintas.

---

### 5.4 Playlist

Una playlist es una **colección ordenada o desordenada de pistas** que será utilizada durante la ejecución de la alarma.

Puede estar formada por:

* Canciones seleccionadas manualmente.
* Canciones de una o varias carpetas.
* Todas las canciones disponibles.
* Enlaces externos (Spotify / YouTube).

---

## 6. Requisitos funcionales

### RF-01 Gestión de alarmas

El sistema deberá permitir:

* Crear alarmas.
* Editar alarmas.
* Eliminar alarmas.
* Activar y desactivar alarmas.

---

### RF-02 Programación temporal

El sistema deberá:

* Calcular la próxima ejecución válida de cada alarma.
* Reprogramar automáticamente la siguiente ejecución tras dispararse.
* Manejar cambios de hora y reinicios del sistema.

---

### RF-03 Repeticiones configurables individualmente

Cada alarma podrá contener múltiples repeticiones.

Para cada repetición el usuario podrá configurar:

* Días de la semana o fecha específica.
* Hora propia o heredada.
* Fuente de audio propia o heredada.
* Opciones de reproducción propias o heredadas.

---

### RF-04 Reproducción de música local

El sistema permitirá:

* Seleccionar canciones individuales.
* Seleccionar carpetas completas.
* Seleccionar todas las canciones disponibles.
* Crear colas de reproducción dinámicas.

---

### RF-05 Control de reproducción

El usuario podrá configurar:

* Reproducción aleatoria (shuffle).
* Permitir o no repetir canciones.
* Repetir la lista completa si se agota.
* Continuar reproduciendo hasta que el usuario detenga la alarma.

---

### RF-06 Integración con Spotify

El sistema permitirá:

* Asociar una alarma a una playlist de Spotify.
* Lanzar la reproducción usando el cliente oficial.
* Gestionar errores si Spotify no está disponible.

---

### RF-07 Integración con YouTube

El sistema permitirá:

* Asociar una alarma a una playlist de YouTube.
* Abrir la playlist cuando la alarma se dispare.
* Informar de limitaciones de reproducción.

---

### RF-08 Multiplataforma

El sistema deberá:

* Compartir la lógica de negocio entre Android y escritorio.
* Implementar mecanismos específicos de cada plataforma para alarmas y audio.

---

## 7. Requisitos no funcionales

### RNF-01 Persistencia

Todos los datos deberán almacenarse localmente y sobrevivir:

* Reinicios del dispositivo.
* Cierres inesperados de la aplicación.

---

### RNF-02 Rendimiento

* El cálculo de alarmas no deberá consumir recursos innecesarios.
* La reproducción deberá ser fluida.

---

### RNF-03 Consumo energético

* En móviles, la app no deberá mantener procesos activos innecesarios.
* Solo se activará cuando una alarma se dispare.

---

### RNF-04 Seguridad y permisos

* Solicitar solo los permisos estrictamente necesarios.
* Manejar correctamente permisos denegados.

---

### RNF-05 Usabilidad

* Interfaz clara y funcional.
* Flujo lógico de pantallas.
* Mensajes de error comprensibles.

---

## 8. Casos de uso principales

### CU-01 Crear alarma

1. El usuario pulsa “crear alarma”.
2. Introduce nombre y hora.
3. Define fuente de audio.
4. Configura repeticiones.
5. Guarda la alarma.

Resultado: la alarma queda programada.

---

### CU-02 Disparo de alarma

1. El sistema detecta que una repetición debe ejecutarse.
2. Se lanza el mecanismo de alarma.
3. Comienza la reproducción.
4. El usuario detiene o pospone la alarma.
5. Se programa la siguiente ejecución válida.

---

### CU-03 Reproducción aleatoria

1. Se genera una cola de reproducción.
2. Las canciones se reproducen sin repetirse (si está configurado).
3. La lista se reinicia solo si está permitido.

---

## 9. Casos límite y excepciones

* El dispositivo se reinicia.
* El usuario elimina música seleccionada.
* Spotify no está instalado.
* No hay conexión a Internet.
* Permisos de almacenamiento denegados.
* El volumen del sistema está a cero.
* Cambio de zona horaria.

Cada caso deberá ser **detectado y tratado** de forma explícita.

---

## 10. Arquitectura general del sistema

### 10.1 Separación por capas

* Capa de presentación (UI).
* Capa de lógica de negocio (core).
* Capa de persistencia.
* Capa de integración con sistema operativo.

---

### 10.2 Núcleo compartido

El núcleo contendrá:

* Modelos de dominio.
* Reglas de negocio.
* Cálculo de repeticiones.
* Gestión de colas de reproducción.

Este núcleo será **Java puro**, sin dependencias de Android ni de escritorio.

---

## 11. Compatibilidad multiplataforma

### Android

* AlarmManager.
* BroadcastReceiver.
* Foreground Service.
* MediaPlayer.
* SQLiteOpenHelper.

### Escritorio

* Scheduler Java.
* UI Swing.
* Audio Java SE.
* SQLite vía JDBC.

---

## 12. Criterios de evaluación (para alumnos)

* Correcta implementación de alarmas.
* Separación clara de responsabilidades.
* Uso correcto de persistencia.
* Manejo de errores y casos límite.
* Claridad del código.
* Documentación del proyecto.

---

## 13. Resultado esperado

Al finalizar el proyecto, el alumno habrá construido:

* Una aplicación funcional real.
* Un sistema extensible.
* Un proyecto organizado y documentado.
* Una base sólida para proyectos más complejos.

---
