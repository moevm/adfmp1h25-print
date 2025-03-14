package ru.moevm.printhubapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.moevm.printhubapp.presentation.client.AddOrderParametersScreen
import ru.moevm.printhubapp.presentation.client.AddOrderScreen
import ru.moevm.printhubapp.presentation.client.ClientProfileScreen
import ru.moevm.printhubapp.presentation.client.MainClientScreen
import ru.moevm.printhubapp.presentation.client.OrderDetailsScreen
import ru.moevm.printhubapp.presentation.client.SuccessOrderScreen

fun NavGraphBuilder.clientNavigation(navHostController: NavHostController) {
    composable(route = Screen.MainClientScreen.route) {
        MainClientScreen(
            navHostController = navHostController,
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
            addOrder = {
                navHostController.navigate(Screen.AddOrderParametersScreen.route)
            },
            showOrderDetails = {
                navHostController.navigate(Screen.OrderDetailsScreen.route)
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
                navHostController.navigate(Screen.SuccessScreen.route)
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
                navHostController.navigate(Screen.AddOrderScreen.route)
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
        route = Screen.OrderDetailsScreen.route
    ) {
        OrderDetailsScreen(
            idOrder = 1,
            onBack = {
                navHostController.popBackStack()
            },
            onAbout = {
                navHostController.navigate(Screen.AboutScreen.route)
            },
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