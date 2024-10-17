// MainActivity.kt
package com.example.barbershopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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



