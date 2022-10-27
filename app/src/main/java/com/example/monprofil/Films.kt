package com.example.monprofil

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun Films(navController: NavController,
          viewmodel: MainViewModel,
          windowSizeClass: WindowSizeClass
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                TousLesFilms(navController, viewmodel, 2);
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                TousLesFilms(navController, viewmodel, 3);
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TousLesFilms(navController: NavController,
                 viewmodel: MainViewModel,
                 nbColonne: Int
){
    val films by viewmodel.movies.collectAsState();
    val rechercheEtat by viewmodel.searchClickEtat;
    val recherche by viewmodel.searchTextEtat;
    var topBottom: Dp = 0.dp;
    var taille: Modifier = Modifier;

    when(rechercheEtat){
        SearchClickEtat.CLOSED -> {
            if(films.isEmpty() || recherche.isEmpty()){
                viewmodel.getFilmsInitiaux();
            }
            if(nbColonne == 2) {
                topBottom = AppBarDefaults.BottomAppBarElevation;
                taille = Modifier.padding(bottom = 65.dp);
            }
            else{
                topBottom = 0.dp;
                taille = Modifier.padding(start = 75.dp);
            }
        }
        SearchClickEtat.OPENED -> {
            if(recherche.isNotEmpty()){
                viewmodel.getFilmsSearch(recherche);
            }
            if(nbColonne == 3) {
                taille = Modifier.padding(top = 65.dp);
            }
        }
    }

    LazyVerticalGrid(cells = GridCells.Fixed(nbColonne),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = taille
    ) {
        items(films) { film ->
            val poster = "https://image.tmdb.org/t/p/w500" + film.poster_path;
            val date = SimpleDateFormat("yyyy-MM-dd").parse(film.release_date);
            val formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(date);

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                elevation = topBottom,
                onClick = {
                    viewmodel.updateFilm(film.id);
                    viewmodel.updateDateFilmSerie(formatDate);
                    viewmodel.updateFilmSerieActeurClickEtat("film")
                    navController.navigate("film");
                }
            ) {
                Column() {
                    if (film.poster_path != null) {
                        Image(
                            painter = rememberAsyncImagePainter(poster),
                            contentDescription = "Poster",
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.CenterHorizontally)
                        );
                    };
                    Text(text = film.title,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    );
                    Spacer(modifier = Modifier.size(8.dp));
                    Text(text = formatDate,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    );
                    Spacer(modifier = Modifier.size(5.dp));
                }
            }
        }
    }
}