package com.example.myapplication.services.base

import android.content.Context
import androidx.lifecycle.ViewModel

// Igual que el BaseService de Retrofit: allí getRetrofit() entregaba el cliente HTTP,
// aquí getDatabase() entrega la base de datos local (SQLite con Room).
abstract class BaseService(context: Context): ViewModel() {
    private val database_name: String = "tareas_db"
    private val database: AppDatabase = AppDatabase.getInstance(context, this.database_name)

    public fun getDatabase(): AppDatabase {
        return this.database
    }
}
