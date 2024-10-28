package com.example.barbershopapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.LifecycleOwner
import com.example.barbershopapp.viewmodel.CorteBarberoViewModel
import org.threeten.bp.LocalDate
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.newtwork.Barbero
import com.example.barbershopapp.viewmodel.BarberoViewModel

/*@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResumenesScreen(context: Context, lifecycleOwner: LifecycleOwner) {
    val corteBarberoViewModel: CorteBarberoViewModel = viewModel()
    val date = LocalDate.of(2024, 10, 13)
    val idBarbero = 2
    val mes = 10
    val anio = 2024
    val dateBegin = LocalDate.of(2024,10,9)

    Column {
        // Botón para generar resumen diario
        Button(onClick = {
            corteBarberoViewModel.loadCortesDiarios(idBarbero = idBarbero, fecha = date.toString())
            generatePdfFromViewModel(context, corteBarberoViewModel, "Diario", "resumen_diario_" + date.toString() + "_barbero_" + idBarbero, lifecycleOwner, fechaInicio = date)
        }) {
            Text("Generar Resumen Diario")
        }

        // Botón para generar resumen semanal
        Button(onClick = {
            corteBarberoViewModel.loadCortesSemanales(idBarbero = 2, inicio = dateBegin.toString(), fin = date.toString())
            generatePdfFromViewModel(context, corteBarberoViewModel, "Semanal", "resumen_semanal_" + dateBegin.toString() + "_" + date.toString() + "_barbero_" + idBarbero, lifecycleOwner, fechaInicio = dateBegin, fechaFin = date)
        }) {
            Text("Generar Resumen Semanal")
        }

        // Botón para generar resumen mensual
        Button(onClick = {
            corteBarberoViewModel.loadCortesMensuales(idBarbero = idBarbero, mes = mes, anio = anio)
            generatePdfFromViewModel(context, corteBarberoViewModel, "Mensual", "resumen_mensual_" + dateBegin.toString() + "_" + date.toString() + "_barbero_" + idBarbero, lifecycleOwner, fechaInicio = dateBegin, fechaFin = date)
        }) {
            Text("Generar Resumen Mensual")
        }
    }
}*/

import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResumenesScreen(context: Context, lifecycleOwner: LifecycleOwner) {
    val corteBarberoViewModel: CorteBarberoViewModel = viewModel()
    val barberViewModel: BarberoViewModel = viewModel()

    var showDialog by remember { mutableStateOf(false) }
    var resumenType by remember { mutableStateOf("") }
    var selectedBarbero by remember { mutableStateOf<Barbero?>(null) }
    var expanded by remember { mutableStateOf(false) }  // Control de expansión del menú
    var fecha by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    // Cargar los barberos al iniciar el Composable
    LaunchedEffect(Unit) {
        barberViewModel.loadBarberos()
    }

    val barberos by barberViewModel.barberos.collectAsState(initial = emptyList())

    fun resetFields() {
        selectedBarbero = null
        fecha = ""
        fechaInicio = ""
        fechaFin = ""
        mes = ""
        anio = ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(100.dp))

        // Botón para generar resumen diario
        Button(onClick = {
            resumenType = "Diario"
            showDialog = true
        },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                contentColor = MaterialTheme.colorScheme.background // Color del texto
            ),
            shape = RoundedCornerShape(30.dp), // Bordes redondeados
            elevation = ButtonDefaults.buttonElevation(8.dp) // Elevación para sombra)
        ) {
            Text("Generar Resumen Diario")
        }

        Spacer(Modifier.height(10.dp))

        // Botón para generar resumen semanal
        Button(onClick = {
            resumenType = "Semanal"
            showDialog = true
        },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                contentColor = MaterialTheme.colorScheme.background // Color del texto
            ),
            shape = RoundedCornerShape(30.dp), // Bordes redondeados
            elevation = ButtonDefaults.buttonElevation(8.dp) // Elevación para sombra)
        ) {
            Text("Generar Resumen Semanal")
        }

        Spacer(Modifier.height(10.dp))

        // Botón para generar resumen mensual
        Button(onClick = {
            resumenType = "Mensual"
            showDialog = true
        },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                contentColor = MaterialTheme.colorScheme.background // Color del texto
            ),
            shape = RoundedCornerShape(30.dp), // Bordes redondeados
            elevation = ButtonDefaults.buttonElevation(8.dp) // Elevación para sombra)
        ) {
            Text("Generar Resumen Mensual")
        }

        // Dialog para pedir datos según el tipo de resumen
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Seleccione los datos para el resumen $resumenType") },
                text = {
                    Column {
                        // ExposedDropdownMenuBox para seleccionar barbero
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedBarbero?.barberoNombre ?: "Seleccionar Barbero",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Barbero") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .clickable { expanded = !expanded }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                barberos.forEach { barbero ->
                                    DropdownMenuItem(
                                        text = { Text(text = barbero.barberoNombre) },
                                        onClick = {
                                            selectedBarbero = barbero
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Campos adicionales según el tipo de resumen
                        when (resumenType) {
                            "Diario" -> {
                                OutlinedTextField(
                                    value = fecha,
                                    onValueChange = { fecha = it },
                                    label = { Text("Fecha (YYYY-MM-DD)") }
                                )
                            }
                            "Semanal" -> {
                                OutlinedTextField(
                                    value = fechaInicio,
                                    onValueChange = { fechaInicio = it },
                                    label = { Text("Fecha de inicio (YYYY-MM-DD)") }
                                )
                                OutlinedTextField(
                                    value = fechaFin,
                                    onValueChange = { fechaFin = it },
                                    label = { Text("Fecha de fin (YYYY-MM-DD)") }
                                )
                            }
                            "Mensual" -> {
                                OutlinedTextField(
                                    value = mes,
                                    onValueChange = { mes = it },
                                    label = { Text("Mes (1-12)") }
                                )
                                OutlinedTextField(
                                    value = anio,
                                    onValueChange = { anio = it },
                                    label = { Text("Año") }
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        selectedBarbero?.let { barbero ->
                            when (resumenType) {
                                "Diario" -> {
                                    corteBarberoViewModel.loadCortesDiarios(
                                        idBarbero = barbero.idbarbero,
                                        fecha = fecha
                                    )
                                    generatePdfFromViewModel(
                                        context,
                                        corteBarberoViewModel,
                                        "Diario",
                                        "resumen_diario_${fecha}_barbero_${barbero.idbarbero}",
                                        lifecycleOwner,
                                        fechaInicio = LocalDate.parse(fecha)
                                    )
                                }
                                "Semanal" -> {
                                    corteBarberoViewModel.loadCortesSemanales(
                                        idBarbero = barbero.idbarbero,
                                        inicio = fechaInicio,
                                        fin = fechaFin
                                    )
                                    generatePdfFromViewModel(
                                        context,
                                        corteBarberoViewModel,
                                        "Semanal",
                                        "resumen_semanal_${fechaInicio}_a_${fechaFin}_barbero_${barbero.idbarbero}",
                                        lifecycleOwner,
                                        fechaInicio = LocalDate.parse(fechaInicio),
                                        fechaFin = LocalDate.parse(fechaFin)
                                    )
                                }
                                "Mensual" -> {
                                    corteBarberoViewModel.loadCortesMensuales(
                                        idBarbero = barbero.idbarbero,
                                        mes = mes.toInt(),
                                        anio = anio.toInt()
                                    )
                                    generatePdfFromViewModel(
                                        context,
                                        corteBarberoViewModel,
                                        "Mensual",
                                        "resumen_mensual_${mes}_${anio}_barbero_${barbero.idbarbero}",
                                        lifecycleOwner,
                                        fechaInicio = LocalDate.of(anio.toInt(), mes.toInt(), 1)
                                    )
                                }
                            }
                        }
                        resetFields()
                        showDialog = false
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        resetFields()
                        showDialog = false
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

