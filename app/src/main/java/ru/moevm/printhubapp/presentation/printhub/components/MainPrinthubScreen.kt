package ru.moevm.printhubapp.presentation.printhub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import ru.moevm.printhubapp.presentation.printhub.state.MainPrinthubState
import ru.moevm.printhubapp.presentation.printhub.viewmodels.MainPrinthubViewModel
import ru.moevm.printhubapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import ru.moevm.printhubapp.navigation.Screen

@Composable
fun MainPrinthubScreen(
    navHostController: NavHostController,
    onAbout: () -> Unit,
    onOrderDetails: (Any?) -> Unit,
) {
    var search by remember { mutableStateOf("") }
    val searchQuery = remember { MutableStateFlow("") }

    val viewModel: MainPrinthubViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    LaunchedEffect(searchQuery) {
        searchQuery
            .debounce(500)
            .collectLatest { query ->
                viewModel.searchOrders(query)
            }
    }

    LaunchedEffect(Unit) {
        navBackStackEntry?.destination?.route?.let { route ->
            if (route == Screen.MainPrinthubScreen.route) {
                viewModel.getPrinthubOrders()
            }
        }
    }

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
                    val items = listOf(
                        PrinthubNavigationItem.Home,
                        PrinthubNavigationItem.Profile
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                value = search,
                onValueChange = {
                    search = it
                    searchQuery.value = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_choose_order_number),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppTheme.colors.gray7
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppTheme.colors.gray1,
                    focusedContainerColor = AppTheme.colors.gray1,
                    disabledContainerColor = AppTheme.colors.gray1,
                    disabledBorderColor = AppTheme.colors.gray7,
                    unfocusedBorderColor = AppTheme.colors.gray7,
                    focusedBorderColor = AppTheme.colors.gray7
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    is MainPrinthubState.Loading -> {
                        CircularProgressIndicator(
                            color = AppTheme.colors.orange10
                        )
                    }
                    is MainPrinthubState.Success -> {
                        val orders = (state as MainPrinthubState.Success).orders
                        if (orders.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_order),
                                fontSize = 18.sp,
                                color = AppTheme.colors.gray7
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(orders.size) { index ->
                                    val order = orders[index]
                                    OrderPrinthubCard(
                                        order = order,
                                        openDetails = { onOrderDetails(order.id) }
                                    )
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPrinthubScreenPreview() {
    val navHostController = rememberNavController()
    MainPrinthubScreen(navHostController, {}, {})
}