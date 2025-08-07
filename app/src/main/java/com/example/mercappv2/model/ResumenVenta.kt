package com.example.mercappv2.model


data class ResumenVenta(
    val periodo: String, // Ej: "Hoy", "Esta semana", "Este mes"
    val total: Double
)
