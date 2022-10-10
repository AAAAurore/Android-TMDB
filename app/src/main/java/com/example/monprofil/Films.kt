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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                Barre(viewmodel, 2)
            }
        }
        else -> {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Barre(viewmodel, 3)
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

    val espace: Int
    val taille: Modifier

    if(nbColonne == 2) {
        espace = 0
        taille = Modifier.fillMaxWidth()
    }
    else{
        espace = 80
        taille = Modifier.width(500.dp)
    }

    LazyVerticalGrid(cells = GridCells.Fixed(nbColonne),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = taille.offset(x = espace.dp)) {
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
                        modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally))
                    Text(text = film.title,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    val date = SimpleDateFormat("yyyy-MM-dd").parse(film.release_date);
                    val formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(date);
                    Text(text = formatDate,
                        color = Color.Gray)
                }
            }
        }
    }
}
sealed class Boutons(val icon: Int, val label: String, val route: String) {
    object Films : Boutons(R.drawable.ic_baseline_movie_24, "Films", "films")
    object Series : Boutons(R.drawable.ic_baseline_tv_24, "Séries", "series")
    object Acteurs : Boutons(R.drawable.ic_baseline_person_24, "Acteurs", "acteurs")
}

val elementBoutons = listOf(
    Boutons.Films,
    Boutons.Series,
    Boutons.Acteurs
)

@Composable
fun TopBar(navController : NavController){
    TopAppBar(title = { Text("Fav'app") },
        actions = { IconButton(onClick = { navController.navigate("films") } ) {
            Icon(Icons.Filled.Search, "Rechercher")
        }})
}

@Composable
fun BottomBar(navController: NavController){
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        elementBoutons.forEach { bouton ->
            /*val bottomSelected = remember{ mutableStateOf(0) };
            var bottomSelected by remember {
                mutableStateOf(0)
            }*/
            val bottomSelected = currentDestination?.hierarchy?.any { it.route == bouton.route };
            val colorIcon: ColorFilter
            if(bottomSelected == true) {
                colorIcon = ColorFilter.tint(Color.White)
            }
            else{
                colorIcon = ColorFilter.tint(Color.LightGray)
            }
            BottomNavigationItem(
                icon = { Image(painterResource(bouton.icon),
                        contentDescription = bouton.label,
                        colorFilter = (colorIcon)) },
                label = { Text(text = bouton.label) },
                selected = bottomSelected == true,
                onClick = {
                    navController.navigate(bouton.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarPaysage(navController: NavController){
    NavigationRail{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        elementBoutons.forEach { bouton ->
            val bottomSelected = currentDestination?.hierarchy?.any { it.route == bouton.route };
            NavigationRailItem(
                icon = { Image(painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = ColorFilter.tint(Color.Gray)) },
                label = { Text(text = bouton.label,
                            color = Color.Gray) },
                selected = bottomSelected == true,
                onClick = {
                    navController.navigate(bouton.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Barre(viewmodel: MainViewModel, nbColonne: Int){
    val navController = rememberNavController()
    if(nbColonne == 2){
        Scaffold(topBar = { TopBar(navController) },
                content = { TousLesFilms(viewmodel, nbColonne) },
                bottomBar = { BottomBar(navController) }
        ) /*{ innerPadding ->
            NavHost(navController,
                    startDestination = Boutons.Films.route,
                    modifier = Modifier.padding(innerPadding)){
                        composable(Boutons.Films.route) {
                            Films(navController, viewmodel, windowSizeClass)
                        }
                        composable(Boutons.Series.route) {
                            Series(navController, viewmodel, windowSizeClass)
                        }
                        composable(Boutons.Acteurs.route) {
                            Acteurs(navController, viewmodel, windowSizeClass)
                        }
                }
            }*/

        }
    else {
        Scaffold(
            bottomBar = { BottomBarPaysage(navController) },
            content = { TousLesFilms(viewmodel, nbColonne) },
        )
    }
}