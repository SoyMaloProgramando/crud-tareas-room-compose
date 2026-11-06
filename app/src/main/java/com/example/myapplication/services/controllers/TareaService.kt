package com.example.myapplication.services.controllers

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.myapplication.services.base.BaseService
import com.example.myapplication.services.models.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Misma lógica que PostService: cada función lanza una corrutina,
// llama al DAO y responde con success(...) o error(...)
class TareaService(context: Context): BaseService(context) {

    // R - Consultar todas las tareas
    fun get_tareas(
        success: (data: List<Tarea>)-> Unit,
        error: (msg:String)-> Unit
    ){
        viewModelScope.launch (Dispatchers.Main){
            try {
                val data = getDatabase().tareaDao().get_tareas()
                success(data)
            } catch (ex: Exception){
                ex.printStackTrace()
                error("Se presentó un error al consultar la tabla tareas")
            }
        }
    }

    // C - Insertar una tarea
    fun insert_tarea(
        tarea: Tarea,
        success: ()-> Unit,
        error: (msg:String)-> Unit
    ){
        viewModelScope.launch (Dispatchers.Main){
            try {
                getDatabase().tareaDao().insert_tarea(tarea)
                success()
            } catch (ex: Exception){
                ex.printStackTrace()
                error("Se presentó un error al insertar en la tabla tareas")
            }
        }
    }

    // U - Actualizar una tarea
    fun update_tarea(
        tarea: Tarea,
        success: ()-> Unit,
        error: (msg:String)-> Unit
    ){
        viewModelScope.launch (Dispatchers.Main){
            try {
                getDatabase().tareaDao().update_tarea(tarea)
                success()
            } catch (ex: Exception){
                ex.printStackTrace()
                error("Se presentó un error al actualizar la tabla tareas")
            }
        }
    }

    // D - Eliminar una tarea
    fun delete_tarea(
        tarea: Tarea,
        success: ()-> Unit,
        error: (msg:String)-> Unit
    ){
        viewModelScope.launch (Dispatchers.Main){
            try {
                getDatabase().tareaDao().delete_tarea(tarea)
                success()
            } catch (ex: Exception){
                ex.printStackTrace()
                error("Se presentó un error al eliminar en la tabla tareas")
            }
        }
    }
}
