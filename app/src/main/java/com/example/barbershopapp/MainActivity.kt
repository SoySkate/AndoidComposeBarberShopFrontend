package com.example.barbershopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.ui.theme.BarberShopAppTheme
import com.example.barbershopapp.viewmodel.BarberoViewModel
import com.example.barbershopapp.viewmodel.CorteBarberoViewModel
import com.example.barbershopapp.viewmodel.CortesViewModel
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberShopAppTheme {
                AndroidThreeTen.init(this)
                // Crear el ViewModel en la actividad
                val barberoViewModel: BarberoViewModel = viewModel()
                val corteViewModel: CortesViewModel = viewModel()
                val corteBarberoViewModel: CorteBarberoViewModel = viewModel()
                HomeScreenPreview(barberoViewModel,corteViewModel, corteBarberoViewModel)
            }
        }
    }
}



