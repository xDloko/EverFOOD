package com.stormcode.everfood.firstMain.adapters

import android.util.Log
import java.io.Serializable

data class CarritoItem(
    val productId: String,
    val name: String,
    val price: Double,
    var quantity: Int
)

class Carrito(val storeId: String): Serializable {
    private val items: MutableList<CarritoItem> = mutableListOf()

    val storeid = storeId

    fun agregarProducto(producto: CarritoItem) {
        val existingItem = items.find { it.productId == producto.productId }
        if (existingItem != null) {
            existingItem.quantity += producto.quantity
        } else {
            items.add(producto)
        }
        Log.d("Carrito", "Producto agregado: ${producto.name}, Cantidad: ${producto.quantity}")
        val items = obtenerItems()
        Log.d("items", "Items en el carrito: ${items.size}")
    }

    fun eliminarProducto(productId: String) {
        items.removeAll { it.productId == productId }
    }

    fun obtenerTotal(): Double {
        return items.sumOf { it.price * it.quantity }
    }

    fun obtenerItems(): List<CarritoItem> = items

    fun limpiarCarrito() {
        items.clear()

    }


}
