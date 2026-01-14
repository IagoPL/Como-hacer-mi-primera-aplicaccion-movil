
# Despertador Multiplataforma en Java 

## Android + Desktop (Java SE)

Proyecto completo  de prueba para el desarrollo de una aplicación de despertador multiplataforma, implementada íntegramente en Java, sin frameworks de arquitectura, siguiendo principios de ingeniería de software reales.

Este repositorio incluye:
- Especificación formal de requisitos
- Arquitectura técnica detallada
- Manual paso a paso de implementación
- Implementación de referencia (app final)

---

## Objetivo del proyecto

El objetivo de este proyecto es aprender a diseñar e implementar una aplicación real aplicando:

- Programación orientada a objetos
- Separación de responsabilidades
- Arquitectura por capas
- Persistencia local
- Ejecución en segundo plano
- Reutilización de lógica entre plataformas

El proyecto no busca optimización extrema, sino claridad, corrección y mantenibilidad.

---

## Descripción general

La aplicación permite:

- Crear y gestionar alarmas.
- Configurar repeticiones individuales por alarma.
- Asociar música local o externa a cada alarma.
- Reproducir música en modo:
  - aleatorio (shuffle)
  - con o sin repetición de canciones
- Ejecutarse en:
  - Android (Java nativo)
  - Ordenadores (Java SE + Swing)

La lógica de negocio se comparte entre plataformas mediante un módulo `core` independiente.

---

## Estructura del repositorio

```

root/
├── core/                 # Lógica de negocio compartida (Java puro)
├── androidApp/           # Aplicación Android nativa
├── desktopApp/           # Aplicación de escritorio (Swing)
├── docs/                 # Documentación del proyecto
│    ├── 01_SRS.md
│    ├── 02_Arquitectura_Tecnica.md
│    └── 03_Manual_Implementacion.md
└── README.md

````

---

## Documentación del proyecto

Toda la documentación está en la carpeta `docs/` y debe leerse en este orden:

### 1. `01_SRS.md` — Especificación de requisitos
Define qué debe hacer el sistema:
- Requisitos funcionales y no funcionales
- Modelo de datos lógico
- Reglas del negocio
- Casos de uso
- Estados del sistema
- Criterios de aceptación

Documento normativo y no interpretable.

---

### 2. `02_Arquitectura_Tecnica.md` — Diseño técnico
Define cómo se implementa el sistema:
- Arquitectura por capas
- Módulos y dependencias
- Interfaces (ports)
- Adaptadores Android y Desktop
- Scheduling, audio y persistencia

Traduce el SRS a una solución técnica concreta.

---

### 3. `03_Manual_Implementacion.md` — Guía paso a paso
Explica cómo construir el proyecto:
- Fases de implementación
- Código clave
- Checkpoints por fase
- Buenas prácticas

Documento principal para el desarrollo.

---

## Arquitectura resumida

- Core compartido (Java puro)
  - Entidades
  - Reglas de negocio
  - Casos de uso
- Adaptadores por plataforma
  - Android: AlarmManager, ForegroundService, MediaPlayer, SQLiteOpenHelper
  - Desktop: ScheduledExecutorService, Swing, Clip, SQLite JDBC
- Persistencia local
  - SQLite en ambas plataformas
- Scheduling
  - Se programa solo el próximo disparo activo

---

## Requisitos técnicos

### Generales
- Java JDK 8 o superior
- Git

### Android
- Android Studio
- SDK Android actualizado
- Dispositivo o emulador con Android 8 o superior

### Desktop
- Java SE
- Sistema operativo compatible con Java (Windows, Linux o macOS)

---

## Cómo empezar

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd despertador-java
````

### 2. Leer la documentación

Comenzar por:

1. `docs/01_SRS.md`
2. `docs/02_Arquitectura_Tecnica.md`
3. `docs/03_Manual_Implementacion.md`

### 3. Implementar por fases

Seguir estrictamente las fases del manual.
No empezar Android o Desktop sin completar el core.
