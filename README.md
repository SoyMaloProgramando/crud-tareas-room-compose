# Trabajo para clase CRUD

App Android de tareas hecha con **Jetpack Compose** y **Room (SQLite)**. Permite crear, ver, editar, marcar como completadas y eliminar tareas. Los datos se guardan en el celular, así que no se pierden al cerrar la app.

## Tecnologías

- Kotlin
- Jetpack Compose (con elevación de estados)
- Room / SQLite

## Estructura

```
app/src/main/java/com/example/myapplication/
├── MainActivity.kt   → pantalla principal
├── components/       → formulario, lista y diálogo
└── services/         → base de datos con Room
```

## Cómo ejecutarlo

1. Abrir el proyecto en Android Studio.
2. Esperar a que termine el Gradle Sync.
3. Darle ▶ Run en un emulador o celular.
