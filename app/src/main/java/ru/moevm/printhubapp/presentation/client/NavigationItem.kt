package ru.moevm.printhubapp.presentation.client

import ru.moevm.printhubapp.R

sealed class NavigationItem(
    val titleResId: Int
) {

    object Home : NavigationItem (
        titleResId =  R.string.navigation_item_home
    )

    object Profile : NavigationItem (
        titleResId =  R.string.navigation_item_profile
    )
}