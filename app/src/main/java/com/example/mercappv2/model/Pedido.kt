package com.example.mercappv2.model

import java.util.Date

data class Pedido(
    val idPedido: Int,
    val fecha: Date,
    val productos: List<Producto>,
    val estado: String, // "Pendiente" o "Entregado"
    val proveedor: Proveedor
)
