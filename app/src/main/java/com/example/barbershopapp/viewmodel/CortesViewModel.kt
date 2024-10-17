package com.example.barbershopapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.newtwork.Corte
import com.example.barbershopapp.newtwork.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class CortesViewModel : ViewModel() {
    // Estado para almacenar la lista de cortes
    private val _cortes = MutableStateFlow<List<Corte>>(emptyList())
    val cortes: StateFlow<List<Corte>> = _cortes

    // Estado para almacenar el corte seleccionado
    private val _selectedCorte = MutableStateFlow<Corte?>(null)
    val selectedCorte: StateFlow<Corte?> = _selectedCorte

    // Estado para almacenar el booleano de corte  seleccionado
    private val _corteScreenOn = MutableStateFlow<Boolean?>(false)
    val corteScreenOn: StateFlow<Boolean?> = _corteScreenOn

    //Setear el bool
    fun setCorteScreenOn(value: Boolean) {
        _corteScreenOn.value = value
    }

    // Función para cargar los cortes
    fun loadCortes() {
        Log.d("CorteViewModel", "Cargando Cortes...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de barberos
                val cortesList = RetrofitInstance.corteApi.getCortes()
                _cortes.value = cortesList // Actualiza el estado
                Log.d("CorteViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CorteViewModel", "Cortes cargados: ${_cortes.value.size}")
            } catch (e: Exception) {
                Log.e("CorteViewModel", "Error al cargar Cortes: ${e.message}")
            }
        }
    }

    //funcion para crear Corte
    fun createCorte(corteNombre: String, precioDefault: Double){
        viewModelScope.launch {
            try{
                val newCorte = Corte(0, corteNombre,precioDefault)
                val createdBarbero = RetrofitInstance.corteApi.createCorte(newCorte)
                Log.d("CorteViewModel", "Corte creado: $createdBarbero")

            }catch (e: Exception) {
                Log.e("CorteViewModel", "Error al cargar Cortes: ${e.message}")
            }
        }
    }
    fun selectedCorte(corte:Corte){
        _selectedCorte.value = corte
    }
    fun deleteCorte(corte: Corte) {
        viewModelScope.launch {
            try{
                RetrofitInstance.corteApi.deleteCorte(corte.idcorte)
                Log.d("BarberoViewModel", "Deleting Barbero")

            }catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al eliminar barbero: ${e.message}")
            }
        }
    }
    fun updateCortePrecio(corte: Corte) {
        viewModelScope.launch {
            try{
                //aqui hay que especificar que el contenido del body sea texto plano
               // val requestBody =barbero.barberoNombre.toRequestBody("text/plain".toMediaType())
                RetrofitInstance.corteApi.updateCortePrecio(corte.idcorte, corte.precioDefecto)
                Log.d("BarberoViewModel", "Barbero Actualizado: $corte")

            }catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al actualizar barbero ${e.message}")
            }
        }
    }
    fun updateCorteNombre(corte: Corte) {
        viewModelScope.launch {
            try{
                //aqui hay que especificar que el contenido del body sea texto plano
                val requestBody =corte.corteNombre.toRequestBody("text/plain".toMediaType())
                RetrofitInstance.corteApi.updateCorteNombre(corte.idcorte, requestBody)
                Log.d("CorteViewModel", "Corte Actualizado: $corte")

            }catch (e: Exception) {
                Log.e("CorteViewModel", "Error al actualizar Corte ${e.message}")
            }
        }
    }

}
