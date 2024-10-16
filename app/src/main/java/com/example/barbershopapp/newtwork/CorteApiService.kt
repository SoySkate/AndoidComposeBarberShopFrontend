package com.example.barbershopapp.newtwork

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class Corte(
    val idcorte: Int,
    var corteNombre: String,
    var precioDefecto: Double
)

interface CorteApiService {
    @GET("cortes")
    suspend fun getCortes(): List<Corte>

    @GET("cortes/{id}")
    suspend fun getCorte(@Path("id") id: Int): Corte

    @POST("cortes")
    suspend fun createCorte(@Body newCorte: Corte): Corte


    //aqui la mierda de pasarle un string en vez de un objeto
    @PUT("cortes/{id}/nombre")
    suspend fun updateCorteNombre(@Path("id") id: Int, @Body updatedCorte: RequestBody): Corte

    //aqui la mierda de pasarle un double en vez de un objeto
    @PUT("cortes/{id}/precio")
    suspend fun updateCortePrecio(@Path("id") id: Int, @Body updatedCorte: Double): Corte

    @DELETE("cortes/{id}")
    suspend fun deleteCorte(@Path("id") id: Int)
}
