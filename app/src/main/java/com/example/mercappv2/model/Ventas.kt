package com.example.mercappv2.model

import java.time.LocalDateTime

data class Venta(
    val producto: String,
    val cantidad: Int,
    val precioUnitario: Float,
    val fecha: LocalDateTime // puedes usar Date si prefieres
)
