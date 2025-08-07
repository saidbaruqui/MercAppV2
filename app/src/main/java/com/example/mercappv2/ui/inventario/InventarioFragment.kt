package com.example.mercappv2.ui.inventario

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercappv2.R
import com.example.mercappv2.data.InventarioData
import com.example.mercappv2.data.InventarioPrefs
import com.example.mercappv2.databinding.FragmentInventarioBinding
import com.example.mercappv2.model.Producto

class InventarioFragment : Fragment() {

    private var _binding: FragmentInventarioBinding? = null
    private val binding get() = _binding!!

    private lateinit var adaptador: ProductoAdapter
    private val listaProductos get() = InventarioData.listaProductos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar inventario guardado o por defecto
        InventarioData.listaProductos = InventarioPrefs.cargarInventario(requireContext())

        adaptador = ProductoAdapter(listaProductos,
            onEditar = { producto -> mostrarDialogoEditar(producto) },
            onEliminar = { producto -> eliminarProducto(producto) },
            onPedir = { producto ->
                Toast.makeText(requireContext(), "Pedido solicitado para ${producto.nombre}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProductos.adapter = adaptador

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtro = s.toString().lowercase()
                val filtrados = listaProductos.filter {
                    it.nombre.lowercase().contains(filtro)
                }
                adaptador.actualizarLista(filtrados)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.fabAgregarProducto.setOnClickListener {
            mostrarDialogoAgregar()
        }
    }

    private fun mostrarDialogoAgregar() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_producto, null)

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val spinnerCategoria = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)
        val etPrecio = dialogView.findViewById<EditText>(R.id.etPrecio)
        val etStock = dialogView.findViewById<EditText>(R.id.etStock)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btnAgregar)

        val categorias = arrayOf("Seleccionar categoría", "Fruta", "Lácteos", "Panadería", "Granos")
        spinnerCategoria.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnAgregar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val categoria = spinnerCategoria.selectedItem.toString()
            val precio = etPrecio.text.toString().toFloatOrNull() ?: 0f
            val stock = etStock.text.toString().toIntOrNull() ?: 0

            if (nombre.isBlank() || categoria == "Seleccionar categoría") {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevo = Producto(
                idProducto = (listaProductos.maxOfOrNull { it.idProducto } ?: 0) + 1,
                nombre = nombre,
                categoria = categoria,
                precio = precio,
                stock = stock
            )

            listaProductos.add(nuevo)
            adaptador.actualizarLista(listaProductos)
            InventarioPrefs.guardarInventario(requireContext(), listaProductos)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogoEditar(producto: Producto) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_producto, null)

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val spinnerCategoria = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)
        val etPrecio = dialogView.findViewById<EditText>(R.id.etPrecio)
        val etStock = dialogView.findViewById<EditText>(R.id.etStock)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btnAgregar)

        val categorias = arrayOf("Seleccionar categoría", "Fruta", "Lácteos", "Panadería", "Granos")
        spinnerCategoria.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias)

        etNombre.setText(producto.nombre)
        etPrecio.setText(producto.precio.toString())
        etStock.setText(producto.stock.toString())
        spinnerCategoria.setSelection(categorias.indexOf(producto.categoria))

        btnAgregar.text = "Actualizar"

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnAgregar.setOnClickListener {
            producto.nombre = etNombre.text.toString()
            producto.categoria = spinnerCategoria.selectedItem.toString()
            producto.precio = etPrecio.text.toString().toFloatOrNull() ?: 0f
            producto.stock = etStock.text.toString().toIntOrNull() ?: 0

            adaptador.actualizarLista(listaProductos)
            InventarioPrefs.guardarInventario(requireContext(), listaProductos)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun eliminarProducto(producto: Producto) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar producto")
            .setMessage("¿Seguro que deseas eliminar \"${producto.nombre}\"?")
            .setPositiveButton("Sí") { _, _ ->
                listaProductos.remove(producto)
                adaptador.actualizarLista(listaProductos)
                InventarioPrefs.guardarInventario(requireContext(), listaProductos)
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.actualizarLista(listaProductos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
