package com.example.tutorvirtualesucar.ui.screens

import android.Manifest
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.tutorvirtualesucar.model.Entrega
import com.example.tutorvirtualesucar.model.EstadoEntrega
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EntregasScreen(
    entregas: List<Entrega>,
    onMarcarEnDesarrollo: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current
    val preferenciasFechas = remember(contexto) {
        contexto.getSharedPreferences("fechas_entregas", Context.MODE_PRIVATE)
    }

    val fechasLimite = remember(preferenciasFechas) {
        mutableStateMapOf<String, String>().apply {
            preferenciasFechas.all.forEach { (id, valor) ->
                if (valor is String) put(id, valor)
            }
        }
    }

    val entregasOrdenadas = entregas.sortedBy { entrega ->
        fechasLimite[entrega.id.toString()] ?: "9999-12-31"
    }

    val pedirPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) elegirFechaRecordatorio(contexto)
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Mis entregas", style = MaterialTheme.typography.headlineMedium)
        }

        item {
            Button(onClick = {
                val faltaPermiso =
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(
                                contexto,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED

                if (faltaPermiso) {
                    pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    elegirFechaRecordatorio(contexto)
                }
            }) {
                Text("Programar recordatorio")
            }
        }

        items(entregasOrdenadas, key = { it.id }) { entrega ->
            val id = entrega.id.toString()
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

                    FechaLimiteEntrega(
                        fechaGuardada = fechasLimite[id],
                        fechaOriginal = entrega.fechaLimite,
                        onCambiarFecha = { nuevaFecha ->
                            preferenciasFechas.edit().putString(id, nuevaFecha).apply()
                            fechasLimite[id] = nuevaFecha
                        }
                    )

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

@Composable
private fun FechaLimiteEntrega(
    fechaGuardada: String?,
    fechaOriginal: String,
    onCambiarFecha: (String) -> Unit
) {
    val contexto = LocalContext.current
    val fechaVisible = fechaGuardada
        ?.split("-")
        ?.reversed()
        ?.joinToString("/")
        ?: fechaOriginal

    Text("Fecha límite: $fechaVisible")

    val formato = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val limite = Calendar.getInstance()
    val hoy = formato.format(limite.time)
    limite.add(Calendar.DAY_OF_MONTH, 3)
    val enTresDias = formato.format(limite.time)

    if (fechaGuardada != null && fechaGuardada >= hoy && fechaGuardada <= enTresDias) {
        Text(
            text = "Vence pronto",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelLarge
        )
    }

    TextButton(onClick = {
        val calendario = Calendar.getInstance()
        fechaGuardada?.split("-")?.let { partes ->
            calendario.set(
                partes[0].toInt(),
                partes[1].toInt() - 1,
                partes[2].toInt()
            )
        }

        DatePickerDialog(
            contexto,
            { _, anio, mes, dia ->
                val mesTexto = (mes + 1).toString().padStart(2, '0')
                val diaTexto = dia.toString().padStart(2, '0')
                onCambiarFecha("$anio-$mesTexto-$diaTexto")
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(if (fechaGuardada == null) "Elegir fecha límite" else "Cambiar fecha límite")
    }
}

internal fun mostrarRecordatorio(contexto: Context) {
    val canalId = "recordatorios_entregas"
    val gestor = contexto.getSystemService(NotificationManager::class.java) ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        gestor.createNotificationChannel(
            NotificationChannel(
                canalId,
                "Recordatorios de entregas",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
    }

    val notificacion = NotificationCompat.Builder(contexto, canalId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Tutor Virtual ESUCAR")
        .setContentText("Revisa tus entregas pendientes.")
        .build()

    gestor.notify(1, notificacion)
}