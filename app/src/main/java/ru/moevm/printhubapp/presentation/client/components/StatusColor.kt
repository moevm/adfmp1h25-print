package ru.moevm.printhubapp.presentation.client.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "В работе" -> AppTheme.colors.yellow
        "Готов к получению" -> AppTheme.colors.green8
        "Отказ" -> AppTheme.colors.red
        else -> AppTheme.colors.gray7
    }
}