package com.example.monprofil

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Films(navController: NavController, viewmodel: MainViewModel, windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                //Barre(navController, viewmodel, windowSizeClass, 2)
                TousLesFilms(viewmodel, 2)
            }
        }
        else -> {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                //Barre(navController, viewmodel, windowSizeClass,3)
                TousLesFilms(viewmodel, 3)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TousLesFilms(viewmodel: MainViewModel, nbColonne: Int){
    val films by viewmodel.movies.collectAsState()

    if(films.isEmpty())
    {
        viewmodel.getFilmsInitiaux();
    }

    val taille: Modifier

    if(nbColonne == 2) {
        taille = Modifier.fillMaxWidth().padding(bottom = 65.dp)
    }
    else{
        taille = Modifier.padding(start = 75.dp)
    }

    LazyVerticalGrid(cells = GridCells.Fixed(nbColonne),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = taille) {
        items(films) { film ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable { film.id },
                elevation = 10.dp
            ) {
                Column() {
                    val image = "https://image.tmdb.org/t/p/w500" + film.poster_path
                    Image(painter = rememberAsyncImagePainter(image),
                        contentDescription = "Poster",
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally))
                    Text(text = film.title,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    val date = SimpleDateFormat("yyyy-MM-dd").parse(film.release_date);
                    val formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(date);
                    Text(text = formatDate,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
        }
    }
}