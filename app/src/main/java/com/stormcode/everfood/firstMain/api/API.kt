package com.stormcode.everfood.firstMain.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.stormcode.everfood.firstMain.Store
import com.stormcode.everfood.firstMain.Menu
import com.stormcode.everfood.firstMain.Message
import com.stormcode.everfood.firstMain.MessagesResponse
import com.stormcode.everfood.firstMain.Pedido
import com.stormcode.everfood.firstMain.PedidoRequest
import com.stormcode.everfood.firstMain.Producto
import retrofit2.Response
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val id: String, val email: String, val username: String, val token: String)

data class RegisterRequest(val email: String, val password: String, val username: String)
data class RegisterResponse(val id: String, val username: String, val email: String)

data class TiendaIdRequest(val tienda_id: String)

data class ProductoIdRequest(val menu_id: String)

data class UsuarioRequest(val user_id: String)



interface AuthService {

    @POST("api/user/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/user/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/system/system-stores")
    suspend fun getStores(@Query("limit") limit: Int, @Query("offset") offset: Int): List<Store>

    @POST("api/store/tienda-vermenu")
    suspend fun getMenus(@Body storeId: TiendaIdRequest): List<Menu>

    @POST("api/store/tienda-verproductos")
    suspend fun getProductos(@Body menuId: ProductoIdRequest): List<Producto>

    @POST("api/user/crear-pedido")
    suspend fun enviarPedido(@Body pedido: PedidoRequest): Response<Void>

    @POST("/api/chats/mensajes")
    fun sendMessage(@Body message: Map<String, String>): Call<Void>

    @GET("/api/chats/users")
    fun getUsers(): Call<List<String>>

    @GET("/api/chats/messages")
    fun getMessages(
        @Query("user1") user1: String,
        @Query("user1Model") user1Model: String,
        @Query("user2") user2: String,
        @Query("user2Model") user2Model: String
    ): Call<MessagesResponse>


    @POST("/api/user/obtener-pedidos")
    fun obtenerPedidos(@Body request: UsuarioRequest): Call<List<Pedido>>




}


