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
fun Series(navController: NavController,
           viewmodel: MainViewModel,
           windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                ToutesLesSeries(navController, viewmodel, windowSizeClass, 2);
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                ToutesLesSeries(navController, viewmodel, windowSizeClass, 3);
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ToutesLesSeries(navController: NavController,
                    viewmodel: MainViewModel,
                    windowSizeClass: WindowSizeClass,
                    nbColonne: Int
) {
    val series by viewmodel.tvs.collectAsState();
    val rechercheEtat by viewmodel.searchClickEtat;
    val recherche by viewmodel.searchTextEtat;
    var bottom: Dp = 0.dp;
    val taille: Modifier;

    when(rechercheEtat){
        SearchClickEtat.CLOSED -> {
            if(series.isEmpty() && recherche.isEmpty()){
                viewmodel.getSeriesInitiaux();
                bottom = AppBarDefaults.BottomAppBarElevation;
            }
        }
        SearchClickEtat.OPENED -> {
            if(recherche.isNotEmpty()){
                viewmodel.getSeriesSearch(recherche);
            }
            bottom = 0.dp;
        }
    }

    if(nbColonne == 2) {
        taille = Modifier.fillMaxWidth();
    }
    else {
        taille = Modifier.padding(start = 75.dp);
    }

    LazyVerticalGrid(cells = GridCells.Fixed(nbColonne),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = taille
    ) {
        items(series) { serie ->
            val poster = "https://image.tmdb.org/t/p/w500" + serie.poster_path
            val date = SimpleDateFormat("yyyy-MM-dd").parse(serie.first_air_date);
            val formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(date);

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                elevation = bottom,
                onClick = {
                    viewmodel.updateSerie(serie.id);
                    viewmodel.updateDateFilmSerie(formatDate);
                    viewmodel.updateFilmSerieActeurClickEtat("serie")
                    navController.navigate("serie");
                }
            ) {
                Column() {
                    if (poster != null) {
                        Image(
                            painter = rememberAsyncImagePainter(poster),
                            contentDescription = "Poster",
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.CenterHorizontally)
                        );
                    };
                    Text(text = serie.name,
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