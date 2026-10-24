package com.example.tutorvirtualesucar.model

enum class EstadoEntrega {
    POR_INICIAR,
    EN_DESARROLLO,
    ENVIADO,
    APROBADO
}

data class Entrega(
    val id: Int,
    val etapaId: Int,
    val titulo: String,
    val fechaLimite: String,
    val estado: EstadoEntrega = EstadoEntrega.POR_INICIAR
)