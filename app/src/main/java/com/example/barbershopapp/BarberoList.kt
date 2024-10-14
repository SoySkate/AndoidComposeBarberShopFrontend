package com.example.barbershopapp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun PreviewBarberComponents(){

}
@Composable
fun SettingsNavBarBarberos() {

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton( onClick = { /* Acción al hacer clic en el botón */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                ),


                ) {
                Text(text = "Atras")
            }
            TextButton(onClick = { /* Acción para Ajuste 2 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                )
            ) {
                Text(text = "Create")
            }
            TextButton(onClick = { /* Acción para Ajuste 3 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                )
            ) {
                Text(text = "Edit")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BarberScreenPreview() {
    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            SettingsNavBarBarberos() // Coloca la barra de navegación en la parte inferior
        },
        content = { innerPadding ->
            // Aquí va el contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PreviewBarberComponents()
            }
        }
    )
}
