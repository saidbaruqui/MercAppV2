package com.example.mercappv2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.databinding.ItemVentaRecienteBinding
import com.example.mercappv2.model.Venta

class VentasRecientesAdapter(private var lista: List<Venta>) :
    RecyclerView.Adapter<VentasRecientesAdapter.VentaViewHolder>() {

    inner class VentaViewHolder(val binding: ItemVentaRecienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val binding = ItemVentaRecienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val venta = lista[position]
        with(holder.binding) {
            tvNombreProducto.text = venta.producto
            tvCantidadVendida.text = "Cantidad: ${venta.cantidad}"
            val total = venta.precioUnitario * venta.cantidad
            tvTotalVenta.text = "Total: $${"%.2f".format(total)}"
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Venta>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
