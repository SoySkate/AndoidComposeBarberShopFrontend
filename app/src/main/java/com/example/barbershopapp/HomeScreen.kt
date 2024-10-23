package com.example.barbershopapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun Navigation(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel) {
    val navController = rememberNavController() // Crear un NavController
    val lifecycleOwner = LocalLifecycleOwner.current // Obtener el LifecycleOwner

    NavHost(navController = navController, startDestination = "home") {
        composable("cortes") { CortesScreenPreview(corteViewModel) } //pantalla cortes
        composable("home") {
            corteViewModel.setCorteScreenOn(false)
            PreviewButtons(navController) } //pantalla home") { CortesScreenPreview() } //pantalla cortes
        composable("barberos") { BarberScreenPreview(barberViewModel)  } // Pantalla de barberos
        composable("resumenes") { ResumenesScreen(context = LocalContext.current, lifecycleOwner = lifecycleOwner) } //Pantalla resumenes
    }
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
fun PreviewComponents(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel) {
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
            Navigation(barberViewModel, corteViewModel)
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
fun SettingsNavBar(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel, corteBarberoViewModel: CorteBarberoViewModel) {

    val corteScreenOn by corteViewModel.corteScreenOn.collectAsState()

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            TextButton(onClick = { /* Acción para Ajuste 2 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                )
            ) {
                Text(text = "Ajustes App")
            }
            if(corteScreenOn==true){
            //esto es para hacerlo bool asi cuando este dentro la screen cortes list se abra
            TextButton(onClick = {
              // var existBarberAndCorte by remember { mutableStateOf(false) }
                val corte = corteViewModel.selectedCorte.value
                var precioFinal = corte?.precioDefecto
                val barbero = barberViewModel.selectedBarbero.value

                Log.d("CortesBarberoViewModel", "Tocando bototn de COMENZAR")
                Log.d("CortesBarberoViewModel", "Valor de barbero: $barbero")
                Log.d("CortesBarberoViewModel", "Valor de corte: $corte")

                if(corte!=null && barbero!=null){
                    Log.d("CortesBarberoViewModel", "DENTRO condicional botoncomenzar")
                     // Verificar que corte y barbero no sean nulos antes de crear CorteBarbero
                            corte?.let { nonNullCorte ->
                                barbero?.let { nonNullBarbero ->
                                    val cortebarbero = CorteBarbero(
                                        idcortebarbero = 0,
                                        corte = nonNullCorte,
                                        barbero = nonNullBarbero,
                                        fechaCorte = LocalDate.now().toString(),
                                        precioFinal = nonNullCorte.precioDefecto
                                    )
                                    corteBarberoViewModel.createCortesBarbero(cortebarbero)
                                }
                            }
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.background // Color del texto
                )
            ) {
                Text(text = "Comenzar")
            }
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(barberViewModel: BarberoViewModel, corteViewModel: CortesViewModel, corteBarberoViewModel: CorteBarberoViewModel) {
    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            SettingsNavBar(barberViewModel, corteViewModel, corteBarberoViewModel) // Coloca la barra de navegación en la parte inferior
        },
        content = { innerPadding ->
            // Aquí va el contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PreviewComponents(barberViewModel, corteViewModel)
            }
        }
    )
}




