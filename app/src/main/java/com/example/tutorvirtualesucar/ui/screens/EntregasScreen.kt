package com.example.tutorvirtualesucar.ui.screens

import com.example.tutorvirtualesucar.ui.screens.EntregasScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.Entrega
import com.example.tutorvirtualesucar.model.EstadoEntrega

@Composable
fun EntregasScreen(
    entregas: List<Entrega>,
    onMarcarEnDesarrollo: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Mis entregas", style = MaterialTheme.typography.headlineMedium)
        }

        items(entregas, key = { it.id }) { entrega ->
            val estado = when (entrega.estado) {
                EstadoEntrega.POR_INICIAR -> "Por iniciar"
                EstadoEntrega.EN_DESARROLLO -> "En desarrollo"
                EstadoEntrega.ENVIADO -> "Enviado"
                EstadoEntrega.APROBADO -> "Aprobado"
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(entrega.titulo, style = MaterialTheme.typography.titleMedium)
                    Text("Fecha límite: ${entrega.fechaLimite}")
                    Text("Estado: $estado")

                    if (entrega.estado == EstadoEntrega.POR_INICIAR) {
                        Button(onClick = { onMarcarEnDesarrollo(entrega.id) }) {
                            Text("Marcar en desarrollo")
                        }
                    }
                }
            }
        }
    }
}