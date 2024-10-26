package com.stormcode.everfood.firstMain


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormcode.everfood.firstMain.adapters.StoresAdapter
import com.stormcode.everfood.firstMain.api.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StoreViewModel : ViewModel() {

    private val storesList = mutableListOf<Store>()
    val storesAdapter = StoresAdapter(storesList)

    fun loadStores(limit: Int, offset: Int) {
        viewModelScope.launch {
            try {
                val newStores = RetrofitClient.authService.getStores(limit, offset)
                storesList.addAll(newStores)
                storesAdapter.notifyDataSetChanged()
            } catch (e: HttpException) {
                Log.e("API Error", "Error: ${e.code()}, ${e.message()}")
            } catch (e: Exception) {
                Log.e("API Failure", e.message ?: "Error desconocido")
            }
        }
    }
}