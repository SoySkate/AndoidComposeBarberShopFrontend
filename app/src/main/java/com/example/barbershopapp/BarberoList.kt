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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.ui.theme.BarberShopAppTheme
import com.example.barbershopapp.viewmodel.BarberoViewModel
import kotlinx.coroutines.delay


@Composable
fun PreviewBarberComponents() {
    val barberoViewModel: BarberoViewModel = viewModel()

    // Cargar barberos al iniciar la pantalla
    barberoViewModel.loadBarberos()

    // Observar los cambios en la lista de barberos
    val barberos by barberoViewModel.barberos.collectAsState()

    // Variable para almacenar el barbero seleccionado
    var selectedBarbero by remember { mutableStateOf<Barbero?>(null) }

    // Variable para controlar si se deben mostrar los botones después de un long press
    var mostrarBotones by remember { mutableStateOf(false) }
    var updateBarbero by remember { mutableStateOf(false) }

    // Pasar la lista de barberos a la función que crea tu UI
    if (barberos.isEmpty()) {
        Text("No hay barberos disponibles.")
    } else {
        Column {
            if (updateBarbero) {
                val editBarbero = Barbero(
                    barberoNombre = selectedBarbero?.barberoNombre ?: "",
                    idbarbero = selectedBarbero?.idbarbero ?: 0
                )
                    UpdateBarber(editBarbero)
            }
            LazyColumn {
                items(barberos) { barbero ->
                    // Cada elemento de la lista es un botón que al ser clicado selecciona el barbero
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                if (selectedBarbero == barbero) Color.Gray else Color.Transparent
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        // Seleccionar el barbero con un solo toque
                                        selectedBarbero = barbero
                                        barberoViewModel.selectedBarbero(barbero)
                                        mostrarBotones = false // Ocultar los botones al seleccionar con un toque
                                    },
                                    onLongPress = {
                                        // Verificar si el barbero seleccionado coincide con el actual
                                        if (selectedBarbero == barbero) {
                                            mostrarBotones = true // Mostrar los botones en caso de long press
                                        }
                                    }
                                )
                            }
                            .padding(12.dp)
                    ) {
                        // Mostrar el nombre del barbero
                        Text(
                            text = barbero.barberoNombre,
                            modifier = Modifier
                                .weight(1f) // Hacer que el Text ocupe el espacio sobrante
                                .padding(end = 8.dp)
                        )

                        // Mostrar los botones solo si el barbero está seleccionado y se mantiene presionado
                        if (selectedBarbero == barbero && mostrarBotones) {
                            // Botón para editar el barbero
                            Button(
                                onClick = {
                                    // Lógica para editar el barbero
                                    updateBarbero=true
                                    println("Editar barbero: ${barbero.barberoNombre}")
                                },
                                modifier = Modifier.padding(end = 4.dp), // Espacio entre botones
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Yellow,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(text = "Editar")
                            }

                            // Botón para eliminar el barbero
                            Button(
                                onClick = {
                                    // Lógica para eliminar el barbero
                                    barberoViewModel.deleteBarbero(barbero)
                                    println("Eliminar barbero: ${barbero.barberoNombre}")
                                    selectedBarbero = null
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Eliminar")
                            }
                        }
                    }

                }

                }
            }
        }
    }

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BarberListPreview() {
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
            Text(text = "Show Barberos List")
        }

        // Mostrar PreviewBarberComponents si showBarberComponents es true
        if (showBarberComponents) {
            PreviewBarberComponents()
        }
    }
}
@Composable
fun BarberScreenPreview(){
    Column(){
        BarberScreenWithFormPreview()
        Spacer(modifier = Modifier.height(6.dp))
        BarberListPreview()
    }

}
@Composable
fun UpdateBarber(barbero: Barbero){
    var barberoNombre by remember { mutableStateOf("") }
    val barberoViewModel: BarberoViewModel = viewModel()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

        // Mostrar formulario si showForm es true
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Campo de entrada para el nombre del barbero
                TextField(
                    value = barberoNombre,
                    onValueChange = { barberoNombre = it },
                    label = { Text("Nombre del barbero") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Botón para enviar el formulario
                Button(
                    onClick = {
                        // Aquí puedes agregar la lógica para crear un nuevo barbero
                        // Por ejemplo, llamar a una función del ViewModel para enviar los datos
                        if (barberoNombre.isNotEmpty()) {
                            //Actualizacion del barbero
                            barbero.barberoNombre = barberoNombre
                            barberoViewModel.updateBarbero(barbero)
                            println("Barbero Actualizado: $barberoNombre")
                            // Luego de la creación, limpiar el campo de entrada
                            barberoNombre = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = barberoNombre.isNotEmpty() // Habilitar solo si el nombre no está vacío
                ) {
                    Text(text = "Actualizar Barbero")
                }

        }
    }
}

@Composable
fun BarberScreenWithForm() {
    var showForm by remember { mutableStateOf(false) }
    var barberoNombre by remember { mutableStateOf("") }
    val barberoViewModel: BarberoViewModel = viewModel()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Toggle switch para mostrar/ocultar el formulario
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Añadir nuevo barbero")
            Switch(
                checked = showForm,
                onCheckedChange = { showForm = it }
            )
        }

        // Mostrar formulario si showForm es true
        if (showForm) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Campo de entrada para el nombre del barbero
                TextField(
                    value = barberoNombre,
                    onValueChange = { barberoNombre = it },
                    label = { Text("Nombre del barbero") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Botón para enviar el formulario
                Button(
                    onClick = {
                        // Aquí puedes agregar la lógica para crear un nuevo barbero
                        // Por ejemplo, llamar a una función del ViewModel para enviar los datos
                        if (barberoNombre.isNotEmpty()) {
                            // Simulación de la creación del barbero
                            barberoViewModel.createBarbero(barberoNombre)
                            println("Nuevo barbero creado: $barberoNombre")
                            // Luego de la creación, limpiar el campo de entrada
                            barberoNombre = ""
                            // Cerrar el formulario
                            showForm = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = barberoNombre.isNotEmpty() // Habilitar solo si el nombre no está vacío
                ) {
                    Text(text = "Crear Barbero")
                }
            }
        }
    }
}

@Composable
@Preview
fun BarberScreenWithFormPreview() {
    BarberScreenWithForm()
}


