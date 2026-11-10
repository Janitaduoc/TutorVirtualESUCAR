package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.material3.FilterChip
import com.example.tutorvirtualesucar.model.Entrega
import com.example.tutorvirtualesucar.model.EstadoEntrega
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.Etapa

@Composable
fun GuiaScreen(
    etapas: List<Etapa>,
    entregas: List<Entrega>,
    onCambiarEstado: (Int, EstadoEntrega) -> Unit,
    modifier: Modifier = Modifier
) {
    val etapaSeleccionadaId = rememberSaveable { mutableStateOf<Int?>(null) }
    val etapaSeleccionada = etapas.firstOrNull {
        it.id == etapaSeleccionadaId.value
    }

    BackHandler(enabled = etapaSeleccionada != null) {
        etapaSeleccionadaId.value = null
    }

    key(etapaSeleccionada?.id) {
        val paso = rememberSaveable { mutableStateOf(1) }
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (etapaSeleccionada == null) {
                item {
                    Text(
                        text = "Guía metodológica",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text("Toca una etapa para ver su detalle.")
                }

                items(etapas, key = { it.id }) { etapa ->
                    Card(
                        onClick = { etapaSeleccionadaId.value = etapa.id },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = etapa.titulo,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(etapa.descripcion)
                            entregas.firstOrNull { it.etapaId == etapa.id }?.let { entrega ->
                                EtiquetaEstado(estado = entrega.estado)
                            }
                        }
                    }
                }
            } else {
                item {
                    TextButton(onClick = { etapaSeleccionadaId.value = null }) {
                        Text("Volver a las etapas")
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = etapaSeleccionada.titulo,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text("Fecha límite: ${etapaSeleccionada.fechaLimite}")
                        entregas.firstOrNull { it.etapaId == etapaSeleccionada.id }?.let { entrega ->
                            EtiquetaEstado(estado = entrega.estado)

                            if (entrega.estado != EstadoEntrega.APROBADO) {
                                Text("Cambiar estado")
                                listOf(
                                    EstadoEntrega.POR_INICIAR,
                                    EstadoEntrega.EN_DESARROLLO,
                                    EstadoEntrega.ENVIADO
                                ).forEach { opcion ->
                                    FilterChip(
                                        selected = entrega.estado == opcion,
                                        onClick = { onCambiarEstado(entrega.id, opcion) },
                                        label = { Text(nombreEstado(opcion)) }
                                    )
                                }
                            }
                        }
                        Text("Paso ${paso.value} de 3", style = MaterialTheme.typography.labelLarge)

                        when (paso.value) {
                            1 -> {
                                Text(
                                    "Comprende la etapa",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(etapaSeleccionada.descripcion)
                            }

                            2 -> {
                                Text(
                                    "Prepara la entrega",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Formato requerido: ${etapaSeleccionada.formatoRequerido}")
                            }

                            3 -> {
                                Text(
                                    "Criterios de ejemplo",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                etapaSeleccionada.pautas.forEach { criterio ->
                                    Text("• $criterio")
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(
                                onClick = { paso.value -= 1 },
                                enabled = paso.value > 1
                            ) {
                                Text("Anterior")
                            }

                            Button(
                                onClick = { paso.value += 1 },
                                enabled = paso.value < 3
                            ) {
                                Text("Siguiente")
                            }
                        }
                    }
                }
            }
        }
    }
}
private fun nombreEstado(estado: EstadoEntrega): String = when (estado) {
    EstadoEntrega.POR_INICIAR -> "Por iniciar"
    EstadoEntrega.EN_DESARROLLO -> "En desarrollo"
    EstadoEntrega.ENVIADO -> "Enviado"
    EstadoEntrega.APROBADO -> "Aprobado"
}

@Composable
private fun EtiquetaEstado(estado: EstadoEntrega) {
    val colores = MaterialTheme.colorScheme
    val color = when (estado) {
        EstadoEntrega.POR_INICIAR -> colores.onSurfaceVariant
        EstadoEntrega.EN_DESARROLLO -> colores.primary
        EstadoEntrega.ENVIADO -> colores.secondary
        EstadoEntrega.APROBADO -> colores.tertiary
    }

    Text(
        text = "Estado: ${nombreEstado(estado)}",
        color = color,
        style = MaterialTheme.typography.labelLarge
    )
}