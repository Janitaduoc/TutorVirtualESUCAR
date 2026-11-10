package com.example.tutorvirtualesucar.ui.screens

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.Calendar

class RecordatorioReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        mostrarRecordatorio(context)
    }
}

internal fun elegirFechaRecordatorio(contexto: Context) {
    val fecha = Calendar.getInstance()

    DatePickerDialog(
        contexto,
        { _, anio, mes, dia ->
            TimePickerDialog(
                contexto,
                { _, hora, minuto ->
                    fecha.set(anio, mes, dia, hora, minuto, 0)
                    fecha.set(Calendar.MILLISECOND, 0)

                    if (fecha.timeInMillis <= System.currentTimeMillis()) {
                        Toast.makeText(
                            contexto, "Elige una fecha y hora futuras.", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val aviso = PendingIntent.getBroadcast(
                            contexto,
                            1,
                            Intent(contexto, RecordatorioReceiver::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )

                        val alarmas = contexto.getSystemService(AlarmManager::class.java)
                        if (alarmas != null) {
                            alarmas.setAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP, fecha.timeInMillis, aviso
                            )
                            Toast.makeText(
                                contexto, "Recordatorio programado.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                fecha.get(Calendar.HOUR_OF_DAY),
                fecha.get(Calendar.MINUTE),
                true
            ).show()
        },
        fecha.get(Calendar.YEAR),
        fecha.get(Calendar.MONTH),
        fecha.get(Calendar.DAY_OF_MONTH)
    ).show()
}