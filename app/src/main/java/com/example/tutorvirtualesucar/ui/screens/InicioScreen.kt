package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen(
    onVerGuia: () -> Unit = {},
    onVerPreguntas: () -> Unit = {},
    onVerEntregas: () -> Unit = {},
    onVerTema: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme
    val textos = MaterialTheme.typography

    Box(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.widthIn(max = 560.dp).fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Tutor Virtual ESUCAR",
                style = textos.titleMedium,
                color = colores.primary,
                fontWeight = FontWeight.Bold
            )

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
                Text(titulo, style = textos.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(descripcion, style = textos.bodySmall, color = colores.onSurfaceVariant)
            }
        }
    }
}