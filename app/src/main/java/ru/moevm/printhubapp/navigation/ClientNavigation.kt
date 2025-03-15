package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.moevm.printhubapp.presentation.client.components.AddOrderParametersScreen
import ru.moevm.printhubapp.presentation.client.components.AddOrderScreen
import ru.moevm.printhubapp.presentation.client.components.ClientProfileScreen
import ru.moevm.printhubapp.presentation.client.components.MainClientScreen
import ru.moevm.printhubapp.presentation.client.components.OrderDetailsScreen
import ru.moevm.printhubapp.presentation.client.components.SuccessOrderScreen

fun NavGraphBuilder.clientNavigation(navHostController: NavHostController) {
    composable(route = Screen.MainClientScreen.route) {
        MainClientScreen(
            navHostController = navHostController,
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            addOrder = {
                navHostController.navigate(Screen.AddOrderScreen.route)
            },
            showOrderDetails = { orderId ->
                navHostController.navigate("${Screen.OrderDetailsScreen.route}/$orderId")
            }
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
            },
            onNavigateTo = {
                navHostController.navigate(Screen.AddOrderParametersScreen.route)
            }
        )
    }

    composable(
        route = Screen.AddOrderParametersScreen.route
    ) {
        AddOrderParametersScreen(
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            onBack = {
                navHostController.popBackStack()
            },
            createOrder = {
                navHostController.navigate(Screen.SuccessScreen.route)
            }
        )
    }

    composable(
        route = Screen.SuccessScreen.route
    ) {
        SuccessOrderScreen(
            onNavigateHome = {
                navHostController.navigate(Screen.MainClientScreen.route) {
                    popUpTo(Screen.SuccessScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable(
        route = "${Screen.OrderDetailsScreen.route}/{orderId}",
        arguments = listOf(navArgument("orderId") { type = NavType.StringType })
    ) { backStackEntry ->
        val orderId = backStackEntry.arguments?.getString("orderId") ?: "1"
        OrderDetailsScreen(
            onBack = {
                navHostController.popBackStack()
            },
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            orderId = orderId
        )
    }

    composable(
        route = Screen.ClientProfileScreen.route
    ) {
        ClientProfileScreen(
            navHostController = navHostController,
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            onLogout = {
                navHostController.navigate(Screen.AuthScreen.route) {
                    popUpTo(Screen.ClientProfileScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}