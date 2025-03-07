package ru.moevm.printhubapp.presentation.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.presentation.splash.SplashViewModel
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun ClientProfileScreen(
    navHostController: NavHostController,
    onAbout: () -> Unit,
    onLogout: () -> Unit
) {
    val splashViewModel: SplashViewModel = hiltViewModel()
    var showLogoutDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.orange10)
                    .padding(top = 32.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.client_profile_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.colors.black9
                    )
                    Icon(
                        modifier = Modifier.clickable { onAbout() },
                        painter = painterResource(R.drawable.info_ic),
                        contentDescription = null,
                        tint = AppTheme.colors.black9
                    )
                }
                HorizontalDivider(
                    color = AppTheme.colors.black9
                )
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = AppTheme.colors.black9
                )
                NavigationBar(
                    containerColor = AppTheme.colors.orange10,
                ) {
                    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                    val items = listOf(
                        NavigationItem.Home,
                        NavigationItem.Profile
                    )

                    items.forEach { item ->
                        val selected = navBackStackEntry?.destination?.hierarchy?.any {
                            it.route == item.screen.route
                        } ?: false
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if(!selected) {
                                    navHostController.navigate(item.screen.route)
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(item.titleResId),
                                    fontSize = 20.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = AppTheme.colors.black9,
                                unselectedTextColor = AppTheme.colors.gray7,
                                indicatorColor = Color.Transparent
                            ),
                            icon = {}
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(AppTheme.colors.gray1, RoundedCornerShape(16.dp))
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            InfoRow(
                titleId = R.string.name_title,
                param = "Клиент"
            )
            InfoRow(
                titleId = R.string.mail_title,
                param = "test_user@mail.ru"
            )
            InfoRow(
                titleId = R.string.logout,
                isNavigate = true,
                color = AppTheme.colors.red,
                onLogout = {
                    showLogoutDialog = true
                }
            )
        }
    }
    if (showLogoutDialog) {
        LogoutDialog(
            onLogout = {
                showLogoutDialog = false
                splashViewModel.logout()
                onLogout()
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}

@Composable
private fun InfoRow(
    titleId: Int,
    param: String = "",
    color: Color = AppTheme.colors.black9,
    isNavigate: Boolean = false,
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clickable { onLogout() }
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(stringResource(titleId), param),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
            if (isNavigate) {
                Icon(
                    painter = painterResource(R.drawable.right_arrow_ic),
                    contentDescription = null,
                    tint = AppTheme.colors.black9
                )
            }
        }
        HorizontalDivider(color = AppTheme.colors.black9)
    }
}

@Preview(showBackground = true)
@Composable
private fun ClientProfilePreview() {
    val navHostController = rememberNavController()
    ClientProfileScreen(navHostController, {}, {})
}