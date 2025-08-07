package com.example.mercappv2.model

class Empleado(
    idUsuario: Int,
    nombre: String,
    correo: String,
    contraseña: String,
    val puedeRegistrarVentas: Boolean,
    val puedeVisualizarProductos: Boolean,
    val puedeGenerarAlertas: Boolean,
    val turno: String,
    val activo: Boolean
) : Usuario(idUsuario, nombre, correo, contraseña, rol = "empleado")
