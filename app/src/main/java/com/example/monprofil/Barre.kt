package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(navHostController: NavHostController, viewmodel: MainViewModel, windowSizeClass: WindowSizeClass) {
    NavHost(navHostController, startDestination = Boutons.Films.route) {
        composable(Boutons.Films.route) {
            Films(navHostController, viewmodel, windowSizeClass)
        }
        composable(Boutons.Series.route) {
            Series(navHostController, viewmodel, windowSizeClass)
        }
        composable(Boutons.Acteurs.route) {
            Acteurs(navHostController, viewmodel, windowSizeClass)
        }
    }
}

@Composable
fun Barre(navController: NavController, viewmodel: MainViewModel, windowSizeClass: WindowSizeClass, nbColonne: Int){
    val navController = rememberNavController()
    if(nbColonne == 2){
        Scaffold(topBar = { TopBar() },
            bottomBar = { BottomBar(navController) }
        ){
            NavigationGraph(navHostController = navController,
                            viewmodel = viewmodel,
                            windowSizeClass = windowSizeClass)
        }
    }
    else {
        Scaffold(
            bottomBar = { BottomBarPaysage(navController) },
        ){
            NavigationGraph(navHostController = navController,
                            viewmodel = viewmodel,
                            windowSizeClass = windowSizeClass)
        }
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

@Composable
fun BottomBar(navController: NavController){
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        elementBoutons.forEach { bouton ->
            val bottomSelected = navBackStackEntry?.destination?.route;
            val colorIcon: ColorFilter
            if(bottomSelected == bouton.route) {
                colorIcon = ColorFilter.tint(Color.White)
            }
            else{
                colorIcon = ColorFilter.tint(Color.LightGray)
            }
            BottomNavigationItem(
                icon = { Image(
                    painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = colorIcon) },
                label = { Text(text = bouton.label) },
                selected = bottomSelected == bouton.route,
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
        elementBoutons.forEach { bouton ->
            val bottomSelected = navBackStackEntry?.destination?.route;
            val colorIcon: ColorFilter
            if(bottomSelected == bouton.route) {
                colorIcon = ColorFilter.tint(Color.Blue)
            }
            else{
                colorIcon = ColorFilter.tint(Color.Gray)
            }
            NavigationRailItem(
                icon = { Image(
                    painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = colorIcon) },
                label = { Text(text = bouton.label,
                    color = Color.Gray) },
                selected = bottomSelected == bouton.route,
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