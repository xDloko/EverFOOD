package com.stormcode.everfood.firstMain


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.Carrito
import com.stormcode.everfood.firstMain.adapters.CarritoItem
import com.stormcode.everfood.firstMain.adapters.ProductAdapter
import com.stormcode.everfood.firstMain.api.RetrofitClient
import com.stormcode.everfood.firstMain.api.ProductoIdRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProductoViewModel(val carrito: Carrito) : ViewModel() {

    val productosList = mutableListOf<Producto>()


    val productAdapter = ProductAdapter(carrito, productosList) { producto, cantidad ->
        val carritoItem = CarritoItem(
            productId = producto._id,
            name = producto.name,
            price = producto.precio.toDouble(),
            quantity = cantidad
        )
        carrito.agregarProducto(carritoItem)
    }

    fun loadProducto(menuId: String) {
        viewModelScope.launch {
            try {
                val newProductos = RetrofitClient.authService.getProductos(ProductoIdRequest(menu_id = menuId))
                productosList.addAll(newProductos)
                productAdapter.notifyDataSetChanged()
            } catch (e: HttpException) {
                Log.e("API Error", "Error: ${e.code()}, ${e.message()}")
            } catch (e: Exception) {
                Log.e("API Failure", e.message ?: "Error desconocido")
            }
        }
    }
}



