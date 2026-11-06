package com.example.myapplication.services.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.services.models.Tarea

// El DAO cumple el mismo papel que PostEndpoints en Retrofit:
// allí se usaba @GET("/posts"), aquí se usan @Query, @Insert, @Update y @Delete
// sobre la tabla "tareas" de SQLite.
@Dao
interface TareaDao {

    // R - Read (consultar)
    @Query("SELECT * FROM tareas ORDER BY id DESC")
    suspend fun get_tareas(): List<Tarea>

    // C - Create (insertar)
    @Insert
    suspend fun insert_tarea(tarea: Tarea): Long

    // U - Update (actualizar), busca el registro por su id
    @Update
    suspend fun update_tarea(tarea: Tarea): Int

    // D - Delete (eliminar), busca el registro por su id
    @Delete
    suspend fun delete_tarea(tarea: Tarea): Int
}
