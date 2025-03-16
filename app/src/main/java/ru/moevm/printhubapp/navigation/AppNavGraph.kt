package ru.moevm.printhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.moevm.printhubapp.presentation.client.viewmodels.MainClientViewModel

@Composable
fun AppNavGraph(navHostController: NavHostController) {
    val viewModel: MainClientViewModel = hiltViewModel()
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route
    ) {

        splashNavigation(navHostController)
        authNavigation(navHostController)
        aboutNavigation(navHostController)
        clientNavigation(navHostController, viewModel)
        printhubNavigation(
            navController = navHostController,
            onLogout = {
                navHostController.navigate(Screen.AuthScreen.route) {
                    popUpTo(Screen.PrinthubProfileScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}