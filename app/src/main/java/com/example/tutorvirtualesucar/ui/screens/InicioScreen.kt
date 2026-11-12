package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.model.Entrega
import com.example.tutorvirtualesucar.model.EstadoEntrega
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.tutorvirtualesucar.R

@Composable
fun InicioScreen(
    onVerGuia: () -> Unit = {},
    onVerPreguntas: () -> Unit = {},
    onVerEntregas: () -> Unit = {},
    onVerTema: () -> Unit = {},
    modifier: Modifier = Modifier,
    entregas: List<Entrega> = emptyList()
) {
    val colores = MaterialTheme.colorScheme
    val textos = MaterialTheme.typography

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 560.dp)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(6.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.carabineros),
                        contentDescription = "Escudo de Carabineros de Chile",
                        modifier = Modifier.size(48.dp)
                    )
                }

                Column {
                    Text(
                        text = "Tutor Virtual ESUCAR",
                        style = textos.titleMedium,
                        color = colores.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Escuela de Suboficiales",
                        style = textos.bodySmall,
                        color = colores.onSurfaceVariant
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colores.primary,
                    contentColor = colores.onPrimary
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("CUADERNO DE CAMPO", style = textos.labelLarge)
                    Text(
                        text = "Tu trabajo de título, paso a paso",
                        style = textos.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Desde la primera idea hasta la última entrega.",
                        style = textos.bodyMedium
                    )
                }
            }

            MiAvance(
                entregas = entregas,
                onVerEntregas = onVerEntregas
            )

            Text(
                text = "Índice del cuaderno",
                modifier = Modifier.padding(top = 8.dp),
                style = textos.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            EntradaCuaderno(
                "01", "Mi tema de investigación",
                "Define qué quieres investigar.", onVerTema
            )
            EntradaCuaderno(
                "02", "Guía metodológica",
                "Consulta las etapas de tu trabajo.", onVerGuia
            )
            EntradaCuaderno(
                "03", "Mis entregas",
                "Revisa tus fechas y tu avance.", onVerEntregas
            )
            EntradaCuaderno(
                "04", "Preguntas frecuentes",
                "Aclara dudas de formato, citas y metodología.", onVerPreguntas
            )
        }
    }
}

@Composable
private fun MiAvance(
    entregas: List<Entrega>,
    onVerEntregas: () -> Unit
) {
    val colores = MaterialTheme.colorScheme
    val textos = MaterialTheme.typography
    val enviadas = entregas.count {
        it.estado == EstadoEntrega.ENVIADO ||
                it.estado == EstadoEntrega.APROBADO
    }

    val objetivo = if (entregas.isEmpty()) {
        0f
    } else {
        enviadas.toFloat() / entregas.size
    }

    val mostrarAvance = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mostrarAvance.value = true
    }

    val progreso = animateFloatAsState(
        targetValue = if (mostrarAvance.value) objetivo else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "Avance de las entregas"
    )

    Card(
        onClick = onVerEntregas,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colores.primaryContainer,
            contentColor = colores.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Mi avance",
                style = textos.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$enviadas de ${entregas.size} etapas enviadas o aprobadas",
                style = textos.bodyMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .progressSemantics(progreso.value)
                    .clip(RoundedCornerShape(4.dp))
                    .background(colores.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progreso.value)
                        .height(8.dp)
                        .background(colores.primary)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                entregas.sortedBy { it.etapaId }.forEachIndexed { indice, entrega ->
                    val enviada = entrega.estado == EstadoEntrega.ENVIADO ||
                            entrega.estado == EstadoEntrega.APROBADO

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = if (enviada) colores.primary else colores.surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (indice + 1).toString(),
                            color = if (enviada) colores.onPrimary else colores.onSurface,
                            style = textos.labelLarge
                        )
                    }
                }
            }

            Text(
                text = "Toca para revisar tus entregas",
                style = textos.labelMedium
            )
        }
    }
}

@Composable
private fun EntradaCuaderno(
    numero: String,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    val colores = MaterialTheme.colorScheme
    val textos = MaterialTheme.typography

    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = colores.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = numero,
                modifier = Modifier
                    .background(colores.primaryContainer, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                color = colores.onPrimaryContainer,
                style = textos.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = titulo,
                    style = textos.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = descripcion,
                    style = textos.bodySmall,
                    color = colores.onSurfaceVariant
                )
            }
        }
    }
}