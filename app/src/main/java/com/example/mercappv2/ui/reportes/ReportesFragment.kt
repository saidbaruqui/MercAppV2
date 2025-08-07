package com.example.mercappv2.ui.reportes

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercappv2.data.HistorialVentasData
import com.example.mercappv2.data.HistorialVentasPrefs
import com.example.mercappv2.data.InventarioPrefs
import com.example.mercappv2.databinding.FragmentReportesBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class ReportesFragment : Fragment() {

    private var _binding: FragmentReportesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adaptadorMasVendidos: ProductoResumenAdapter
    private lateinit var adaptadorRotacion: ProductoResumenAdapter
    private lateinit var adaptadorVentasHoy: VentaResumenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productos = InventarioPrefs.cargarInventario(requireContext())

        // Productos más vendidos
        val masVendidos = productos
            .sortedByDescending { it.vecesVendido }
            .take(5)

        // Productos con más rotación
        val mayorRotacion = productos
            .sortedByDescending { it.vecesVendido / (it.stock + 1f) }
            .take(5)

        val ventasHoy = HistorialVentasPrefs.cargarHistorial(requireContext())
            .filter { it.fecha.toLocalDate() == LocalDate.now() }

        adaptadorMasVendidos = ProductoResumenAdapter(masVendidos)
        adaptadorRotacion = ProductoResumenAdapter(mayorRotacion)
        adaptadorVentasHoy = VentaResumenAdapter(ventasHoy)

        binding.recyclerMasVendidos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMasVendidos.adapter = adaptadorMasVendidos

        binding.recyclerRotacion.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRotacion.adapter = adaptadorRotacion

        binding.recyclerVentasPeriodo.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerVentasPeriodo.adapter = adaptadorVentasHoy

        binding.btnExportar.setOnClickListener {
            Snackbar.make(binding.root, "Exportación en desarrollo 📄", Snackbar.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        val productos = InventarioPrefs.cargarInventario(requireContext())

        adaptadorMasVendidos.actualizarLista(
            productos.sortedByDescending { it.vecesVendido }.take(5)
        )
        adaptadorRotacion.actualizarLista(
            productos.sortedByDescending { it.vecesVendido / (it.stock + 1f) }.take(5)
        )
        adaptadorVentasHoy.actualizar(
                HistorialVentasPrefs.cargarHistorial(requireContext())
                    .filter { it.fecha.toLocalDate() == LocalDate.now() }
                )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
