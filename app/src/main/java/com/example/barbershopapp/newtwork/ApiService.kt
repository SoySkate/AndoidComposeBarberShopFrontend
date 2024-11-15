// ApiService.kt
package com.example.barbershopapp.newtwork

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

////Aqui recordar que los atributos de la class tienen que ser igaules a la DB
data class Barbero(
    val idbarbero: Int,
    var barberoNombre: String
)

interface ApiService {
    // GET para obtener la lista de barberos
    @GET("barberos")
    suspend fun getBarberos(): List<Barbero>

    // GET para obtener un barbero específico por ID
    @GET("barberos/{id}")
    suspend fun getBarbero(@Path("id") id: Int): Barbero

    // POST para crear un nuevo barbero
    @POST("barberos")
    suspend fun createBarbero(@Body newBarbero: Barbero): Barbero

    // PUT para actualizar un barbero existente
    //en este caso hay que usar requestbody en vez de string pq no parsea bien y se
    //duplican las comillas de string
    @PUT("barberos/{id}")
    suspend fun updateBarbero(@Path("id") id: Int, @Body updatedBarbero: RequestBody): Barbero

    // DELETE para eliminar un barbero por ID
    @DELETE("barberos/{id}")
    suspend fun deleteBarbero(@Path("id") id: Int)

}
