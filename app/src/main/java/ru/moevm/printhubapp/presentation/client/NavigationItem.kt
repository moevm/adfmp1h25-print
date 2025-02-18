package ru.moevm.printhubapp.presentation.client

sealed class NavigationItem(
    val titleResId: String
) {

    object Home : NavigationItem (
        titleResId =  "Главная"
    )

    object Profile : NavigationItem (
        titleResId =  "Профиль"
    )
}