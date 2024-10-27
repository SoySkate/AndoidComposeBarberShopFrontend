package com.example.barbershopapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.barbershopapp.ui.theme.BarberShopAppTheme
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barbershopapp.newtwork.CorteBarbero
import com.example.barbershopapp.viewmodel.BarberoViewModel
import com.example.barbershopapp.viewmodel.CorteBarberoViewModel
import com.example.barbershopapp.viewmodel.CortesViewModel
import java.time.LocalDate

data class Business(val businessName: String, val businessOwner: String)

private val negocio = Business("BarberShop","Pratos")


@Composable
fun Navigation(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel, corteBarberoViewModel: CorteBarberoViewModel) {
    val navController = rememberNavController() // Crear un NavController
    val lifecycleOwner = LocalLifecycleOwner.current // Obtener el LifecycleOwner

    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            SettingsNavBar(barberViewModel, corteViewModel, corteBarberoViewModel, navController) // Coloca la barra de navegación en la parte inferior
        },
        content = { innerPadding ->
            // Aquí va el contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("cortes") { CortesScreenPreview(corteViewModel) } //pantalla cortes
                    composable("home") {
                        corteViewModel.setCorteScreenOn(false)
                        PreviewButtons(navController) } //pantalla home") { CortesScreenPreview() } //pantalla cortes
                    composable("barberos") { BarberScreenPreview(barberViewModel)  } // Pantalla de barberos
                    composable("resumenes") { ResumenesScreen(context = LocalContext.current, lifecycleOwner = lifecycleOwner) } //Pantalla resumenes
                }
            }
            }
    )
}

//Se puede acceder al material de ui.theme
@Composable
fun HomeImg() {
    Image(
        painterResource(id = R.drawable.barber01),
        contentDescription = "klk image",
        //es importante el orden de los modificadores
        modifier = Modifier.clip(shape = androidx.compose.foundation.shape.CircleShape)
            .background(
                Color.White)
            .size(80.dp)
    )
}

@Composable
fun MyText(text:String, color: Color, style: TextStyle){
    Text(text,color = color, style = style)
}

@Composable
fun MySideTexts(business: Business, barberViewModel: BarberoViewModel) {
    val selectedBarbero by barberViewModel.selectedBarbero.collectAsState()
    //layout para representacion de columnas
    Column {
        MyText(business.businessName,MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(10.dp))
        MyText(selectedBarbero?.barberoNombre ?: "Barbero No selecccionado.", Color.Red, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun MyComponent(business: Business,barberViewModel: BarberoViewModel) {
    // Layout para representación de filas, centrado
    Row(
        modifier = Modifier
            .fillMaxWidth() // Hace que la fila ocupe todo el ancho
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center // Centra el contenido horizontalmente
    ) {
        HomeImg()
        Spacer(Modifier.width(10.dp)) // Ajuste del espaciado
        MySideTexts(business, barberViewModel)
    }
}

@Composable
fun PreviewComponents(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel, corteBarberoViewModel: CorteBarberoViewModel) {
    BarberShopAppTheme {
        //de esta forma podemos hacer que se pueda hacer scroll scroll state y modifier al column
        //val scrollState = rememberScrollState()
        Column (modifier =
        Modifier.fillMaxWidth()
            .background(MaterialTheme
                .colorScheme.background)
            //.verticalScroll(scrollState)
                ){
            Spacer(Modifier.height(10.dp))
            MyComponent(negocio,barberViewModel)
            Navigation(barberViewModel, corteViewModel, corteBarberoViewModel)

        }

    }
}

@Composable
fun PreviewButtons(navController: NavController) {

    BarberShopAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(Modifier.height(100.dp))

            Button(
                onClick = {  navController.navigate("barberos") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                ),
                shape = RoundedCornerShape(30.dp), // Bordes redondeados
                elevation = ButtonDefaults.buttonElevation(8.dp) // Elevación para sombra
            ) {
                Text(text = "Barberos")
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("cortes") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                ),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(text = "Cortes")
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("resumenes") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                ),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(text = "Resumenes")
            }
        }
    }
}

@Composable
fun SettingsNavBar(
    barberViewModel: BarberoViewModel,
    corteViewModel: CortesViewModel,
    corteBarberoViewModel: CorteBarberoViewModel,
    navController: NavController
) {
    val corteScreenOn by corteViewModel.corteScreenOn.collectAsState()
    var showDialog by remember { mutableStateOf(false) } // Para mostrar el mensaje de advertencia
    var showDialogPrice by remember { mutableStateOf(false) } // Para mostrar el formulario de precio final
    var precioFinal by remember { mutableStateOf("") } // Para almacenar el precio ingresado

    BottomAppBar(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                onClick = { /* Acción para Ajustes de App */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = "Ajustes App")
            }

            if (corteScreenOn == true) {
                TextButton(
                    onClick = {
                        val corte = corteViewModel.selectedCorte.value
                        val barbero = barberViewModel.selectedBarbero.value

                        if (corte != null && barbero != null) {
                            showDialogPrice = true // Mostrar el formulario para configurar el precio final
                        } else {
                            showDialog = true // Mostrar advertencia si faltan selección de corte o barbero
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text(text = "Comenzar")
                }

                // Dialogo de advertencia para seleccionar corte y barbero
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Advertencia") },
                        text = { Text("Seleccione un corte y un barbero antes de continuar.") },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Aceptar")
                            }
                        }
                    )
                }

                // Diálogo para configurar el precio final
                if (showDialogPrice) {
                    AlertDialog(
                        onDismissRequest = { showDialogPrice = false },
                        title = { Text("Configurar Precio Final") },
                        text = {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    value = precioFinal,
                                    onValueChange = { precioFinal = it },
                                    label = { Text("Precio Final:${corteViewModel.selectedCorte.value?.precioDefecto}") },
                                    placeholder = { Text("${corteViewModel.selectedCorte.value?.precioDefecto}") },
                                    singleLine = true
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val corte = corteViewModel.selectedCorte.value
                                val barbero = barberViewModel.selectedBarbero.value

                                // Usa el precio ingresado si es válido, de lo contrario usa el precio por defecto
                                val precioFinalValue = precioFinal.toDoubleOrNull() ?: corte?.precioDefecto

                                if (corte != null && barbero != null && precioFinalValue != null) {
                                    val cortebarbero = CorteBarbero(
                                        idcortebarbero = 0,
                                        corte = corte,
                                        barbero = barbero,
                                        fechaCorte = LocalDate.now().toString(),
                                        precioFinal = precioFinalValue
                                    )
                                    corteBarberoViewModel.createCortesBarbero(cortebarbero)
                                }
                                showDialogPrice = false // Cerrar el diálogo de precio final
                                navController.navigate("home") // Navegar a home
                            }) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialogPrice = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }
    }
}



//@Preview(showSystemUi = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel, corteBarberoViewModel: CorteBarberoViewModel) {
    PreviewComponents(barberViewModel, corteViewModel, corteBarberoViewModel)
}




