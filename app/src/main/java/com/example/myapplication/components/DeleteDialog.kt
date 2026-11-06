package com.example.myapplication.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.services.models.Tarea

// Diálogo para confirmar antes de eliminar.
// La decisión (confirmar o cancelar) se envía HACIA ARRIBA.
@Composable
fun DeleteDialog(
    tarea: Tarea,
    setConfirmar: (value: Tarea) -> Unit,
    setCancelar: () -> Unit
){
    AlertDialog(
        onDismissRequest = { setCancelar() },
        title = { Text(text = "Eliminar tarea") },
        text = { Text(text = "¿Seguro que desea eliminar \"" + tarea.titulo + "\"?") },
        confirmButton = {
            TextButton(onClick = { setConfirmar(tarea) }) { Text(text = "Eliminar") }
        },
        dismissButton = {
            TextButton(onClick = { setCancelar() }) { Text(text = "Cancelar") }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 300)
@Composable
fun DeleteDialogPreview(){
    DeleteDialog(
        tarea = Tarea(id = 1, titulo = "Estudiar Room", descripcion = "", completada = false),
        setConfirmar = {},
        setCancelar = {}
    )
}
