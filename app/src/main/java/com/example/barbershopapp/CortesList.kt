package com.example.barbershopapp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.barbershopapp.newtwork.Corte
import com.example.barbershopapp.viewmodel.BarberoViewModel
import com.example.barbershopapp.viewmodel.CortesViewModel


@Composable
fun PreviewCorteComponents(corteViewModel: CortesViewModel) {
    //val corteViewModel: CorteViewModel = viewModel()

    // Cargar cortes al iniciar la pantalla
    corteViewModel.loadCortes()

    // Observar los cambios en la lista de cortes
    val cortes by corteViewModel.cortes.collectAsState()

    // Variable para almacenar el cortes seleccionado
    var selectedCorte by remember { mutableStateOf<Corte?>(null) }

    // Variable para controlar si se deben mostrar los botones después de un long press
    var mostrarBotones by remember { mutableStateOf(false) }
    var updateCorte by remember { mutableStateOf(false) }

    // Pasar la lista de cortes a la función que crea tu UI
    if (cortes.isEmpty()) {
        Text("No hay cortes disponibles.")
    } else {
        Column {
            if (updateCorte) {
                val editCorte = Corte(
                    corteNombre = selectedCorte?.corteNombre ?: "",
                    precioDefecto = selectedCorte?.precioDefecto ?: 0.00,
                    idcorte = selectedCorte?.idcorte ?: 0
                )
                var corteNombre by remember { mutableStateOf(editCorte.corteNombre) }
                var precioDefecto by remember { mutableStateOf(editCorte.precioDefecto) }
                //val cortesViewModel: CortesViewModel = viewModel()

                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    // Mostrar formulario si showForm es true
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Campo de entrada para el nombre del corte
                        TextField(
                            value = corteNombre,
                            onValueChange = { corteNombre = it },
                            label = { Text("Nombre del Corte") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        // Campo de entrada para el precio del corte
                        //Seguramente hay que ponerlo de otra forma
                        TextField(
                            value = precioDefecto.toString(),
                            onValueChange = { newText ->
                                // Intentar convertir el nuevo texto a Double
                                val newPrecio = try {
                                    newText.toDouble()
                                } catch (e: NumberFormatException) {
                                    // Si ocurre un error, no actualizar el estado
                                    precioDefecto
                                }
                                precioDefecto = newPrecio},
                            label = { Text("Precio del Corte") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Botón para enviar el formulario
                        Button(
                            onClick = {
                                // Aquí puedes agregar la lógica para editar un corte
                                // Por ejemplo, llamar a una función del ViewModel para enviar los datos
                                if (corteNombre.isNotEmpty() && precioDefecto!=0.00) {
                                    //Actualizacion del corte
                                    editCorte.corteNombre = corteNombre
                                    editCorte.precioDefecto = precioDefecto
                                    corteViewModel.updateCorteNombre(editCorte)
                                    corteViewModel.updateCortePrecio(editCorte)
                                    println("Corte Actualizado: $corteNombre")
                                    // Luego de la creación, limpiar el campo de entrada
                                    corteNombre = ""
                                    precioDefecto = 0.00
                                }
//                                updateCorte=false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = corteNombre.isNotEmpty() && precioDefecto!=0.00 // Habilitar solo si el nombre no está vacío
                        ) {
                            Text(text = "Actualizar Corte")
                        }

                    }
                }
                Button(
                    onClick = {
                        updateCorte=false
                    },
                    modifier = Modifier.padding(end = 4.dp), // Espacio entre botones
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )){Text(text = "Close")}
            }
            LazyColumn {
                items(cortes) { corte ->
                    // Cada elemento de la lista es un botón que al ser clicado selecciona el cortes
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                if (selectedCorte == corte) Color.Gray else Color.Transparent
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        // Seleccionar el corte con un solo toque
                                        selectedCorte = corte
                                        corteViewModel.selectedCorte(corte)
                                        mostrarBotones = false // Ocultar los botones al seleccionar con un toque
                                    },
                                    onLongPress = {
                                        // Verificar si el corte seleccionado coincide con el actual
                                        if (selectedCorte == corte) {
                                            mostrarBotones = true // Mostrar los botones en caso de long press
                                        }
                                    }
                                )
                            }
                            .padding(12.dp)
                    ) {

                        // Mostrar el nombre del corte
                        Text(
                            text = corte.corteNombre+" ---> "+corte.precioDefecto.toString() + "€",
                            modifier = Modifier
                                .weight(1f) // Hacer que el Text ocupe el espacio sobrante
                                .padding(end = 8.dp)
                        )

                        // Mostrar los botones solo si el corte está seleccionado y se mantiene presionado
                        if (selectedCorte == corte && mostrarBotones) {
                            // Botón para editar el corte
                            Button(
                                onClick = {
                                    // Lógica para editar el corte
                                    updateCorte=true
                                    println("Editar corte: ${corte.corteNombre}")
                                },
                                modifier = Modifier.padding(end = 4.dp), // Espacio entre botones
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Yellow,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(text = "Editar")
                            }

                            // Botón para eliminar el corte
                            Button(
                                onClick = {
                                    // Lógica para eliminar el corte
                                    corteViewModel.deleteCorte(corte)
                                    println("Eliminar corte: ${corte.corteNombre}")
                                    selectedCorte = null
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

//@Preview(showSystemUi = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CorteListPreview(corteViewModel: CortesViewModel) {
    var showCorteComponents by remember { mutableStateOf(true) }
    var showForm by remember { mutableStateOf(false) }
    var corteNombre by remember { mutableStateOf("") }
    var precioDefecto by remember { mutableStateOf(0.00) }

    Column {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Toggle switch para mostrar/ocultar el formulario
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Añadir nuevo corte")
                Switch(
                    checked = showForm,
                    onCheckedChange = { showForm = it }
                )
            }

            // Mostrar formulario si showForm es true
            if (showForm) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Campo de entrada para el nombre del corte
                    TextField(
                        value = corteNombre,
                        onValueChange = { corteNombre = it },
                        label = { Text("Nombre del Corte") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Campo de entrada para el precio del corte
                    //Seguramente hay que ponerlo de otra forma
                    TextField(
                        value = precioDefecto.toString(),
                        onValueChange = { newText ->
                            // Intentar convertir el nuevo texto a Double
                            val newPrecio = try {
                                newText.toDouble()
                            } catch (e: NumberFormatException) {
                                // Si ocurre un error, no actualizar el estado
                                precioDefecto
                            }
                            precioDefecto = newPrecio
                        },
                        label = { Text("Precio del Corte") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Botón para enviar el formulario
                    Button(
                        onClick = {
                            // Aquí puedes agregar la lógica para crear un nuevo corte
                            // Por ejemplo, llamar a una función del ViewModel para enviar los datos
                            if (corteNombre.isNotEmpty() && (precioDefecto != 0.00)) {
                                // Simulación de la creación del corte
                                corteViewModel.createCorte(corteNombre, precioDefecto)
                                println("Nuevo corte creado: $corteNombre")
                                // Luego de la creación, limpiar el campo de entrada
                                corteNombre = ""
                                // Cerrar el formulario
                                showForm = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = (corteNombre.isNotEmpty() && (precioDefecto != 0.00)) // Habilitar solo si el nombre y el precio no está vacío
                    ) {
                        Text(text = "Crear Corte")
                    }
                }
            }
        }
        // Mostrar PreviewCorteComponents si showCorteComponents es true
        if (showCorteComponents) {
            PreviewCorteComponents(corteViewModel)
        }
    }

}

@Composable
fun CortesScreenPreview(corteViewModel: CortesViewModel){
    corteViewModel.setCorteScreenOn(true)
    Column(){
        Spacer(modifier = Modifier.height(6.dp))
        CorteListPreview(corteViewModel)
    }
}

