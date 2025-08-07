package com.example.mercappv2.data

import com.example.mercappv2.model.Producto
import com.example.mercappv2.model.Venta

object InventarioData {
    var listaProductos: MutableList<Producto> = mutableListOf(
        Producto(1, "Manzanas", "Fruta", 3.5f, 10),
        Producto(2, "Leche", "Lácteos", 2.5f, 5),
        Producto(3, "Pan", "Panadería", 1.2f, 8),
        Producto(4, "Arroz", "Granos", 4.0f, 12)
    )
}
