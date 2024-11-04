package com.stormcode.everfood.firstMain

data class PedidoRequest(
    val tienda_id: String,
    val user_id: String,
    val estado: String = "pendiente",
    val total: Double,
    val productos: List<ProductoRequest>
)

data class ProductoRequest(
    val producto_id: String,
    val cantidad: Int,
    val subtotal: Double
)