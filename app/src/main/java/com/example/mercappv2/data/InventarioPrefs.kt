package com.example.mercappv2.data

import android.content.Context
import com.example.mercappv2.model.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object InventarioPrefs {
    private const val PREFS_NAME = "inventario_prefs"
    private const val KEY_INVENTARIO = "lista_productos"

    fun guardarInventario(context: Context, lista: List<Producto>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(lista)
        prefs.edit().putString(KEY_INVENTARIO, json).apply()
    }

    fun cargarInventario(context: Context): MutableList<Producto> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_INVENTARIO, null)

        return if (json != null) {
            val tipo = object : TypeToken<MutableList<Producto>>() {}.type
            Gson().fromJson(json, tipo)
        } else {
            mutableListOf(
                Producto(1, "Manzanas", "Fruta", 3.5f, 10),
                Producto(2, "Leche", "Lácteos", 2.5f, 5),
                Producto(3, "Pan", "Panadería", 1.2f, 8),
                Producto(4, "Arroz", "Granos", 4.0f, 12)
            )
        }
    }
}
