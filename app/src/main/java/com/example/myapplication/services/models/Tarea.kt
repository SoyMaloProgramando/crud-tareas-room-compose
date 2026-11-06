package com.example.myapplication.services.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity = esta data class es una TABLA de SQLite llamada "tareas".
// (En Retrofit el modelo Post usaba @SerializedName; en Room se usa @ColumnInfo)
@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "titulo")
    var titulo: String,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "completada")
    var completada: Boolean = false
)
