package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.PreguntaFrecuente
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun PreguntasScreen(
    preguntas: List<PreguntaFrecuente>,
    modifier: Modifier = Modifier
) {
    val busqueda = rememberSaveable { mutableStateOf("") }

    val preguntasFiltradas = preguntas.filter { pregunta ->
        pregunta.pregunta.contains(busqueda.value.trim(), ignoreCase = true)
    }
    LazyColumn(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Preguntas frecuentes",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        item {
            OutlinedTextField(
                value = busqueda.value,
                onValueChange = { busqueda.value = it },
                label = { Text("Buscar pregunta") },
                singleLine = true
            )
        }

        if (preguntasFiltradas.isEmpty()) {
            item {
                Text("No se encontraron preguntas.")
            }
        }

        items(preguntasFiltradas, key = { it.id }) { pregunta ->
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = pregunta.pregunta,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = pregunta.respuesta)
                }
            }
        }
    }
}