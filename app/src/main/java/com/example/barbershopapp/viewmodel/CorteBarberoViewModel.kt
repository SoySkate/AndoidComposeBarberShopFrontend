package com.example.barbershopapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.newtwork.CorteBarbero
import com.example.barbershopapp.newtwork.CorteBarberoRequest
import com.example.barbershopapp.newtwork.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class CorteBarberoViewModel : ViewModel() {
    //getAll
    private val _allCortesBarberos = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val allCortesBarberos: StateFlow<List<CorteBarbero>> = _allCortesBarberos

    //getCortesBybarbero
    private val _cortesBarbero = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val cortesBarbero: StateFlow<List<CorteBarbero>> = _cortesBarbero

    //getCortesBybarberoDia
    private val _cortesBarberoDia = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val cortesBarberoDia: StateFlow<List<CorteBarbero>> = _cortesBarberoDia

    //getCortesBybarberoWeek
    private val _cortesBarberoSemana = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val cortesBarberoSemana: StateFlow<List<CorteBarbero>> = _cortesBarberoSemana

    //getCortesBybarberoMonth
    private val _cortesBarberoMes = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val cortesBarberoMes: StateFlow<List<CorteBarbero>> = _cortesBarberoMes

    // Estado para almacenar la lista de cortes
    private val _cortes = MutableStateFlow<List<CorteBarbero>>(emptyList())
    val cortes: StateFlow<List<CorteBarbero>> = _cortes

    // Estado para almacenar el barbero seleccionado
    private val _selectedCortesBarbero = MutableStateFlow<CorteBarbero?>(null)
    val selectedCortesBarbero: StateFlow<CorteBarbero?> = _selectedCortesBarbero

    // Función para cargar todos los cortesBarberos
    fun loadAllCortesBarberos() {
        Log.d("CortesBarberoViewModel", "Cargando cortesBarbero...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de cortesBarbero
                val cortesBarberosList = RetrofitInstance.corteBarberoApi.getCortesBarberos()
                _allCortesBarberos.value = cortesBarberosList // Actualiza el estado
                Log.d("CortesBarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CortesBarberoViewModel", "cortesBarbero cargados: ${_allCortesBarberos.value.size}")
            } catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesBarberos: ${e.message}")
            }
        }
    }

    // Función para cargar los cortesBarbero
    fun loadCortesByBarbero(selectedIdBarbero: Barbero) {
        Log.d("CortesBarberoViewModel", "Cargando cortesBarbero...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de cortesBarbero
                var idBarbero = selectedIdBarbero.idbarbero
                val cortesBarberosList = RetrofitInstance.corteBarberoApi.getCortesByBarbero(idBarbero)
                _cortesBarbero.value = cortesBarberosList // Actualiza el estado
                Log.d("CortesBarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CortesBarberoViewModel", "cortesBarbero cargados: ${_cortesBarbero.value.size}")
            } catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesByBarberoID: ${e.message}")
            }
        }
    }

    //funcion para crear cortesBarbero
    fun createCortesBarbero(corteBarbero: CorteBarbero){
        Log.d("CortesBarberoViewModel", "CREANDO cortesBarbero...")
        viewModelScope.launch {
            try{
                val fechaActual = LocalDate.now().toString()
                val newCorteBarbero = CorteBarberoRequest(corteBarbero.corte.idcorte, corteBarbero.barbero.idbarbero, fechaActual, corteBarbero.precioFinal )
                val createdBarbero = RetrofitInstance.corteBarberoApi.createCorteBarbero(newCorteBarbero)
                Log.d("CortesBarberoViewModel", "cortesBarbero creado: $createdBarbero")

            }catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesBarbero: ${e.message}")
            }
        }
    }

    fun selectedCorteBarbero(corteBarbero:CorteBarbero){
        _selectedCortesBarbero.value = corteBarbero
    }

    fun updateCorteBarberoPrecio(corteBarbero: CorteBarbero) {
        viewModelScope.launch {
            try{

                RetrofitInstance.corteBarberoApi.updateCorteBarberoPrecio(corteBarbero.idcortebarbero, corteBarbero.precioFinal)
                Log.d("CorteBarberoViewModel", "CorteBarbero Actualizado: $corteBarbero")

            }catch (e: Exception) {
                Log.e("CorteBarberoViewModel", "Error al actualizar corteBarbero ${e.message}")
            }
        }
    }

    // Cargar los cortes diarios
    fun loadCortesDiarios(idBarbero: Int, fecha: String) {
        viewModelScope.launch {
            try {
                val cortesDiarios = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndDay(idBarbero, fecha)
                _cortes.value = cortesDiarios
            } catch (e: Exception) {
                Log.e("CorteBarberoViewModel", "Error al cargar cortes diarios: ${e.message}")
            }
        }
    }

    // Cargar los cortes semanales
    fun loadCortesSemanales(idBarbero: Int, inicio: String, fin: String) {
        viewModelScope.launch {
            try {
                val cortesSemanales = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndWeek(idBarbero, inicio, fin)
                _cortes.value = cortesSemanales
            } catch (e: Exception) {
                Log.e("CorteBarberoViewModel", "Error al cargar cortes semanales: ${e.message}")
            }
        }
    }

    // Cargar los cortes mensuales
    fun loadCortesMensuales(idBarbero: Int, mes: Int, anio: Int) {
        viewModelScope.launch {
            try {
                val cortesMensuales = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndMonth(idBarbero, mes, anio)
                _cortes.value = cortesMensuales
            } catch (e: Exception) {
                Log.e("CorteBarberoViewModel", "Error al cargar cortes mensuales: ${e.message}")
            }
        }
    }

}
