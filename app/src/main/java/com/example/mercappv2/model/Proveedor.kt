package com.example.mercappv2.model

data class Proveedor(
    val idProveedor: Int,
    val nombreEmpresa: String,
    val telefono: String,
    val email: String,
    val productosSuministrados: List<Producto>
)
