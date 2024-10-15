package com.example.barbershopapp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import android.util.Log
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.ui.theme.BarberShopAppTheme
import com.example.barbershopapp.viewmodel.BarberoViewModel


@Composable
fun PreviewBarberComponents() {
        val barberoViewModel: BarberoViewModel = viewModel()

        // Cargar barberos al iniciar la pantalla
        barberoViewModel.loadBarberos()

        // Observar los cambios en la lista de barberos
        val barberos by barberoViewModel.barberos.collectAsState()

        // Pasar la lista de barberos a la función que crea tu UI
        if (barberos.isEmpty()) {
            Text("No hay barberos disponibles.")

        } else {
            LazyColumn {
                items(barberos) { barbero ->
                    Text(text = barbero.barberoNombre)
                }
            }
        }
}


@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BarberScreenPreview() {
    var showBarberComponents by remember { mutableStateOf(false) }

    Column {

        Button(
            onClick = {
                showBarberComponents = true // Actualizar el estado para mostrar PreviewBarberComponents
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                contentColor = MaterialTheme.colorScheme.background // Color del texto
            ),
            shape = RoundedCornerShape(30.dp), // Bordes redondeados
            elevation = ButtonDefaults.buttonElevation(8.dp) // Elevación para sombra
        ) {
            Text(text = "CallApi")
        }

        // Mostrar PreviewBarberComponents si showBarberComponents es true
        if (showBarberComponents) {
            PreviewBarberComponents()
        }
    }
}

