package com.example.barbershopapp.newtwork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Define la URL base de tu API
    private const val BASE_URL = "http://192.168.0.16:8080/"

    // Crea una instancia de Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte JSON a objetos
            .build()
    }

    // Crea una instancia de ApiService
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}