package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Profil(windowSizeClass: WindowSizeClass, navController: NavController){
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.TopCenter) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Presentation()
                        Lien(navController)
                    }
                }
            }
        }
        else -> {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.TopCenter) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Presentation()
                    }
                }
                Box(contentAlignment = Alignment.TopCenter) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Lien(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Presentation(){
    Spacer(modifier = Modifier.size(40.dp))
    Image(
        painterResource(R.drawable.photo), contentDescription = "Ma photo de profil",
        modifier = Modifier
            .clip(CircleShape)
            .size(150.dp)
            .border(1.dp, Color.Black, CircleShape)
    )
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        text = "Aurore BIADOS",
        fontSize = 30.sp
    )
    Spacer(modifier = Modifier.size(15.dp))
    Text(
        text = "Étudiante en 3ème année du cycle\ningénieur\"Informatique pour la Santé\"",
        textAlign = TextAlign.Center
    )
    Text(
        text = "École d'ingénieur ISIS - INU Champollion",
        fontStyle = FontStyle.Italic
    )
    Spacer(modifier = Modifier.size(50.dp))
}

@Composable
fun Lien(navController: NavController){
    Column() {
        Row() {
            Image(
                //painterResource(R.drawable.mail), contentDescription = "Mon adresse-mail",
                imageVector = Icons.Filled.MailOutline,
                contentDescription = "Mon adresse-mail",
                modifier = Modifier.size(25.dp)
            )
            Text(text = "  aurore.biados@etud.univ-jfc.fr")
        }
        Row(){
            Image(
                painterResource(R.drawable.linkedin), contentDescription = "Mon LinkedIn",
                modifier = Modifier.size(25.dp)
            )
            Text(text = "  https://fr.linkedin.com/in/aurore-biados")
        }
    }
    Spacer(modifier = Modifier.size(70.dp))
    Button(
        onClick  = {navController.navigate("films")},
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp,
        )
    ) {
        Text("Démarrer")
    }
}

/*@Composable
fun Photo() {
    Image(
        painterResource(R.drawable.photo), contentDescription = "Ma photo de profil",
        modifier = Modifier
            .clip(CircleShape)
            .size(150.dp)
            .border(1.dp, Color.Black, CircleShape)
    )
}

@Composable
fun Nom() {
    Text(
        text = "Aurore BIADOS",
        fontSize = 30.sp
    )
}

@Composable
fun Profil() {
    Text(
        text = "Étudiante en 3ème année du cycle\ningénieur\"Informatique pour la Santé\"",
        textAlign = TextAlign.Center
    )
    Text(
        text = "École d'ingénieur ISIS - INU Champollion",
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun Lien() {
    Column() {
        Row() {
            Image(
                //painterResource(R.drawable.mail), contentDescription = "Mon adresse-mail",
                imageVector = Icons.Filled.MailOutline,
                contentDescription = "Mon adresse-mail",
                modifier = Modifier.size(25.dp)
            )
            Text(text = "  aurore.biados@etud.univ-jfc.fr")
        }
        Row(){
            Image(
                painterResource(R.drawable.linkedin), contentDescription = "Mon LinkedIn",
                modifier = Modifier.size(25.dp)
            )
            Text(text = "  https://fr.linkedin.com/in/aurore-biados")
        }
    }
}

@Composable
fun Bouton() {
    Button(
        onClick  = {},
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp,
        )
    ) {
        Text("Démarrer")
    }
}*/