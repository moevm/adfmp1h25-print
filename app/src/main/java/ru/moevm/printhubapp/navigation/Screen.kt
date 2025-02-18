package ru.moevm.printhubapp.navigation

sealed class Screen(val route: String) {
    object AuthScreen : Screen(ROUTER_AUTH)
    object RegistrationScreen : Screen(ROUTER_REGISTRATION)

    companion object {
        private const val ROUTER_AUTH = "auth_screen"
        private const val ROUTER_REGISTRATION = "registration_screen"
    }
}