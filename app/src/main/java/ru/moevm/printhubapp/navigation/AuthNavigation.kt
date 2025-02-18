package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.moevm.printhubapp.presentation.auth.AuthScreen
import ru.moevm.printhubapp.presentation.auth.RegistrationScreen

fun NavGraphBuilder.authNavigation(navHostController: NavHostController) {
    composable(
        route = Screen.AuthScreen.route
    ) {
        AuthScreen(
            onLoginTo = {},
            onRegistration = {
                navHostController.navigate(Screen.RegistrationScreen.route)
            },
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            }
        )
    }
    composable(
        route = Screen.RegistrationScreen.route
    ) {
        RegistrationScreen(
            onRegistration = {},
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            }
        )
    }
}