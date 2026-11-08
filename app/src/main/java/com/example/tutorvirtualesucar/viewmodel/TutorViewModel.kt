package com.example.tutorvirtualesucar.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.tutorvirtualesucar.model.Entrega
import com.example.tutorvirtualesucar.model.EstadoEntrega
import com.example.tutorvirtualesucar.model.Etapa
import com.example.tutorvirtualesucar.model.PreguntaFrecuente

class TutorViewModel(application: Application) : AndroidViewModel(application) {

    val etapas: List<Etapa> = listOf(
        Etapa(
            id = 1,
            titulo = "Planteamiento del problema",
            descripcion = "Identifica el problema, a quién afecta y por qué es importante investigarlo.",
            formatoRequerido = "Documento escrito",
            fechaLimite = "Por definir"
        ),
        Etapa(
            id = 2,
            titulo = "Marco teórico",
            descripcion = "Reúne conceptos y antecedentes que fundamenten la investigación.",
            formatoRequerido = "Documento con referencias bibliográficas",
            fechaLimite = "Por definir"
        ),
        Etapa(
            id = 3,
            titulo = "Metodología",
            descripcion = "Describe cómo realizarás la investigación y recogerás la información.",
            formatoRequerido = "Documento escrito",
            fechaLimite = "Por definir"
        )
    )
    val preguntasFrecuentes = listOf(
        PreguntaFrecuente(
            id = 1,
            pregunta = "¿Cómo empiezo a plantear el problema?",
            categoria = "Metodología",
            respuesta = "Describe qué situación quieres investigar, a quién afecta y por qué es importante estudiarla."
        )
    )
    private val preferencias = application.getSharedPreferences(
        "avance_entregas",
        Context.MODE_PRIVATE
    )

    private val _entregas = mutableStateOf(
        etapas.map { etapa ->
            val estadoGuardado = preferencias.getString(
                "estado_${etapa.id}",
                null
            )

            Entrega(
                id = etapa.id,
                etapaId = etapa.id,
                titulo = etapa.titulo,
                fechaLimite = etapa.fechaLimite,
                estado = EstadoEntrega.entries.firstOrNull {
                    it.name == estadoGuardado
                } ?: EstadoEntrega.POR_INICIAR
            )
        }
    )

    val entregas: List<Entrega>
        get() = _entregas.value

    fun marcarEnDesarrollo(id: Int) {
        val entrega = entregas.firstOrNull { it.id == id } ?: return
        if (entrega.estado != EstadoEntrega.POR_INICIAR) return

        val nuevoEstado = EstadoEntrega.EN_DESARROLLO

        _entregas.value = entregas.map {
            if (it.id == id) it.copy(estado = nuevoEstado) else it
        }

        preferencias.edit()
            .putString("estado_$id", nuevoEstado.name)
            .apply()
    }
}