package com.example.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.services.models.Tarea

// Tarjeta de una tarea. No decide nada por sí sola:
// avisa HACIA ARRIBA qué tarea se marcó, se quiere editar o eliminar.
@Composable
fun TareaItem(
    tarea: Tarea,
    setCompletada: (value: Tarea) -> Unit,
    setEditar: (value: Tarea) -> Unit,
    setEliminar: (value: Tarea) -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { setCompletada(tarea.copy(completada = it)) }
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.titulo,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if(tarea.completada) TextDecoration.LineThrough else TextDecoration.None
                )
                if(tarea.descripcion.isNotBlank()){
                    Text(text = tarea.descripcion)
                }
                Row {
                    TextButton(onClick = { setEditar(tarea) }) { Text(text = "Editar") }
                    TextButton(onClick = { setEliminar(tarea) }) { Text(text = "Eliminar") }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 160)
@Composable
fun TareaItemPreview(){
    TareaItem(
        tarea = Tarea(id = 1, titulo = "Estudiar Room", descripcion = "Entity, DAO y Database", completada = false),
        setCompletada = {},
        setEditar = {},
        setEliminar = {}
    )
}
