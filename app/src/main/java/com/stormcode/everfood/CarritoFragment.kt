package com.stormcode.everfood

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.CarritoViewModel

import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoAdapter
import com.stormcode.everfood.firstMain.adapters.CarritoViewModelFactory

class CarritoFragment : Fragment() {

    private lateinit var carritoViewModel: CarritoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storeId = arguments?.getString("tienda_id") ?: ""
        carritoViewModel = ViewModelProvider(requireActivity(), CarritoViewModelFactory(storeId!!)).get(CarritoViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_carritofragment, container, false)


        val checkBox1: CheckBox = root.findViewById(R.id.checkBoxDomicilio)
        val checkBox2: CheckBox = root.findViewById(R.id.checkBoxRecolecion)
        val editTextDomicilio: EditText = root.findViewById(R.id.inputDireccion)
        val editTextTelefono: EditText = root.findViewById(R.id.inputTelefono)
        val editTextNombreRecibe: EditText = root.findViewById(R.id.inputNombreRecibe)
        val finalizarPedidoButton: Button = root.findViewById(R.id.finalizarPedidoButton)
        val total: TextView = root.findViewById(R.id.total)
        recyclerView = root.findViewById(R.id.recyclerViewCarrito)
        totalTextView = root.findViewById(R.id.totalTextView)
        


        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                editTextDomicilio.visibility = View.VISIBLE
                editTextTelefono.visibility = View.VISIBLE
                editTextNombreRecibe.visibility = View.VISIBLE

                checkBox2.isChecked = false
            } else {

                editTextDomicilio.visibility = View.GONE
                editTextTelefono.visibility = View.GONE
                editTextNombreRecibe.visibility = View.GONE
            }
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                editTextDomicilio.visibility = View.GONE
                editTextTelefono.visibility = View.GONE
                editTextNombreRecibe.visibility = View.GONE

                checkBox1.isChecked = false
            }
        }

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                recyclerView.visibility = View.GONE
                finalizarPedidoButton.visibility = View.GONE
                total.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
                finalizarPedidoButton.visibility = View.VISIBLE
                total.visibility = View.VISIBLE

            }
        }




        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CarritoAdapter(carritoViewModel.carrito.obtenerItems())

        totalTextView.text = "Total: ${carritoViewModel.carrito.obtenerTotal()}"

        val items = carritoViewModel.carrito.obtenerItems()
        Log.d("CarritoFragment", "Items en el carrito: ${items.size}")
        recyclerView.adapter = CarritoAdapter(items)

        carritoViewModel.calcularTotal()
        carritoViewModel.totalCarrito.observe(viewLifecycleOwner) { total ->
            totalTextView.text = "Total: $${"%.2f".format(total)}"
        }


        finalizarPedidoButton.setOnClickListener {
            finalizarPedido()
        }

        carritoViewModel.pedidoExitoso.observe(viewLifecycleOwner) { exito ->
            if (exito) {
                Toast.makeText(context, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()
                carritoViewModel.limpiarCarrito()
            } else {
                Toast.makeText(context, "Hubo un error al realizar el pedido", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun finalizarPedido() {
        val userId = context?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)?.getString("userId", null)

        if (userId == null) {
            Log.e("CarritoViewModel", "User ID no encontrado")
        } else {
            carritoViewModel.enviarPedido(userId)
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(storeId: String) =
            CarritoFragment().apply {
                arguments = Bundle().apply {
                    putString("tienda_id", storeId) // Asegúrate de pasar el storeId aquí
                }
            }
    }
}
