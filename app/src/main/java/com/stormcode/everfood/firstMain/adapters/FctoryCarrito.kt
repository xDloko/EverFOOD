package com.stormcode.everfood.firstMain.adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stormcode.everfood.firstMain.CarritoViewModel

class CarritoViewModelFactory(private val storeId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(storeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}