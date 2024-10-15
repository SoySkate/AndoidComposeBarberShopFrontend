package com.example.barbershopapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.newtwork.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarberoViewModel : ViewModel() {
    // Estado para almacenar la lista de barberos
    private val _barberos = MutableStateFlow<List<Barbero>>(emptyList())
    val barberos: StateFlow<List<Barbero>> = _barberos

    // Función para cargar los barberos
    fun loadBarberos() {
        Log.d("BarberoViewModel", "Cargando barberos...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de barberos
                val barberosList = RetrofitInstance.api.getBarberos()
                _barberos.value = barberosList // Actualiza el estado
                Log.d("BarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("BarberoViewModel", "Barberos cargados: ${_barberos.value.size}")
            } catch (e: Exception) {
                Log.e("BarberoViewModel", "Error al cargar barberos: ${e.message}")
            }
        }
    }
}
