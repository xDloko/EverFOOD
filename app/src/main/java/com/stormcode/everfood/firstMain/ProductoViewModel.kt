package com.stormcode.everfood.firstMain


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.ProductAdapter
import com.stormcode.everfood.firstMain.api.RetrofitClient
import com.stormcode.everfood.firstMain.api.ProductoIdRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProductoViewModel : ViewModel() {

    private val productosList = mutableListOf<Producto>()
    val ProductAdapter = ProductAdapter(productosList)

    fun loadProducto(cartaId: String) {
        viewModelScope.launch {
            try {
                val newProductos = RetrofitClient.authService.getProductos(ProductoIdRequest(carta_id = cartaId))
                productosList.addAll(newProductos)
                ProductAdapter.notifyDataSetChanged()
            } catch (e: HttpException) {
                // Manejar errores HTTP
                Log.e("API Error", "Error: ${e.code()}, ${e.message()}")
            } catch (e: Exception) {
                // Manejar errores generales
                Log.e("API Failure", e.message ?: "Error desconocido")
            }
        }
    }
}


