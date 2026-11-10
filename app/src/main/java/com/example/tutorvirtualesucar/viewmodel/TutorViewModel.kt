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
            fechaLimite = "Por definir",
            pautas = listOf(
                "Delimita el problema e identifica a quién afecta.",
                "Explica por qué es relevante investigarlo."
            )
        ),
        Etapa(
            id = 2,
            titulo = "Marco teórico",
            descripcion = "Reúne conceptos y antecedentes que fundamenten la investigación.",
            formatoRequerido = "Documento con referencias bibliográficas",
            fechaLimite = "Por definir",
            pautas = listOf(
                "Define los conceptos principales.",
                "Usa fuentes pertinentes y cita las referencias."
            )
        ),
        Etapa(
            id = 3,
            titulo = "Metodología",
            descripcion = "Describe cómo realizarás la investigación.",
            formatoRequerido = "Documento escrito",
            fechaLimite = "Por definir",
            pautas = listOf(
                "Explica cómo recogerás la información.",
                "Justifica por qué ese método responde a tu pregunta."
            )
        ),
        Etapa(
            id = 4,
            titulo = "Recogida de información",
            descripcion = "Aplica los instrumentos y organiza la información obtenida.",
            formatoRequerido = "Registro de la información recogida",
            fechaLimite = "Por definir",
            pautas = listOf(
                "Registra cuándo y cómo obtuviste la información.",
                "Organiza los datos y resguarda la identidad de los participantes."
            )
        ),
        Etapa(
            id = 5,
            titulo = "Análisis de resultados",
            descripcion = "Interpreta los hallazgos y relaciónalos con los objetivos.",
            formatoRequerido = "Informe de resultados",
            fechaLimite = "Por definir",
            pautas = listOf(
                "Presenta los hallazgos con evidencia.",
                "Explica cómo se relacionan con los objetivos del trabajo."
            )
        )
    )
    val preguntasFrecuentes = listOf(
        PreguntaFrecuente(
            id = 1,
            pregunta = "¿Cómo empiezo a plantear el problema?",
            categoria = "Metodología",
            respuesta = "Describe qué situación quieres investigar, a quién afecta y por qué es importante estudiarla."
        ),
        PreguntaFrecuente(
            id = 2,
            pregunta = "¿Cuándo debo incluir una citación?",
            categoria = "Citación",
            respuesta = "Cita la fuente cuando uses ideas, datos o palabras de otra persona."
        ),
        PreguntaFrecuente(
            id = 3,
            pregunta = "¿Cómo organizo el informe?",
            categoria = "Formato",
            respuesta = "Usa títulos claros y revisa el formato solicitado para cada etapa."
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
        if (entregas.any { it.id == id && it.estado == EstadoEntrega.POR_INICIAR }) {
            actualizarEstadoEntrega(id, EstadoEntrega.EN_DESARROLLO)
        }
    }

    fun actualizarEstadoEntrega(id: Int, nuevoEstado: EstadoEntrega) {
        val entrega = entregas.firstOrNull { it.id == id } ?: return

        if (entrega.estado == EstadoEntrega.APROBADO ||
            nuevoEstado == EstadoEntrega.APROBADO ||
            entrega.estado == nuevoEstado
        ) return

        _entregas.value = entregas.map {
            if (it.id == id) it.copy(estado = nuevoEstado) else it
        }

        preferencias.edit()
            .putString("estado_$id", nuevoEstado.name)
            .apply()
    }
}