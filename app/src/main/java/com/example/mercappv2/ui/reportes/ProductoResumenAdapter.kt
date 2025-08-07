package com.example.mercappv2.ui.reportes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.R
import com.example.mercappv2.model.Producto

class ProductoResumenAdapter(private var lista: List<Producto>) :
    RecyclerView.Adapter<ProductoResumenAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.textNombre)
        val categoria: TextView = view.findViewById(R.id.textCategoria)
        val precio: TextView = view.findViewById(R.id.textPrecio)
        val stock: TextView = view.findViewById(R.id.textStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_resumen, parent, false)
        return ProductoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.categoria.text = "Categoría: ${producto.categoria}"
        holder.precio.text = "Precio: $${producto.precio}"
        holder.stock.text = "Stock: ${producto.stock}"
    }

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
