package com.stormcode.everfood.firstMain.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api-server-production-12d3.up.railway.app/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }


}
