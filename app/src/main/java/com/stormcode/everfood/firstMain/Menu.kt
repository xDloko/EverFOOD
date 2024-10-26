package com.stormcode.everfood.firstMain

data class Menu(

    val id:Int,
    val tienda_id: Int,
    val name: String,

) {
    companion object {
        const val tienda_id = "tienda_id"

    }
}
