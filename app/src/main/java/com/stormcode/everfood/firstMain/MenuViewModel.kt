package com.stormcode.everfood.firstMain


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.MenuAdapter
import com.stormcode.everfood.firstMain.api.RetrofitClient
import com.stormcode.everfood.firstMain.api.TiendaIdRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MenuViewModel : ViewModel() {

    private val menusList = mutableListOf<Menu>()
    val MenuAdapter = MenuAdapter(menusList)

    fun loadMenu(tiendaId: String) {
        viewModelScope.launch {

            try {
                val newMenus = RetrofitClient.authService.getMenus(TiendaIdRequest(tienda_id = tiendaId))
                menusList.addAll(newMenus)
                MenuAdapter.notifyDataSetChanged()
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


