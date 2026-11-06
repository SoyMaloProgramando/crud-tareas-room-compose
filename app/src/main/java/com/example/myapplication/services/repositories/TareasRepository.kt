package com.example.myapplication.services.repositories

import android.content.Context
import com.example.myapplication.services.controllers.TareaService
import com.example.myapplication.services.models.Tarea

// Misma lógica que PostsRepository: la pantalla solo habla con el repositorio
// y recibe la respuesta en load(...) o error().
// Después de crear, actualizar o eliminar, se vuelve a consultar la tabla
// para entregar la lista actualizada.
class TareasRepository(context: Context) {
    private val tareaService: TareaService = TareaService(context)

    // R - Read
    fun queryTareas(
        load:(data: List<Tarea>)-> Unit,
        error:() -> Unit
    ){
        this.tareaService.get_tareas(
            success = {
                load(it)
            },
            error = {
                System.err.println(it)
                error()
            }
        )
    }

    // C - Create
    fun createTarea(
        tarea: Tarea,
        load:(data: List<Tarea>)-> Unit,
        error:() -> Unit
    ){
        this.tareaService.insert_tarea(
            tarea = tarea,
            success = {
                this.queryTareas(load = load, error = error)
            },
            error = {
                System.err.println(it)
                error()
            }
        )
    }

    // U - Update
    fun updateTarea(
        tarea: Tarea,
        load:(data: List<Tarea>)-> Unit,
        error:() -> Unit
    ){
        this.tareaService.update_tarea(
            tarea = tarea,
            success = {
                this.queryTareas(load = load, error = error)
            },
            error = {
                System.err.println(it)
                error()
            }
        )
    }

    // D - Delete
    fun deleteTarea(
        tarea: Tarea,
        load:(data: List<Tarea>)-> Unit,
        error:() -> Unit
    ){
        this.tareaService.delete_tarea(
            tarea = tarea,
            success = {
                this.queryTareas(load = load, error = error)
            },
            error = {
                System.err.println(it)
                error()
            }
        )
    }
}
