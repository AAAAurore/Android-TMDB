package com.example.monprofil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
            NavHost(
                navController = navController,
                startDestination = "profil"){
                composable("profil"){
                    Profil(navController, windowSizeClass)
                }
                composable( "films") {
                    Films(navController, viewmodel, windowSizeClass)
                }
                /*composable( "series") {
                    Series(navController, viewmodel, windowSizeClass)
                }
                composable( "acteurs") {
                    Acteurs(navController, viewmodel, windowSizeClass)
                }*/
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