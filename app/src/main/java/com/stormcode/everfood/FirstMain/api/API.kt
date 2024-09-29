package com.stormcode.everfood.FirstMain.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.stormcode.everfood.FirstMain.Store
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val id: String, val email: String, val username: String, val token: String)

data class RegisterRequest(val email: String, val password: String, val username: String)
data class RegisterResponse(val id: String, val username: String, val email: String)



interface AuthService {

    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/store/tienda-all")
    suspend fun getStores(@Query("limit") limit: Int, @Query("offset") offset: Int): List<Store>
}