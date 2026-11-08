package com.example.tutorvirtualesucar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tutorvirtualesucar.model.Etapa
import com.example.tutorvirtualesucar.model.PreguntaFrecuente

class TutorViewModel : ViewModel() {

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
}