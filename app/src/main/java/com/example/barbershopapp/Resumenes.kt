package com.example.barbershopapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResumenesScreen(context: Context, lifecycleOwner: LifecycleOwner) {
    val corteBarberoViewModel: CorteBarberoViewModel = viewModel()

    var showDialog by remember { mutableStateOf(false) }
    var resumenType by remember { mutableStateOf("") }

    var idBarbero by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    val date = LocalDate.of(2024, 10, 13)
    val fecha111 = date.toString()

    fun resetFields() {
        idBarbero = ""
        fecha = ""
        fechaInicio = ""
        fechaFin = ""
        mes = ""
        anio = ""
    }

    Column {
        // Botón para generar resumen diario
        Button(onClick = {
            resumenType = "Diario"
            showDialog = true
        }) {
            Text("Generar Resumen Diario")
        }

        // Botón para generar resumen semanal
        Button(onClick = {
            resumenType = "Semanal"
            showDialog = true
        }) {
            Text("Generar Resumen Semanal")
        }

        // Botón para generar resumen mensual
        Button(onClick = {
            resumenType = "Mensual"
            showDialog = true
        }) {
            Text("Generar Resumen Mensual")
        }

        // Dialog para pedir datos según el tipo de resumen
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Ingrese los datos para el resumen $resumenType") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = idBarbero,
                            onValueChange = { idBarbero = it },
                            label = { Text("ID del Barbero") }
                        )
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
                        // Llamar a la función correspondiente según el tipo de resumen
                        when (resumenType) {
                            "Diario" -> {
                                corteBarberoViewModel.loadCortesDiarios(
                                    idBarbero = idBarbero.toInt(),
                                    fecha = fecha
                                )
                                generatePdfFromViewModel(
                                    context,
                                    corteBarberoViewModel,
                                    "Diario",
                                    "resumen_diario_${fecha}_barbero_$idBarbero",
                                    lifecycleOwner,
                                    fechaInicio = LocalDate.parse(fecha)
                                )
                            }
                            "Semanal" -> {
                                corteBarberoViewModel.loadCortesSemanales(
                                    idBarbero = idBarbero.toInt(),
                                    inicio = fechaInicio,
                                    fin = fechaFin
                                )
                                generatePdfFromViewModel(
                                    context,
                                    corteBarberoViewModel,
                                    "Semanal",
                                    "resumen_semanal_${fechaInicio}_a_${fechaFin}_barbero_$idBarbero",
                                    lifecycleOwner,
                                    fechaInicio = LocalDate.parse(fechaInicio),
                                    fechaFin = LocalDate.parse(fechaFin)
                                )
                            }
                            "Mensual" -> {
                                corteBarberoViewModel.loadCortesMensuales(
                                    idBarbero = idBarbero.toInt(),
                                    mes = mes.toInt(),
                                    anio = anio.toInt()
                                )
                                generatePdfFromViewModel(
                                    context,
                                    corteBarberoViewModel,
                                    "Mensual",
                                    "resumen_mensual_${mes}_${anio}_barbero_$idBarbero",
                                    lifecycleOwner,
                                    fechaInicio = LocalDate.of(anio.toInt(), mes.toInt(), 1)
                                )
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
