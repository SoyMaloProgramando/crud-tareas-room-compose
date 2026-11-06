# CRUD de Tareas con Room (SQLite) + Jetpack Compose + elevación de estados

App Android que hace un **CRUD de tareas** (título, descripción y completada) guardado en una base de datos **SQLite local con Room**, con la interfaz en **Jetpack Compose** y aplicando **elevación de estados (state hoisting)**.

Usa la misma estructura de los proyectos de clase:
- `components/` y los callbacks `set...` del ejemplo **ElevacionEstados** (MyApplication11).
- `services/base`, `controllers`, `models` y `repositories` del ejemplo **Retrofitapp** (MyApplication12); la diferencia es que aquí los datos salen de Room y no de una API.

## Estructura

```
app/src/main/java/com/example/myapplication/
├── MainActivity.kt                 ← estados elevados (tareas, msg) + HomeScreen
├── components/
│   ├── TareaForm.kt                ← formulario Crear / Actualizar (como LoginForm)
│   ├── TareasList.kt               ← LazyColumn con las tareas
│   ├── TareaItem.kt                ← tarjeta con Checkbox, Editar y Eliminar
│   ├── DeleteDialog.kt             ← confirmación antes de eliminar
│   └── MessagesText.kt             ← mismo componente del ejemplo de clase
└── services/
    ├── base/
    │   ├── BaseService.kt          ← getDatabase()   (en Retrofit: getRetrofit())
    │   └── AppDatabase.kt          ← @Database de Room (singleton)
    ├── daos/
    │   └── TareaDao.kt             ← @Query/@Insert/@Update/@Delete (en Retrofit: endpoints)
    ├── controllers/
    │   └── TareaService.kt         ← get_/insert_/update_/delete_tarea con success / error
    ├── models/
    │   └── Tarea.kt                ← @Entity = tabla "tareas"
    └── repositories/
        └── TareasRepository.kt     ← query/create/update/deleteTarea con load / error
```

## Cómo viaja la información

```
TareaForm / TareaItem / DeleteDialog
        │  evento hacia ARRIBA: setTarea(...), setEditar(...), setEliminar(...)
        ▼
HomeScreen  (estados: tareaEditar, tareaEliminar)
        │  setCrear / setActualizar / setEliminar
        ▼
MainActivity (estados: tareas, msg)
        │  tareasRepository.createTarea(...)
        ▼
TareasRepository → TareaService → TareaDao → SQLite (Room)
        │  load(listaActualizada)
        ▼
tareas = it   →  Compose recompone  →  la lista nueva BAJA a TareasList
```

| CRUD | Dónde empieza | Repositorio | DAO |
|---|---|---|---|
| **C**reate | botón **Guardar** (`TareaForm`) | `createTarea` | `@Insert insert_tarea` |
| **R**ead | al abrir la app (`LaunchedEffect`) | `queryTareas` | `@Query get_tareas` |
| **U**pdate | **Editar** → **Actualizar**, o el Checkbox | `updateTarea` | `@Update update_tarea` |
| **D**elete | **Eliminar** → confirmar | `deleteTarea` | `@Delete delete_tarea` |

## Dependencias agregadas

En `gradle/libs.versions.toml`, `build.gradle.kts` y `app/build.gradle.kts`:

- Room `2.8.5`: `room-runtime`, `room-ktx` y `room-compiler` (con `ksp(...)`).
- Plugin KSP `2.3.13` (`com.google.devtools.ksp`), que genera el código de Room. Con AGP 9 se necesita KSP 2.3.6 o superior.

## Cómo ejecutarlo

1. Abrir el proyecto en Android Studio y presionar **Sync Now**.
2. Ejecutar ▶ en un emulador o celular.

Las tareas quedan guardadas en la base de datos `tareas_db` del dispositivo, así que siguen ahí aunque se cierre la app.
