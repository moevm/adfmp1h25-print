package ru.moevm.printhubapp.presentation.client.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import ru.moevm.printhubapp.presentation.client.state.GetClientOrdersState
import ru.moevm.printhubapp.presentation.client.viewmodels.GetClientOrdersViewModel
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun MainClientScreen(
    navHostController: NavHostController,
    onAbout: () -> Unit,
    addOrder: () -> Unit,
    showOrderDetails: (Any?) -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_uid", Context.MODE_PRIVATE)

    val viewModel: GetClientOrdersViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val uid = sharedPreferences.getString("uid_current_user", "") ?: ""
    Log.d("MainClient", "Getting orders for client: $uid")
    viewModel.getClientOrders(uid)

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
                        text = stringResource(R.string.main_title),
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
                                if (!selected) {
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
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ){
            when (state)
            {
                is GetClientOrdersState.Success -> {
                    val orders = (state as GetClientOrdersState.Success).orders
                    if (orders.isEmpty()) {
                        Text(
                            text = "У вас пока нет заказов",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(orders.size) { index ->
                                OrderCard(order = orders[index], showOrderDetails = { showOrderDetails(orders[index].id) })
                            }
                        }
                    }
                }
                else -> {
                }
            }
            FloatingActionButton(
                onClick = { addOrder() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = CircleShape,
                containerColor = AppTheme.colors.orange10,
                contentColor = AppTheme.colors.black9
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_ic),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainClientScreenPreview() {
    val navHostController = rememberNavController()
    MainClientScreen(navHostController, {}, {}, {})
}