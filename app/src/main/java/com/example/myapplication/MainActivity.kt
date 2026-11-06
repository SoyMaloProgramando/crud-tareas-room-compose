package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.DeleteDialog
import com.example.myapplication.components.MessagesText
import com.example.myapplication.components.TareaForm
import com.example.myapplication.components.TareasList
import com.example.myapplication.services.models.Tarea
import com.example.myapplication.services.repositories.TareasRepository
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    val tareasRepository: TareasRepository by lazy { TareasRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // ESTADOS ELEVADOS: viven aquí arriba (donde está el repositorio)
                // y bajan a los componentes como parámetros.
                var tareas by remember { mutableStateOf<List<Tarea>>(emptyList()) }
                var msg by remember { mutableStateOf<String>("") }

                // R - READ: consulta inicial de la tabla.
                // LaunchedEffect(Unit) la ejecuta una sola vez, no en cada recomposición.
                LaunchedEffect(Unit) {
                    tareasRepository.queryTareas(
                        load = { tareas = it },
                        error = { msg = "Error al consultar las tareas" }
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        tareas = tareas,
                        msg = msg,
                        setMsg = { msg = it },
                        // C - CREATE
                        setCrear = { tarea ->
                            tareasRepository.createTarea(
                                tarea = tarea,
                                load = {
                                    tareas = it
                                    msg = "Tarea guardada"
                                },
                                error = { msg = "Error al guardar la tarea" }
                            )
                        },
                        // U - UPDATE
                        setActualizar = { tarea ->
                            tareasRepository.updateTarea(
                                tarea = tarea,
                                load = {
                                    tareas = it
                                    msg = "Tarea actualizada"
                                },
                                error = { msg = "Error al actualizar la tarea" }
                            )
                        },
                        // D - DELETE
                        setEliminar = { tarea ->
                            tareasRepository.deleteTarea(
                                tarea = tarea,
                                load = {
                                    tareas = it
                                    msg = "Tarea eliminada"
                                },
                                error = { msg = "Error al eliminar la tarea" }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    tareas: List<Tarea>,
    msg: String,
    setMsg: (value: String) -> Unit,
    setCrear: (value: Tarea) -> Unit,
    setActualizar: (value: Tarea) -> Unit,
    setEliminar: (value: Tarea) -> Unit,
    modifier: Modifier = Modifier
){
    // Estados elevados a la pantalla: los comparten el formulario, la lista y el diálogo
    var tareaEditar by remember { mutableStateOf<Tarea?>(null) }
    var tareaEliminar by remember { mutableStateOf<Tarea?>(null) }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = "CRUD de Tareas (Room)",
            style = MaterialTheme.typography.headlineSmall
        )
        TareaForm(
            tareaEditar = tareaEditar,
            setTarea = {
                // id 0 = tarea nueva (CREATE); id distinto de 0 = tarea existente (UPDATE)
                if(it.id == 0){
                    setCrear(it)
                } else {
                    // se conserva el "completada" actual, por si se marcó mientras se editaba
                    val completada = tareas.find { t -> t.id == it.id }?.completada ?: it.completada
                    setActualizar(it.copy(completada = completada))
                }
                tareaEditar = null
            },
            setCancelar = {
                tareaEditar = null
                setMsg("")
            },
            setMsg = { setMsg(it) }
        )
        MessagesText(msgUser = msg)
        TareasList(
            tareas = tareas,
            setCompletada = { setActualizar(it) },
            setEditar = {
                tareaEditar = it
                setMsg("Editando: " + it.titulo)
            },
            setEliminar = { tareaEliminar = it }
        )
    }

    // El diálogo solo aparece cuando hay una tarea por eliminar
    // (misma idea que if(validUser) DashboardScreen() else HomeScreen(...))
    tareaEliminar?.let { tarea ->
        DeleteDialog(
            tarea = tarea,
            setConfirmar = {
                setEliminar(it)
                if(tareaEditar?.id == it.id){
                    tareaEditar = null
                }
                tareaEliminar = null
            },
            setCancelar = { tareaEliminar = null }
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview(){
    MyApplicationTheme {
        HomeScreen(
            tareas = listOf(
                Tarea(id = 2, titulo = "Taller de Compose", descripcion = "Elevación de estados", completada = false),
                Tarea(id = 1, titulo = "Estudiar Room", descripcion = "Entity, DAO y Database", completada = true)
            ),
            msg = "Tarea guardada",
            setMsg = {},
            setCrear = {},
            setActualizar = {},
            setEliminar = {}
        )
    }
}
