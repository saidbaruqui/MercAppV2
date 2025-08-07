package com.example.mercappv2.ui.reportes

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mercappv2.R
import com.example.mercappv2.model.Venta

class VentaResumenAdapter(private var lista: List<Venta>) :
    RecyclerView.Adapter<VentaResumenAdapter.VentaViewHolder>() {

    inner class VentaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val producto: TextView = view.findViewById(R.id.txtProducto)
        val cantidad: TextView = view.findViewById(R.id.txtCantidad)
        val precio: TextView = view.findViewById(R.id.txtPrecio)
        val fecha: TextView = view.findViewById(R.id.txtFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_venta_resumen, parent, false)
        return VentaViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val venta = lista[position]
        holder.producto.text = venta.producto
        holder.cantidad.text = "Cantidad: ${venta.cantidad}"
        holder.precio.text = "Precio: $${"%.2f".format(venta.precioUnitario * venta.cantidad)}"
        holder.fecha.text = "Fecha: ${venta.fecha.toLocalDate()}"
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(listaNueva: List<Venta>) {
        lista = listaNueva
        notifyDataSetChanged()
    }
}
