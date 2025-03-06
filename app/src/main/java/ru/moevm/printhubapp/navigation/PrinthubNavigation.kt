package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.moevm.printhubapp.presentation.printhub.MainPrinthubScreen
import ru.moevm.printhubapp.presentation.printhub.ProfilePrinthubScreen
import ru.moevm.printhubapp.presentation.printhub.StatisticScreen

fun NavGraphBuilder.printhubNavigation(navController: NavHostController) {
    composable(
        route = Screen.MainPrinthubScreen.route
    ) {
        MainPrinthubScreen(
            navHostController = navController,
            onAbout = {
                navController.navigate(Screen.AboutScreen.route)
            },
            onOrderDetails = {}
        )
    }

    composable(
        route = Screen.PrinthubProfileScreen.route
    ) {
        ProfilePrinthubScreen(
            navHostController = navController,
            onAbout = {
                navController.navigate(Screen.AboutScreen.route)
            },
            onStatistic = {
                navController.navigate(Screen.StatisticScreen.route)
            }
        )
    }

    composable(
        route = Screen.StatisticScreen.route
    ) {
        StatisticScreen(
            onBack = {
                navController.popBackStack()
            },
            onAbout = {
                navController.navigate(Screen.AboutScreen.route)
            }
        )
    }
}

