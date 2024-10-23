package com.example.barbershopapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.LifecycleOwner
import com.example.barbershopapp.viewmodel.CorteBarberoViewModel
import org.threeten.bp.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
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
}
