package ru.moevm.printhubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.moevm.printhubapp.navigation.AppNavGraph
import ru.moevm.printhubapp.ui.theme.AppTheme
import ru.moevm.printhubapp.ui.theme.PrintHubAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrintHubAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.materialColors.background
                ) {
                    val navHostController = rememberNavController()
                    AppNavGraph(navHostController)
                }
            }
        }
    }
}