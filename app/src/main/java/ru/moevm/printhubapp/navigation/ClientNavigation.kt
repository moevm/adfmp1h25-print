package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.moevm.printhubapp.presentation.client.AddOrderScreen
import ru.moevm.printhubapp.presentation.client.MainClientScreen

fun NavGraphBuilder.clientNavigation(navHostController: NavHostController) {
    composable(route = Screen.MainClientScreen.route) {
        MainClientScreen(
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            addOrder = {
                navHostController.navigate(Screen.AddOrderScreen.route)
            },
            showOrderDetails = {}
        )
    }

    composable(
        route = Screen.AddOrderScreen.route
    ) {
        AddOrderScreen(
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            onBack = {
                navHostController.popBackStack()
            }
        )
    }
}