package com.stormcode.everfood

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormcode.everfood.firstMain.Pedido
import com.stormcode.everfood.firstMain.adapters.PedidoAdapter
import com.stormcode.everfood.firstMain.api.AuthService
import com.stormcode.everfood.firstMain.api.RetrofitClient
import com.stormcode.everfood.firstMain.api.UsuarioRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class pedidosFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter
    private lateinit var apiService: AuthService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewComidas)
        recyclerView.layoutManager = LinearLayoutManager(context)


        apiService = RetrofitClient.authService
        obtenerPedidos()

        return view
    }

    private fun obtenerPedidos() {
        val userId = context?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)?.getString("userId", null)
        if (userId == null) {
            Toast.makeText(context, "Usuario no identificado", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UsuarioRequest(user_id = userId)


        RetrofitClient.authService.obtenerPedidos(request).enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body() ?: emptyList()
                    Log.d("PedidosFragment", "Pedidos obtenidos: ${pedidos.size}")
                    adapter = PedidoAdapter(pedidos)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("PedidosFragment", "Error al obtener los pedidos: ${response.code()}")
                    Toast.makeText(context, "Error al obtener los pedidos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Log.e("PedidosFragment", "Error en la solicitud de pedidos: ${t.message}")
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}