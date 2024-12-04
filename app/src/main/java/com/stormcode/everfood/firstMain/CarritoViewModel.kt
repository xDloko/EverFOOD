package com.stormcode.everfood.firstMain

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoItem
import com.stormcode.everfood.firstMain.api.RetrofitClient
import kotlinx.coroutines.launch



class CarritoViewModel(private var storeId: String) : ViewModel() {
    var carrito: Carrito = Carrito(storeId)

    private val _pedidoExitoso = MutableLiveData<Boolean>()
    val pedidoExitoso: LiveData<Boolean> get() = _pedidoExitoso

    private val _totalCarrito = MutableLiveData<Double>()
    val totalCarrito: LiveData<Double> get() = _totalCarrito

    fun resetPedidoExitoso() {
        _pedidoExitoso.postValue(false)
    }

    init {
        calcularTotal()
    }

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

    fun calcularTotal() {
        val total = carrito.obtenerItems().sumOf { it.price * it.quantity }
        _totalCarrito.value = total
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
                    _pedidoExitoso.postValue(true)

                } else {
                    Log.e("CarritoViewModel", "Error al enviar pedido: ${response.errorBody()?.string()}")
                    _pedidoExitoso.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("CarritoViewModel", "Error al enviar pedido: ${e.message}")
                _pedidoExitoso.postValue(false)
            }
        }
    }

}
