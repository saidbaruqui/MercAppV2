package com.example.mercappv2.model

class Administrador(
    idUsuario: Int,
    nombre: String,
    correo: String,
    contraseña: String,
    val permisos: List<String>,
    val puedeEditarInventario: Boolean,
    val puedeVerEstadísticas: Boolean,
    val puedeEmitirFacturas: Boolean,
    val puedeGestionarUsuarios: Boolean
) : Usuario(idUsuario, nombre, correo, contraseña, rol = "admin")
