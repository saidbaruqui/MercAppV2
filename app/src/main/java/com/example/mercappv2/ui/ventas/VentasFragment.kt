package com.example.mercappv2.ui.ventas

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercappv2.data.HistorialVentasData
import com.example.mercappv2.data.InventarioData
import com.example.mercappv2.data.InventarioPrefs
import com.example.mercappv2.databinding.FragmentVentasBinding
import com.example.mercappv2.model.Producto
import com.example.mercappv2.model.Venta
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime

class VentasFragment : Fragment() {

    private var _binding: FragmentVentasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adaptadorProductos: ProductoVentaAdapter
    private lateinit var adaptadorCarrito: CarritoAdapter

    private val carrito: MutableList<Producto> = mutableListOf()
    private val inventario: MutableList<Producto> by lazy {
        InventarioPrefs.cargarInventario(requireContext()).toMutableList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentasBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adaptadorProductos = ProductoVentaAdapter(inventario) { producto ->
            agregarAlCarrito(producto)
        }

        adaptadorCarrito = CarritoAdapter(carrito)

        binding.recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProductos.adapter = adaptadorProductos

        binding.recyclerCarrito.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCarrito.adapter = adaptadorCarrito

        binding.btnFinalizarVenta.setOnClickListener {
            finalizarVenta()
        }

        actualizarTotal()
    }

    private fun agregarAlCarrito(producto: Producto) {
        if (producto.stock > 0) {
            producto.stock -= 1
            producto.vecesVendido += 1
            carrito.add(producto.copy(stock = 1)) // solo 1 unidad al carrito
            adaptadorProductos.notifyDataSetChanged()
            adaptadorCarrito.notifyDataSetChanged()
            actualizarTotal()
        } else {
            Snackbar.make(binding.root, "Sin stock disponible", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun actualizarTotal() {
        val total = carrito.sumOf { it.precio.toDouble() }
        binding.tvTotal.text = "Total: $${"%.2f".format(total)}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun finalizarVenta() {
        if (carrito.isEmpty()) {
            Toast.makeText(requireContext(), "Carrito vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val cliente = binding.etNombreCliente.text.toString().ifBlank { "Anónimo" }

        for (producto in carrito) {
            HistorialVentasData.listaVentas.add(
                Venta(
                    producto = producto.nombre,
                    cantidad = 1,
                    precioUnitario = producto.precio,
                    fecha = LocalDateTime.now()
                )
            )
        }

        // Guardar inventario actualizado
        InventarioPrefs.guardarInventario(requireContext(), inventario)

        Toast.makeText(requireContext(), "Venta registrada para $cliente", Toast.LENGTH_LONG).show()

        carrito.clear()
        adaptadorCarrito.notifyDataSetChanged()
        adaptadorProductos.notifyDataSetChanged()
        actualizarTotal()
        binding.etNombreCliente.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
