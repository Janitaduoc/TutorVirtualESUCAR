package com.example.tutorvirtualesucar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorvirtualesucar.ui.theme.TutorVirtualESUCARTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.AnimatedVisibility
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutorVirtualESUCARTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Tutor Virtual ESUCAR",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier)
{val mostrarGuia = rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier.padding(24.dp)) {
        Text(
            text = "¡Bienvenido a $name!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tu apoyo para desarrollar el trabajo de título.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            mostrarGuia.value = !mostrarGuia.value
        }) {
            Text(
                text = if (mostrarGuia.value) "Ocultar guía"
                else "Ver guía metodológica"
            )
            AnimatedVisibility(visible = mostrarGuia.value) {
                Column { Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Planteamiento del problema",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Describe qué problema investigarás, a quién afecta y por qué es importante estudiarlo.",
                    style = MaterialTheme.typography.bodyLarge
                )}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TutorVirtualESUCARTheme {
        Greeting("Android")
    }
}