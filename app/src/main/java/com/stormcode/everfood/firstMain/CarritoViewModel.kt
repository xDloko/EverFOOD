package com.stormcode.everfood.firstMain

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoItem
import com.stormcode.everfood.firstMain.api.RetrofitClient
import kotlinx.coroutines.launch



class CarritoViewModel(private var storeId: String) : ViewModel() {
    var carrito: Carrito = Carrito(storeId)

    fun seleccionarTienda(nuevaStoreId: String) {
        if (storeId != nuevaStoreId) {
            storeId = nuevaStoreId
            carrito.limpiarCarrito()
        }
        Log.d("CarritoViewModel", "ID de tienda actual: $storeId")
    }

    fun agregarAlCarrito(producto: CarritoItem) {
        carrito.agregarProducto(producto)
    }

    fun limpiarCarrito() {
        carrito.limpiarCarrito()
    }

    fun enviarPedido(userId: String) {

        val items = carrito.obtenerItems()

        val total = items.sumOf { it.price * it.quantity }
        val productosRequest = items.map {
            ProductoRequest(
                producto_id = it.productId,
                cantidad = it.quantity,
                subtotal = it.price * it.quantity
            )
        }

        val pedido = PedidoRequest(
            tienda_id = storeId,
            user_id = userId,
            total = total,
            productos = productosRequest
        )

        // Llama a la API para enviar el pedido
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authService.enviarPedido(pedido)
                if (response.isSuccessful) {
                    Log.d("CarritoViewModel", "Pedido enviado exitosamente")
                } else {
                    Log.e("CarritoViewModel", "Error al enviar pedido: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CarritoViewModel", "Error al enviar pedido: ${e.message}")
            }
        }
    }

}
