package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.PreguntaFrecuente
import java.text.Normalizer

private fun normalizarBusqueda(texto: String): String =
    Normalizer.normalize(texto, Normalizer.Form.NFD)
        .replace(Regex("\\p{M}+"), "")
        .lowercase()

@Composable
fun PreguntasScreen(
    preguntas: List<PreguntaFrecuente>,
    modifier: Modifier = Modifier
) {
    val busqueda = rememberSaveable { mutableStateOf("") }
    val abierta = rememberSaveable { mutableStateOf<Int?>(null) }
    val consulta = normalizarBusqueda(busqueda.value.trim())

    val porCategoria = preguntas.filter { pregunta ->
        listOf(pregunta.pregunta, pregunta.respuesta, pregunta.categoria)
            .any { normalizarBusqueda(it).contains(consulta) }
    }.groupBy { it.categoria }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Preguntas frecuentes", style = MaterialTheme.typography.headlineMedium)
        }

        item {
            OutlinedTextField(
                value = busqueda.value,
                onValueChange = { busqueda.value = it },
                label = { Text("Buscar pregunta") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (porCategoria.isEmpty()) {
            item { Text("No se encontraron preguntas.") }
        }

        porCategoria.forEach { (categoria, grupo) ->
            item {
                Text(categoria, style = MaterialTheme.typography.titleMedium)
            }

            items(grupo, key = { it.id }) { pregunta ->
                val expandida = abierta.value == pregunta.id

                Card(
                    onClick = { abierta.value = if (expandida) null else pregunta.id },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(pregunta.pregunta, style = MaterialTheme.typography.titleSmall)
                        if (expandida) Text(pregunta.respuesta)
                        Text(if (expandida) "Ocultar respuesta" else "Ver respuesta")
                    }
                }
            }
        }
    }
}