package ru.moevm.printhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(navHostController: NavHostController, startDestination: String) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        authNavigation(navHostController)
        aboutNavigation(navHostController)
        clientNavigation(navHostController)
        printhubNavigation(navHostController)
    }
}