package ru.moevm.printhubapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.moevm.printhubapp.R

@Composable
fun SplashScreen(
    onNavigateToAuth: () -> Unit,
    onNavigateToClientMain: () -> Unit,
    onNavigateToPrintHubMain: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            SplashState.NavigateToAuth -> onNavigateToAuth()
            SplashState.NavigateToClientMain -> onNavigateToClientMain()
            SplashState.NavigateToPrintHubMain -> onNavigateToPrintHubMain()
            SplashState.Loading -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_ic),
            contentDescription = null
        )
    }
} 