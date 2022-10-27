package com.example.monprofil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun NavigationGraph(navHostController: NavHostController,
                    viewmodel: MainViewModel,
                    windowSizeClass: WindowSizeClass
) {
    NavHost(navHostController, startDestination = Boutons.Films.route) {
        composable(Boutons.Films.route) {
            Films(navHostController, viewmodel, windowSizeClass);
        }
        composable(Boutons.Series.route) {
            Series(navHostController, viewmodel, windowSizeClass);
        }
        composable(Boutons.Acteurs.route) {
            Acteurs(navHostController, viewmodel, windowSizeClass);
        }
        composable( "film") {
            Film(navHostController, viewmodel, windowSizeClass);
        }
        composable( "serie") {
            Serie(navHostController, viewmodel, windowSizeClass);
        }
        composable( "acteur") {
            Acteur(navHostController, viewmodel, windowSizeClass);
        }
    }
}

sealed class Boutons(val icon: Int, val label: String, val route: String) {
    object Films : Boutons(R.drawable.ic_baseline_movie_24, "Films", "films");
    object Series : Boutons(R.drawable.ic_baseline_tv_24, "Series", "series");
    object Acteurs : Boutons(R.drawable.ic_baseline_person_24, "Acteurs", "acteurs");
}

val elementBoutons = listOf(
    Boutons.Films,
    Boutons.Series,
    Boutons.Acteurs
)

@Composable
fun Barre(viewmodel: MainViewModel,
          windowSizeClass: WindowSizeClass,
          nbColonne: Int
){
    val navController = rememberNavController();
    val searchClickEtat by viewmodel.searchClickEtat;
    val searchTextEtat by viewmodel.searchTextEtat;
    val filmSerieActeurClickEtat by viewmodel.filmSerieActeurClickEtat;
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomSelected = navBackStackEntry?.destination?.route;
    viewmodel.updateFilmSerieActeurClickEtat(bottomSelected);

    if(nbColonne == 2){
        Scaffold(topBar = {
            AppTopBar(searchClickEtat = searchClickEtat,
                searchTextEtat = searchTextEtat,
                onTextChange = { viewmodel.updateSearchTextEtat(it); },
                onClickClose = {
                    viewmodel.updateSearchTextEtat("");
                    viewmodel.updateSearchClickEtat(SearchClickEtat.CLOSED);
                    viewmodel.updateFilmSerieActeurClickEtat(bottomSelected);
                    if(bottomSelected == "films"){
                        viewmodel.getFilmsInitiaux();
                    }
                    else if(bottomSelected == "series"){
                        viewmodel.getSeriesInitiaux();
                    }
                    else if(bottomSelected == "acteurs"){
                        viewmodel.getActeurs();
                    }
                },
                onClickSearch = { Log.d("Recherche en cours", it); },
                onSearchTriggered = {
                    viewmodel.updateSearchClickEtat(SearchClickEtat.OPENED);
                    viewmodel.updateFilmSerieActeurClickEtat(navBackStackEntry?.destination?.route);
                },
                filmSerieActeurClickEtat = filmSerieActeurClickEtat,
                onClickReturnPage = { navController.popBackStack(); }
            )},
            bottomBar = { BottomBar(navController, viewmodel) }
        ){
            NavigationGraph(navHostController = navController,
                viewmodel = viewmodel,
                windowSizeClass = windowSizeClass);
        }
    }
    else {
        Scaffold(){
            NavigationGraph(navHostController = navController,
                viewmodel = viewmodel,
                windowSizeClass = windowSizeClass);
            SearchFlottant(navController = navController,
                searchClickEtat = searchClickEtat,
                searchTextEtat = searchTextEtat,
                onTextChange = { viewmodel.updateSearchTextEtat(it); },
                onClickClose = {
                    viewmodel.updateSearchTextEtat("");
                    viewmodel.updateSearchClickEtat(SearchClickEtat.CLOSED);
                },
                onClickSearch = { Log.d("Recherche en cours", it); },
                onSearchTriggered = {
                    viewmodel.updateSearchClickEtat(SearchClickEtat.OPENED);
                                    },
                filmSerieActeurClickEtat = filmSerieActeurClickEtat,
                onClickReturnPage = { navController.popBackStack(); }
            );
        }
    }
}

@Composable
fun AppTopBar(searchClickEtat: SearchClickEtat,
              searchTextEtat: String,
              onTextChange: (String) -> Unit,
              onClickClose: () -> Unit,
              onClickSearch: (String) -> Unit,
              onSearchTriggered: () -> Unit,
              filmSerieActeurClickEtat: FilmSerieActeurClickEtat,
              onClickReturnPage: () -> Unit
){
    when(searchClickEtat){
        SearchClickEtat.CLOSED -> {
            when(filmSerieActeurClickEtat){
                FilmSerieActeurClickEtat.NO -> {
                    TopBar(onClickSearch = onSearchTriggered);
                }
                FilmSerieActeurClickEtat.YES -> {
                    TopBarRetour(onClickReturnPage = onClickReturnPage);
                }
            }
        }
        SearchClickEtat.OPENED -> {
            TopBarSearch(text = searchTextEtat,
                        onTextChange = onTextChange,
                        onClickClose = onClickClose,
                        onClickSearch = onClickSearch
            );
        }
    }
}

@Composable
fun TopBar(onClickSearch: () -> Unit){
    TopAppBar(title = { Text("Fav'app"); },
        actions = {
            IconButton(onClick = { onClickSearch(); }) {
                Icon(Icons.Filled.Search, "Rechercher",
                    tint = Color.White);
            }
        }
    )
}

@Composable
fun TopBarRetour(onClickReturnPage: () -> Unit
){
    TopAppBar(navigationIcon = {
        IconButton(onClick = {
            onClickReturnPage();
        }) {
            Icon(
                Icons.Filled.ArrowBack, "Retourner",
                tint = Color.White
            );
        }},
        title = {}
    )
}

@Composable
fun TopBarSearch( text: String,
                  onTextChange: (String) -> Unit,
                  onClickClose: () -> Unit,
                  onClickSearch: (String) -> Unit
) {
    TopAppBar(){
        TextField(modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it); },
            placeholder = {
                Text(
                    text = "Rechercher...",
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                );
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {  },
                ) {
                    Icon(Icons.Filled.Search, "Rechercher");
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isEmpty()) {
                            onTextChange("");
                            onClickClose();
                        } else {
                            onClickClose();
                        }
                    }
                ) {
                    Icon(Icons.Filled.Close, "Fermer");
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onClickSearch(text); }),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        );
    }
}

@Composable
fun BottomBar(navController: NavController,
              viewmodel: MainViewModel
){
    val searchClickEtat by viewmodel.searchClickEtat;
    val filmSerieActeurClickEtat by viewmodel.filmSerieActeurClickEtat;

    when(searchClickEtat){
        SearchClickEtat.CLOSED -> {
            when(filmSerieActeurClickEtat) {
                FilmSerieActeurClickEtat.NO -> {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState();

                        elementBoutons.forEach { bouton ->
                            val bottomSelected = navBackStackEntry?.destination?.route;
                            val colorIcon: ColorFilter;

                            if (bottomSelected == bouton.route) {
                                colorIcon = ColorFilter.tint(Color.White);
                            } else {
                                colorIcon = ColorFilter.tint(Color.LightGray);
                            }

                            BottomNavigationItem(
                                icon = {
                                    Image(
                                        painterResource(bouton.icon),
                                        contentDescription = bouton.label,
                                        colorFilter = colorIcon
                                    )
                                },
                                label = { Text(text = bouton.label); },
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
                            );
                        }
                    }
                }
                FilmSerieActeurClickEtat.YES -> {}
            }
        }
        SearchClickEtat.OPENED -> {}
    }
}

@Composable
fun SearchFlottant(navController: NavController,
                   searchClickEtat: SearchClickEtat,
                   searchTextEtat: String,
                   onTextChange: (String) -> Unit,
                   onClickClose: () -> Unit,
                   onClickSearch: (String) -> Unit,
                   onSearchTriggered: () -> Unit,
                   filmSerieActeurClickEtat: FilmSerieActeurClickEtat,
                   onClickReturnPage: () -> Unit
){
    when(searchClickEtat){
        SearchClickEtat.CLOSED -> {
            when(filmSerieActeurClickEtat){
                FilmSerieActeurClickEtat.NO -> {
                    BoutonFlottantSearch(onSearchTriggered);
                    BottomBarPaysage(navController);
                }
                FilmSerieActeurClickEtat.YES -> {
                    TopBarRetour(onClickReturnPage = onClickReturnPage);
                }
            }
        }
        SearchClickEtat.OPENED -> {
            TopBarSearch(text = searchTextEtat,
                onTextChange = onTextChange,
                onClickClose = onClickClose,
                onClickSearch = onClickSearch
            );
        }
    }
}

@Composable
fun BottomBarPaysage(navController: NavController){
    NavigationRail{
        val navBackStackEntry by navController.currentBackStackEntryAsState();

        elementBoutons.forEach { bouton ->
            val bottomSelected = navBackStackEntry?.destination?.route;
            val colorIcon: ColorFilter;

            if(bottomSelected == bouton.route) {
                colorIcon = ColorFilter.tint(Color.Blue);
            }
            else{
                colorIcon = ColorFilter.tint(Color.Gray);
            }

            NavigationRailItem(
                icon = { Image(
                    painterResource(bouton.icon),
                    contentDescription = bouton.label,
                    colorFilter = colorIcon); },
                label = { Text(text = bouton.label,
                    color = Color.Gray); },
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
            );
        }
    }
}

@Composable
fun BoutonFlottantSearch(onClickSearch: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(onClick = { onClickSearch(); },
            backgroundColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(Icons.Filled.Search, "Rechercher");
        };
    }
}
