package com.example.barbershopapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.newtwork.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class BarberoViewModel : ViewModel() {
    // Estado para almacenar la lista de barberos
    private val _barberos = MutableStateFlow<List<Barbero>>(emptyList())
    val barberos: StateFlow<List<Barbero>> = _barberos

    // Estado para almacenar el barbero seleccionado
    private val _selectedBarbero = MutableStateFlow<Barbero?>(null)
    val selectedBarbero: StateFlow<Barbero?> = _selectedBarbero

    // Función para cargar los barberos
    fun loadBarberos() {
        Log.d("BarberoViewModel", "Cargando barberos...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de barberos
                val barberosList = RetrofitInstance.barberoApi.getBarberos()
                _barberos.value = barberosList // Actualiza el estado
                Log.d("BarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("BarberoViewModel", "Barberos cargados: ${_barberos.value.size}")
            } catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al cargar barberos: ${e.message}")
            }
        }
    }
    //null selectedbarber fun
    fun selectedBarberoNull(){
        _selectedBarbero.value = null
    }
    //funcion para crear Barbero
    fun createBarbero(barberoNombre: String){
        viewModelScope.launch {
        try{
            val newBarbero = Barbero(0, barberoNombre)
            val createdBarbero = RetrofitInstance.barberoApi.createBarbero(newBarbero)
            //Esto no me sirve para actualizar la lista de barberos nose porque despues de crear uno
            //_barberos.value = _barberos.value + newBarbero // Añadir el nuevo barbero a la lista existente
            loadBarberos()
            Log.d("BarberoViewModel", "Barbero creado: $createdBarbero")

        }catch (e: Exception) {
            Log.e("BarberoViewModel", "Error al cargar barberos: ${e.message}")
        }
        }
    }
    fun selectedBarbero(barbero:Barbero){
        _selectedBarbero.value = barbero
    }
    fun deleteBarbero(barbero: Barbero) {
        viewModelScope.launch {
            try{
                RetrofitInstance.barberoApi.deleteBarbero(barbero.idbarbero)
                Log.d("BarberoViewModel", "Deleting Barbero")
                loadBarberos()

                }catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al eliminar barbero: ${e.message}")
            }
        }
    }
    fun updateBarbero(barbero: Barbero) {
        viewModelScope.launch {
            try{
                //aqui hay que especificar que el contenido del body sea texto plano
                val requestBody =barbero.barberoNombre.toRequestBody("text/plain".toMediaType())
                RetrofitInstance.barberoApi.updateBarbero(barbero.idbarbero, requestBody)
                Log.d("BarberoViewModel", "Barbero Actualizado: $barbero")
                loadBarberos()

            }catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al actualizar barbero ${e.message}")
            }
        }
    }

}
