package com.example.mercappv2.ui.inventario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.databinding.ItemProductoBinding
import com.example.mercappv2.model.Producto

class ProductoAdapter(
    private var lista: List<Producto>,
    val onEditar: (Producto) -> Unit,
    val onEliminar: (Producto) -> Unit,
    val onPedir: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        with(holder.binding) {
            textNombre.text = producto.nombre
            textCategoria.text = producto.categoria
            textPrecio.text = "$${producto.precio}"
            textStock.text = "Stock: ${producto.stock}"

            imgAdvertencia.visibility = if (producto.stock < 5) View.VISIBLE else View.GONE
            btnPedir.visibility = if (producto.stock < 5) View.VISIBLE else View.GONE

            btnEditar.setOnClickListener { onEditar(producto) }
            btnEliminar.setOnClickListener { onEliminar(producto) }
            btnPedir.setOnClickListener { onPedir(producto) }
        }
    }

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
