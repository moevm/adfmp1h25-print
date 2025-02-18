package ru.moevm.printhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.AuthScreen.route) {
        authNavigation(navHostController)
        aboutNavigation(navHostController)
    }
}