package com.stormcode.everfood

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

        recyclerView = root.findViewById(R.id.recyclerViewCarrito)
        totalTextView = root.findViewById(R.id.totalTextView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CarritoAdapter(carritoViewModel.carrito.obtenerItems())

        totalTextView.text = "Total: ${carritoViewModel.carrito.obtenerTotal()}"

        root.findViewById<Button>(R.id.finalizarPedidoButton).setOnClickListener {
            finalizarPedido()
        }

        val items = carritoViewModel.carrito.obtenerItems()
        Log.d("CarritoFragment", "Items en el carrito: ${items.size}")
        recyclerView.adapter = CarritoAdapter(items)

        val finalizarPedidoButton: Button = root.findViewById(R.id.finalizarPedidoButton)
        finalizarPedidoButton.setOnClickListener {
            finalizarPedido()
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
