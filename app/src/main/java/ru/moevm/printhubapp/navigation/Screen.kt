package ru.moevm.printhubapp.navigation

sealed class Screen(val route: String) {
    object AuthScreen : Screen(ROUTER_AUTH)
    object RegistrationScreen : Screen(ROUTER_REGISTRATION)

    object AboutScreen : Screen(ROUTER_ABOUT)

    object MainClientScreen : Screen(ROUTER_MAIN_CLIENT_SCREEN)
    object AddOrderScreen : Screen(ROUTER_ADD_ORDER_SCREEN)



    companion object {
        private const val ROUTER_AUTH = "auth_screen"
        private const val ROUTER_REGISTRATION = "registration_screen"

        private const val ROUTER_ABOUT = "about_screen"

        private const val ROUTER_MAIN_CLIENT_SCREEN = "main_client_screen"
        private const val ROUTER_ADD_ORDER_SCREEN = "add_order_screen"
    }
}