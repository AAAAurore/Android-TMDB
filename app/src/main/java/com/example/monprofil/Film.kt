package com.example.monprofil

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import java.util.*

@Composable
fun Film(navController: NavController,
         viewmodel: MainViewModel,
         windowSizeClass: WindowSizeClass
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                DescriptionFilm(navController, viewmodel, 2);
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                DescriptionFilm(navController, viewmodel, 3);
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@Composable
fun DescriptionFilm(navController: NavController,
                    viewmodel: MainViewModel,
                    nbColonne: Int
) {
    val film: ModelFilmOnly by viewmodel.movie.collectAsState();
    val idFilm by viewmodel.film;
    val taille: Modifier;
    val scrollState = rememberScrollState(0);
    val backdrop = "https://image.tmdb.org/t/p/w500" + film.backdrop_path;
    val poster = "https://image.tmdb.org/t/p/w500" + film.poster_path;
    val acteursFilm by viewmodel.creditsMovie.collectAsState();
    val date by viewmodel.dateFilmSerie;

    viewmodel.getFilm(idFilm);

    if(nbColonne == 3) {
        taille = Modifier
            .padding(top = 50.dp)
    }
    else{
        taille = Modifier;
    }

    viewmodel.DistributionFilm(idFilm);

    Column(modifier = taille.verticalScroll(scrollState).padding(horizontal = 10.dp)) {
        Text(
            text = film.title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        );
        if (film.backdrop_path != null) {
            Image(
                painter = rememberAsyncImagePainter(backdrop),
                contentDescription = "Backdrop",
                modifier = Modifier
                    .fillMaxWidth().size(195.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillWidth
            );
        };
        Row() {
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
            }
            Column() {
                Text(text = date,
                    color = Color.Gray
                );
                Spacer(modifier = Modifier.size(5.dp));
                ListeGenres(film);
            }
        }
        Spacer(modifier = Modifier.size(5.dp));
        Text(
            text = "Synopsis",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        );
        Spacer(modifier = Modifier.size(5.dp));
        Text(text = film.overview);
        Spacer(modifier = Modifier.size(10.dp));
        Text(
            text = "Têtes d'affiche",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        );
        Spacer(modifier = Modifier.size(5.dp));
        FlowRow(modifier = Modifier.align(Alignment.CenterHorizontally)){
            (acteursFilm).forEach() { acteur ->
                CartesActeurs(viewmodel, navController, acteur, nbColonne);
            }
        }
    }
}

@Composable
fun ListeGenres(film: ModelFilmOnly){
    val listeGenres = film.genres;

    if(listeGenres.isNotEmpty()){
        (listeGenres).forEach() { genre ->
            Text(text =
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontStyle = FontStyle.Italic)
                ) {
                    append(genre.name);
                    if (listeGenres.last() != genre) {
                        append(", ");
                    }
                }
            }
        )}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartesActeurs(viewmodel: MainViewModel,
                  navController: NavController,
                  acteur: ModelCastDistributionFilm,
                  nbColonne: Int
){
    val profile = "https://image.tmdb.org/t/p/w500" + acteur.profile_path;
    val taille: Modifier;

    if(nbColonne == 2) {
        taille = Modifier.fillMaxWidth(0.5F);
    }
    else{
        taille = Modifier.fillMaxWidth(0.33F);
    }

    Card(
        modifier = taille.padding(5.dp),
        elevation = AppBarDefaults.BottomAppBarElevation,
        onClick = {
            viewmodel.updateActeur(acteur.id);
            viewmodel.updateFilmSerieActeurClickEtat("acteur")
            navController.navigate("acteur");
        }
    ) {
        Column() {
            if (acteur.profile_path != null) {
                Image(
                    painter = rememberAsyncImagePainter(profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally)
                );
            };
            Text(
                text = acteur.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            );
            Spacer(modifier = Modifier.size(10.dp));
            Text(
                text = acteur.character,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            );
            Spacer(modifier = Modifier.size(5.dp));
        }
    }
}