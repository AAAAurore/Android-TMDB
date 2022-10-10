package com.example.monprofil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.monprofil.ui.theme.MonProfilTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewmodel: MainViewModel by viewModels()
            val windowSizeClass = calculateWindowSizeClass(this)
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        NavHost(
                            navController = navController,
                            startDestination = "profil"){
                            composable("profil"){
                                Profil(navController, windowSizeClass)
                            }
                            composable( "films") {
                                Films(navController, viewmodel, windowSizeClass)
                            }
                            composable( "series") {
                                Series(navController, viewmodel, windowSizeClass)
                            }
                            composable( "acteurs") {
                                Acteurs(navController, viewmodel, windowSizeClass)
                            }
                            composable( "barre") {
                                Barre(navController, viewmodel, windowSizeClass, 2)
                            }
                        }
                    }
                }
                else -> {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        NavHost(
                            navController = navController,
                            startDestination = "profil"){
                            composable("profil"){
                                Profil(navController, windowSizeClass)
                            }
                            composable( "films") {
                                Films(navController, viewmodel, windowSizeClass)
                            }
                            composable( "series") {
                                Series(navController, viewmodel, windowSizeClass)
                            }
                            composable( "acteurs") {
                                Acteurs(navController, viewmodel, windowSizeClass)
                            }
                            composable( "barre") {
                                Barre(navController, viewmodel, windowSizeClass, 3)
                            }
                        }
                    }
                }
            }
            /*MonProfilTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                }
            }*/
        }
    }
}