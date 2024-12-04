package com.stormcode.everfood.firstMain

data class Pedido(
    val tienda_id: Tienda,
    val user_id: String,
    val estado: String,
    val total: Double,
    val fecha: String,
    val productos: List<Productos>
)

data class Tienda(
    val name: String
)

data class Productos(
    val producto_id: ProductoDetalle,
    val cantidad: Int,
    val subtotal: Double
)

data class ProductoDetalle(
    val name: String
)