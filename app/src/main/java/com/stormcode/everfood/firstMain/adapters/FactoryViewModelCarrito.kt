package com.stormcode.everfood.firstMain.adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stormcode.everfood.firstMain.ProductoViewModel

class ProductoViewModelFactory(private val carrito: Carrito) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            return ProductoViewModel(carrito) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}