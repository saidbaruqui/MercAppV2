package com.example.mercappv2.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercappv2.databinding.FragmentDashboardBinding
import com.example.mercappv2.data.HistorialVentasData
import com.example.mercappv2.data.InventarioData
import com.example.mercappv2.model.Venta
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var ventasAdapter: VentasRecientesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarVentasDelDia()
        mostrarStockBajo()
        mostrarVentasRecientes()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostrarVentasDelDia() {
        val hoy = LocalDate.now()
        val total = HistorialVentasData.listaVentas
            .filter { it.fecha.toLocalDate() == hoy }
            .sumOf { it.cantidad.toDouble() * it.precioUnitario }

        binding.tvVentasDia.text = "Ventas hoy: $${"%.2f".format(total)}"
    }

    private fun mostrarStockBajo() {
        val productosBajoStock = InventarioData.listaProductos.count { it.stock < 5 }
        binding.tvStockBajo.text = "Productos con stock bajo: $productosBajoStock"
    }

    private fun mostrarVentasRecientes() {
        val recientes = HistorialVentasData.listaVentas
            .sortedByDescending { it.fecha }
            .take(5)

        ventasAdapter = VentasRecientesAdapter(recientes)
        binding.recyclerVentasRecientes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerVentasRecientes.adapter = ventasAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
