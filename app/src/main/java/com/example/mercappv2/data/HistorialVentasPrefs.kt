package com.example.mercappv2.data

import android.content.Context
import com.example.mercappv2.model.Venta
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object HistorialVentasPrefs {
    private const val PREFS_NAME = "historial_ventas_prefs"
    private const val KEY_VENTAS = "lista_ventas"

    fun guardarHistorial(context: Context, ventas: List<Venta>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(ventas)
        prefs.edit().putString(KEY_VENTAS, json).apply()
    }

    fun cargarHistorial(context: Context): MutableList<Venta> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_VENTAS, null)

        return if (json != null) {
            val tipo = object : TypeToken<MutableList<Venta>>() {}.type
            Gson().fromJson(json, tipo)
        } else {
            mutableListOf()
        }
    }
}
