package com.stormcode.everfood.FirstMain

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val id: String, val email: String, val username: String, val token: String)


interface AuthService {

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    //@POST("/register")
    //fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    //@GET("/profile")
    //fun profile(): Call<ProfileResponse>
}