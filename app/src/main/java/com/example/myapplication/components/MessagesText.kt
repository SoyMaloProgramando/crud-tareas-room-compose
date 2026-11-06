package com.example.myapplication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Mismo componente del ejemplo de elevación de estados:
// no tiene estado propio, solo muestra el texto que le llega desde arriba.
@Composable
fun MessagesText(
    msgUser: String
){
    Text(
        modifier = Modifier.padding(12.dp).fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = msgUser
    )
}

@Preview(showBackground = true, widthDp = 250, heightDp = 400)
@Composable
fun MessagesTextPreview(){
    MessagesText(msgUser = "Tarea guardada")
}
