package com.example.mercappv2.model

data class Producto(
    val idProducto: Int,
    var nombre: String,
    var categoria: String,
    var precio: Float,
    var stock: Int,
    var vecesVendido: Int = 0
)
