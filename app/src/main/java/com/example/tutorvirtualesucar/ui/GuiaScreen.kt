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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.Etapa

@Composable
fun GuiaScreen(
    etapas: List<Etapa>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Guía metodológica",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(etapas, key = { it.id }) { etapa ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = etapa.titulo,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(text = etapa.descripcion)
                    Text(text = "Formato: ${etapa.formatoRequerido}")
                    Text(text = "Fecha límite: ${etapa.fechaLimite}")
                }
            }
        }
    }
}