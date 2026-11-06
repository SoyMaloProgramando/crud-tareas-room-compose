package com.example.myapplication.services.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.services.daos.TareaDao
import com.example.myapplication.services.models.Tarea

// Base de datos SQLite manejada por Room.
// entities = tablas de la base de datos, version = versión del esquema.
@Database(entities = [Tarea::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: una sola instancia de la base de datos para toda la app
        fun getInstance(context: Context, databaseName: String): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                ).build().also { INSTANCE = it }
            }
        }
    }
}
