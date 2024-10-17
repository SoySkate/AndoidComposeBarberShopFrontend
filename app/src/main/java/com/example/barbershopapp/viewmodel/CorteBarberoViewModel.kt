package com.example.barbershopapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.newtwork.CorteBarbero
import com.example.barbershopapp.newtwork.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
    //Funcion para getcortes Barbero dia
    fun loadCortesByBarberoDia(selectedIdBarberoDia: Barbero, dia:String) {
        Log.d("CortesBarberoViewModel", "Cargando cortesBarbero...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de cortesBarbero
                var idBarbero = selectedIdBarberoDia.idbarbero
                val cortesBarberosDiaList = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndDay(idBarbero, dia)
                _cortesBarberoDia.value = cortesBarberosDiaList // Actualiza el estado
                Log.d("CortesBarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CortesBarberoViewModel", "cortesBarbero cargados: ${_cortesBarberoDia.value.size}")
            } catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesBarberoDIA: ${e.message}")
            }
        }
    }
    //Funcion para getcortes Barbero semana
    fun loadCortesByBarberoSemana(selectedIdBarberoSemana: Barbero, inicio:String, fin:String) {
        Log.d("CortesBarberoViewModel", "Cargando cortesBarbero...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de cortesBarbero
                var idBarbero = selectedIdBarberoSemana.idbarbero
                val cortesBarberosSemanaList = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndWeek(idBarbero, inicio, fin)
                _cortesBarberoSemana.value = cortesBarberosSemanaList // Actualiza el estado
                Log.d("CortesBarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CortesBarberoViewModel", "cortesBarbero cargados: ${_cortesBarberoSemana.value.size}")
            } catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesBarberoSemana: ${e.message}")
            }
        }
    }
    //Funcion para getcortes Barbero mes
    fun loadCortesByBarberoMes(selectedIdBarberoMes: Barbero, mes:Int, anio:Int) {
        Log.d("CortesBarberoViewModel", "Cargando cortesBarbero...")
        viewModelScope.launch {
            try {
                // Llama a la API para obtener la lista de cortesBarbero
                var idBarbero = selectedIdBarberoMes.idbarbero
                val cortesBarberosMesList = RetrofitInstance.corteBarberoApi.getCortesByBarberoAndMonth(idBarbero, mes, anio)
                _cortesBarberoMes.value = cortesBarberosMesList // Actualiza el estado
                Log.d("CortesBarberoViewModel", "¡Todo salió bien EN VIEWMODEL!");
                Log.d("CortesBarberoViewModel", "cortesBarbero cargados: ${_cortesBarberoSemana.value.size}")
            } catch (e: Exception) {
                Log.e("CortesBarberoViewModel", "Error al cargar cortesBarberoMes: ${e.message}")
            }
        }
    }
    //funcion para crear cortesBarbero
    fun createCortesBarbero(corteBarbero: CorteBarbero){
        viewModelScope.launch {
            try{
                val newCorteBarbero = CorteBarbero(0, corteBarbero.corte, corteBarbero.barbero, corteBarbero.precioFinal )
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

}
