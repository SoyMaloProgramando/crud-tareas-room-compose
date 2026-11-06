package com.example.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.services.models.Tarea

// Formulario para CREAR y ACTUALIZAR (misma idea que LoginForm):
// - Los campos son estados locales, porque solo los usa este componente.
// - Al presionar el botón, la tarea se envía HACIA ARRIBA con setTarea(...)
// - Si tareaEditar es null se está creando; si trae una tarea se está editando.
@Composable
fun TareaForm(
    tareaEditar: Tarea?,
    setTarea: (value: Tarea) -> Unit,
    setCancelar: () -> Unit,
    setMsg: (value: String) -> Unit
){
    // remember(tareaEditar): cuando cambia la tarea a editar, los campos se vuelven a llenar
    var titulo by remember(tareaEditar) { mutableStateOf<String>(tareaEditar?.titulo ?: "") }
    var descripcion by remember(tareaEditar) { mutableStateOf<String>(tareaEditar?.descripcion ?: "") }

    Column {
        TextField(
            modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
            label = {Text(text = "Título")},
            value = titulo,
            onValueChange = {titulo = it}
        )
        TextField(
            modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
            label = {Text(text = "Descripción")},
            value = descripcion,
            onValueChange = {descripcion = it}
        )
        Button(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            onClick = {
                if(titulo.isBlank()){
                    setMsg("El título es obligatorio")
                } else {
                    setTarea(
                        Tarea(
                            id = tareaEditar?.id ?: 0, // 0 = tarea nueva, Room genera el id
                            titulo = titulo.trim(),
                            descripcion = descripcion.trim(),
                            completada = tareaEditar?.completada ?: false
                        )
                    )
                    titulo = ""
                    descripcion = ""
                }
            }
        ) { Text(text = if(tareaEditar == null) "Guardar" else "Actualizar") }

        if(tareaEditar != null){
            OutlinedButton(
                modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(),
                onClick = { setCancelar() }
            ) { Text(text = "Cancelar edición") }
        }
    }
}

@Preview(showBackground = true, widthDp = 250, heightDp = 400)
@Composable
fun TareaFormPreview(){
    TareaForm(
        tareaEditar = null,
        setTarea = {},
        setCancelar = {},
        setMsg = {}
    )
}
