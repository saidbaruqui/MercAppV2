package com.example.mercappv2.ui.ventas

import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.R
import com.example.mercappv2.model.Producto

class ProductoVentaAdapter(
    private val lista: List<Producto>,
    private val onAgregarClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoVentaAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.textNombre)
        val precio: TextView = view.findViewById(R.id.textPrecio)
        val stock: TextView = view.findViewById(R.id.textStock)
        val btnAgregar: Button = view.findViewById(R.id.btnAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_venta, parent, false)
        return ProductoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = "$${producto.precio}"
        holder.stock.text = "Stock: ${producto.stock}"
        holder.btnAgregar.setOnClickListener { onAgregarClick(producto) }
    }
}
