package com.example.myapplication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.services.models.Tarea

// Lista de tareas (lo que devuelve el SELECT de Room).
// Recibe la lista desde arriba y pasa los eventos de cada tarjeta hacia arriba.
@Composable
fun TareasList(
    tareas: List<Tarea>,
    setCompletada: (value: Tarea) -> Unit,
    setEditar: (value: Tarea) -> Unit,
    setEliminar: (value: Tarea) -> Unit
){
    if(tareas.isEmpty()){
        MessagesText(msgUser = "No hay tareas registradas")
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = tareas, key = { it.id }) { tarea ->
                TareaItem(
                    tarea = tarea,
                    setCompletada = setCompletada,
                    setEditar = setEditar,
                    setEliminar = setEliminar
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 400)
@Composable
fun TareasListPreview(){
    TareasList(
        tareas = listOf(
            Tarea(id = 2, titulo = "Taller de Compose", descripcion = "Elevación de estados", completada = false),
            Tarea(id = 1, titulo = "Estudiar Room", descripcion = "Entity, DAO y Database", completada = true)
        ),
        setCompletada = {},
        setEditar = {},
        setEliminar = {}
    )
}
