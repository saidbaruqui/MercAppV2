package com.example.mercappv2.ui.pedidos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.mercappv2.R
import com.google.android.material.chip.Chip
import java.util.*

class PedidosFragment : Fragment() {

    private lateinit var textFecha: TextView
    private lateinit var spinnerProveedor: Spinner
    private lateinit var chipsProductos: LinearLayout
    private lateinit var switchEntregado: Switch
    private lateinit var btnGuardar: Button
    private lateinit var btnAgregarProveedor: ImageButton

    private lateinit var proveedorAdapter: ArrayAdapter<String>

    private var listaProveedores = mutableListOf("Seleccionar proveedor", "Distribuidora A", "Proveedor B")
    private var listaProductos = listOf("Leche", "Pan", "Arroz", "Huevos")

    private val productosSeleccionados = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        // Referencias
        textFecha = view.findViewById(R.id.textFecha)
        spinnerProveedor = view.findViewById(R.id.spinnerProveedor)
        chipsProductos = view.findViewById(R.id.chipsProductos)
        switchEntregado = view.findViewById(R.id.switchEntregado)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnAgregarProveedor = view.findViewById(R.id.fabAgregarProveedor)

        // Adaptador del Spinner
        proveedorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaProveedores)
        spinnerProveedor.adapter = proveedorAdapter

        // Fecha
        textFecha.setOnClickListener {
            mostrarSelectorFecha()
        }

        // Chips de productos
        mostrarChipsProductos()

        // Guardar pedido
        btnGuardar.setOnClickListener {
            guardarPedido()
        }

        // Abrir diálogo para agregar proveedor
        btnAgregarProveedor.setOnClickListener {
            mostrarDialogoAgregarProveedor()
        }

        return view
    }

    private fun mostrarDialogoAgregarProveedor() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_proveedor, null)
        builder.setView(view)

        val etNombreEmpresa = view.findViewById<EditText>(R.id.etNombreEmpresa)
        val etTelefono = view.findViewById<EditText>(R.id.etTelefono)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val chipGroup = view.findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipGroupProductosProveedor)
        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarProveedor)

        // Cargar productos en chips
        chipGroup.removeAllViews()
        for (producto in listaProductos) {
            val chip = Chip(requireContext())
            chip.text = producto
            chip.isCheckable = true
            chipGroup.addView(chip)
        }

        val dialog = builder.create()

        btnAgregar.setOnClickListener {
            val nombre = etNombreEmpresa.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val productos = mutableListOf<String>()

            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) productos.add(chip.text.toString())
            }

            if (nombre.isBlank() || telefono.isBlank() || email.isBlank() || productos.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Proveedor agregado: $nombre\nProductos: ${productos.joinToString()}",
                    Toast.LENGTH_LONG
                ).show()

                // Agregar al spinner y actualizar
                listaProveedores.add(nombre)
                proveedorAdapter.notifyDataSetChanged()
                spinnerProveedor.setSelection(listaProveedores.lastIndex)

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun mostrarSelectorFecha() {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
            val fecha = "$d/${m + 1}/$y"
            textFecha.text = fecha
        }, year, month, day)

        datePicker.show()
    }

    private fun mostrarChipsProductos() {
        chipsProductos.removeAllViews()
        productosSeleccionados.clear()

        for (producto in listaProductos) {
            val chip = Chip(requireContext())
            chip.text = producto
            chip.isCheckable = true
            chip.setPadding(16)

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    productosSeleccionados.add(producto)
                } else {
                    productosSeleccionados.remove(producto)
                }
            }

            chipsProductos.addView(chip)
        }
    }

    private fun guardarPedido() {
        val fecha = textFecha.text.toString()
        val proveedor = spinnerProveedor.selectedItem.toString()
        val entregado = switchEntregado.isChecked

        if (fecha == "Seleccionar fecha" || proveedor == "Seleccionar proveedor" || productosSeleccionados.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val resumen = """
            📦 Pedido guardado:
            • Fecha: $fecha
            • Proveedor: $proveedor
            • Productos: ${productosSeleccionados.joinToString(", ")}
            • Entregado: ${if (entregado) "Sí" else "No"}
        """.trimIndent()

        Toast.makeText(requireContext(), resumen, Toast.LENGTH_LONG).show()
    }
}
