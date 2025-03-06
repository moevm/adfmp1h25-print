package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.moevm.printhubapp.presentation.printhub.MainPrinthubScreen

fun NavGraphBuilder.printhubNavigation(navController: NavHostController) {
    composable(
        route = Screen.MainPrinthubScreen.route
    ) {
        MainPrinthubScreen(
            onAbout = {
                navController.navigate(Screen.AboutScreen.route)
            },
            onNavigateTo = { screen ->

            },
            onOrderDetails = {}
        )
    }
}