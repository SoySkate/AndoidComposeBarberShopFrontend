package com.example.barbershopapp
import android.content.res.Configuration
import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.barbershopapp.ui.theme.BarberShopAppTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
/*
private val listaNegocios :List<Business> = listOf(
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC"),
    Business("Barberia","Tio JuanC")
)*/
data class Business(val businessName: String, val businessOwner: String)
//data class Cortes(val name: String, val price: String)
private val negocio = Business("Barberia","Tio JuanC")


@Composable
fun Navigation() {
    val navController = rememberNavController() // Crear un NavController
    NavHost(navController = navController, startDestination = "home") {
        composable("cortes") { CortesScreenPreview() } //pantalla cortes
        composable("home") { PreviewButtons(navController) } //pantalla home") { CortesScreenPreview() } //pantalla cortes
        composable("barberos") { BarberScreenPreview()  } // Pantalla de barberos
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
fun MySideTexts(business: Business) {
    //layout para represatncion de columnas
    Column {
        MyText(business.businessName,MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleLarge)
        //Spacer(Modifier.height(10.dp))
        MyText(business.businessOwner, Color.Red, style = MaterialTheme.typography.titleSmall)
    }
}


@Composable
fun MyComponent(business: Business) {
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
        //Spacer(Modifier.width(10.dp)) // Ajuste del espaciado
        MySideTexts(business)
    }
}


@Composable
fun PreviewComponents() {
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
            MyComponent(negocio)
            Navigation()
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
            //Spacer(Modifier.height(100.dp))

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

            //Spacer(Modifier.height(10.dp))

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

            //Spacer(Modifier.height(10.dp))

            Button(
                onClick = { /* Acción al hacer clic en el botón */ },
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
fun SettingsNavBar() {

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
        }
    }
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            SettingsNavBar() // Coloca la barra de navegación en la parte inferior
        },
        content = { innerPadding ->
            // Aquí va el contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PreviewComponents()
            }
        }
    )
}




