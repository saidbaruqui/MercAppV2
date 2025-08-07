package com.example.mercappv2.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun guardarSesion(idUsuario: Int, nombre: String, correo: String, rol: String) {
        val editor = prefs.edit()
        editor.putInt("idUsuario", idUsuario)
        editor.putString("nombre", nombre)
        editor.putString("correo", correo)
        editor.putString("rol", rol)
        editor.apply()
    }

    fun obtenerIdUsuario(): Int = prefs.getInt("idUsuario", -1)
    fun obtenerNombre(): String? = prefs.getString("nombre", null)
    fun obtenerCorreo(): String? = prefs.getString("correo", null)
    fun obtenerRol(): String? = prefs.getString("rol", null)

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }

    fun sesionActiva(): Boolean {
        return prefs.contains("idUsuario") && prefs.contains("rol")
    }
}
