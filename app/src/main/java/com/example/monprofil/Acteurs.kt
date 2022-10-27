package com.example.monprofil

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Acteurs(navController: NavController,
            viewmodel: MainViewModel,
            windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                TousLesActeurs(navController, viewmodel, 2);
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                TousLesActeurs(navController, viewmodel, 3);
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TousLesActeurs(navController: NavController,
                   viewmodel: MainViewModel,
                   nbColonne: Int
) {
    val acteurs by viewmodel.credits.collectAsState();
    val rechercheEtat by viewmodel.searchClickEtat;
    val recherche by viewmodel.searchTextEtat;
    var bottom: Dp = 0.dp;
    val taille: Modifier;

    when(rechercheEtat){
        SearchClickEtat.CLOSED -> {
            if(acteurs.isEmpty() && recherche.isEmpty()){
                viewmodel.getActeurs();
                bottom = AppBarDefaults.BottomAppBarElevation;
            }
        }
        SearchClickEtat.OPENED -> {
            if(recherche.isNotEmpty()){
                viewmodel.getActeursSearch(recherche);
            }
            bottom = 0.dp;
        }
    }

    if (nbColonne == 2) {
        taille = Modifier.fillMaxWidth();
    } else {
        taille = Modifier.padding(start = 75.dp);
    }

    LazyVerticalGrid(
        cells = GridCells.Fixed(nbColonne),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = taille
    ) {
        items(acteurs) { acteur ->
            val poster = "https://image.tmdb.org/t/p/w500" + acteur.profile_path

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                elevation = bottom,
                onClick = {
                    viewmodel.updateActeur(acteur.id);
                    viewmodel.updateFilmSerieActeurClickEtat("acteur")
                    navController.navigate("acteur");
                }
            ) {
                Column() {
                    if(poster != null){
                        Image(
                            painter = rememberAsyncImagePainter(poster),
                            contentDescription = "Poster",
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    };
                    Text(
                        text = acteur.name,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    );
                    Spacer(modifier = Modifier.size(5.dp));
                }
            }
        }
    }
}