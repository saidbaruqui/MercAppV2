package com.example.mercappv2.ui.ventas

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.R
import com.example.mercappv2.model.Producto

class CarritoAdapter(private var lista: List<Producto>) :
    RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    inner class CarritoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.textNombre)
        val precio: TextView = view.findViewById(R.id.textPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = "$${producto.precio}"
    }
}
