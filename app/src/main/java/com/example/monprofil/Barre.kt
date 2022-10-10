/*package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun Barre(navController: NavController, viewmodel: MainViewModel, nbColonne: Int, contenu : Unit){
    val navController = rememberNavController()
    if(nbColonne == 2){
        Scaffold(topBar = { TopBar() },
            content = { /*contenu*/ },
            bottomBar = { BottomBar(navController) }
        )
    }
    else {
        Scaffold(
            bottomBar = { BottomBarPaysage(navController) },
            content = { contenu },
        )
    }
}

sealed class Boutons(val icon: Int, val label: String, val route: String) {
    object Films : Boutons(R.drawable.ic_baseline_movie_24, "Films", "films")
    object Series : Boutons(R.drawable.ic_baseline_tv_24, "Series", "series")
    object Acteurs : Boutons(R.drawable.ic_baseline_person_24, "Acteurs", "acteurs")
}

val elementBoutons = listOf(
    Boutons.Films,
    Boutons.Series,
    Boutons.Acteurs
)

@Composable
fun TopBar(){
    TopAppBar(title = { Text("Fav'app") },
        actions = { IconButton(onClick = {} ) {
            Icon(Icons.Filled.Search, "Rechercher")
        }
        })
}

/*@Composable
fun Content(navController: NavController, viewmodel: MainViewModel, nbColonne: Int){
    TousLesFilms(viewmodel, nbColonne)
}*/

@Composable
fun BottomBar(navController: NavController){
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        elementBoutons.forEach { bouton ->
            BottomNavigationItem(
                icon = { Image(
                    painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = ColorFilter.tint(Color.White)) },
                label = { Text(text = bouton.label) },
                selected = currentDestination?.hierarchy?.any { it.route == bouton.route } == true,
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
            NavigationRailItem(
                icon = { Image(
                    painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = ColorFilter.tint(Color.Gray)) },
                label = { Text(text = bouton.label,
                    color = Color.Gray) },
                selected = currentDestination?.hierarchy?.any { it.route == bouton.route } == true,
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
}*/