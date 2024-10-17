package com.example.barbershopapp.newtwork

import android.provider.ContactsContract.RawContacts.Data
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

//aqui nose si es necesario pasarle ninguna fecha creo que no pq ya se crea en el backend
data class CorteBarbero(
    val idcortebarbero: Int,
    var corte: Corte,
    var barbero: Barbero,
    var precioFinal: Double
)

interface CorteBarberoApiService {
    @GET("cortebarbero")
    suspend fun getCortesBarberos(): List<CorteBarbero>

    @GET("cortebarbero/barbero/{idbarbero}")
    suspend fun getCortesByBarbero(@Path("id") idbarbero: Int): List<CorteBarbero>

    @GET("cortebarbero/barbero/{idbarbero}/dia")
    suspend fun getCortesByBarberoAndDay(@Path("id") idbarbero: Int, @Query("fecha") fecha: String): List<CorteBarbero>

    @GET("cortebarbero/barbero/{idbarbero}/semana")
    suspend fun getCortesByBarberoAndWeek(@Path("id") idbarbero: Int, @Query("inicio") inicio: String, @Query("fin") fin: String): List<CorteBarbero>

    @GET("cortebarbero/barbero/{idbarbero}/mes")
    suspend fun getCortesByBarberoAndMonth(@Path("id") idbarbero: Int, @Query("mes") mes: Int, @Query("anio") anio: Int): List<CorteBarbero>


    //create
    @POST("cortebarbero")
    suspend fun createCorteBarbero(@Body newCorteBarbero: CorteBarbero): CorteBarbero


    //updateprice
    @PUT("cortes/{id}/precioFinal")
    suspend fun updateCorteBarberoPrecio(@Path("id") id: Int, @Body nuevoPrecioFinal: Double): CorteBarbero


}
