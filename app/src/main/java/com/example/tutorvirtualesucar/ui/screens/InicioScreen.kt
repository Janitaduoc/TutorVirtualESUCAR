package com.example.tutorvirtualesucar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen(
    onVerGuia: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tutor Virtual ESUCAR",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Tu apoyo para desarrollar el trabajo de título.",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = onVerGuia) {
            Text("Ver guía metodológica")
        }
    }
}