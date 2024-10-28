package com.example.barbershopapp

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.barbershopapp.newtwork.CorteBarbero
import com.example.barbershopapp.viewmodel.CorteBarberoViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import org.threeten.bp.LocalDate

// Función para generar el PDF a partir del ViewModel
@RequiresApi(Build.VERSION_CODES.O)
fun generatePdfFromViewModel(
    context: Context,
    viewModel: CorteBarberoViewModel,
    tipoResumen: String,
    nombreArchivo: String,
    lifecycleOwner: LifecycleOwner,
    fechaInicio: LocalDate? = null,
    fechaFin: LocalDate? = null
) {
    lifecycleOwner.lifecycleScope.launch {
        viewModel.cortes.collect { resumenData ->
            if (resumenData.isNotEmpty()) {
                generatePdfResumen(context, resumenData, tipoResumen, nombreArchivo, fechaInicio, fechaFin)
            }
        }
    }
}

// Función para crear el PDF con formato profesional
@RequiresApi(Build.VERSION_CODES.O)
fun generatePdfResumen(
    context: Context,
    resumenData: List<CorteBarbero>,
    tipoResumen: String,
    nombreArchivo: String,
    fechaInicio: LocalDate? = null,
    fechaFin: LocalDate? = null
) {
    val barberiaNombre = "Pratos BarberShop"
    val pdfDocument = PdfDocument()
    val paint = Paint()

    // Crear una página para el PDF (Tamaño A4)
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val pageWidth = pageInfo.pageWidth
    var y = 50f // Coordenada Y inicial

    // Centrar el nombre de la barbería en la parte superior
    paint.textSize = 22f
    paint.textAlign = Paint.Align.CENTER
    canvas.drawText(barberiaNombre, pageWidth / 2f, y, paint)
    y += 50f

    // Escribir el nombre del barbero alineado a la izquierda
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 16f
    canvas.drawText("Barbero: ${resumenData[0].barbero.barberoNombre}", 10f, y, paint)
    y += 30f

    // Escribir el tipo de resumen y las fechas dependiendo del tipo
    paint.textSize = 14f
    when (tipoResumen) {
        "Diario" -> {
            val fecha = fechaInicio?.toString() ?: "Fecha no especificada"
            canvas.drawText("Resumen del día: $fecha", 10f, y, paint)
        }
        "Semanal" -> {
            val inicio = fechaInicio?.toString() ?: "Inicio no especificado"
            val fin = fechaFin?.toString() ?: "Fin no especificado"
            canvas.drawText("Resumen entre la semana del $inicio al $fin", 10f, y, paint)
        }
        "Mensual" -> {
            val mesAnio = fechaInicio?.month.toString() + " de " + fechaInicio?.year.toString()
            canvas.drawText("Resumen del mes de $mesAnio", 10f, y, paint)
        }
        else -> {
            canvas.drawText("Resumen de cortes", 10f, y, paint)
        }
    }
    y += 40f

    // Desglose de cortes realizados
    paint.textSize = 14f
    var total = 0.0

    for (corte in resumenData) {
        // Si es resumen diario, no mostrar la fecha en cada corte
        val detalleCorte = if (tipoResumen == "Diario") {
            "Corte: ${corte.corte.corteNombre}, Precio cobrado: ${corte.precioFinal}€"
        } else {
            "Fecha: ${corte.fechaCorte}, Corte: ${corte.corte.corteNombre}, Precio cobrado: ${corte.precioFinal}€"
        }

        // Dibujar el texto del corte
        canvas.drawText(detalleCorte, 10f, y, paint)
        y += 25f
        total += corte.precioFinal
    }

    // Dejar espacio antes de mostrar el total
    y += 40f

    // Dibujar el total cobrado alineado a la izquierda
    paint.textSize = 16f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Total cobrado: $total€", 10f, y, paint)

    pdfDocument.finishPage(page)

    // Guardar el PDF en el almacenamiento
    //val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File(directory, "$nombreArchivo.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        pdfDocument.close()
    }
}

