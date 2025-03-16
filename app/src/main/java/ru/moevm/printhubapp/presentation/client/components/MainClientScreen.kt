package ru.moevm.printhubapp.presentation.client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.presentation.client.state.MainClientState
import ru.moevm.printhubapp.presentation.client.viewmodels.MainClientViewModel
import ru.moevm.printhubapp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainClientScreen(
    navHostController: NavHostController,
    onAbout: () -> Unit,
    addOrder: () -> Unit,
    showOrderDetails: (Any?) -> Unit
) {
    val viewModel: MainClientViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    var search by remember { mutableStateOf("") }
    val searchQuery = remember { MutableStateFlow("") }

    var openFilterBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val filterBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    var openAmountFilterBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipAmountPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val scopeAmount = rememberCoroutineScope()
    val filterAmountBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipAmountPartiallyExpanded)

    var openFormatPrinterFilterBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipFormatPrinterPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val scopeFormatPrinter = rememberCoroutineScope()
    val filterFormatPrinterBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipFormatPrinterPartiallyExpanded)
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
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is MainClientState.Success -> {
                    val orders = (state as MainClientState.Success).orders
                    if (orders.isEmpty()) {
                        Text(
                            text = "У вас пока нет заказов",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = AppTheme.colors.gray7
                        )
                    } else {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
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
                            LazyRow(
                                contentPadding = PaddingValues(
                                    top = 16.dp,
                                    bottom = 16.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                openFilterBottomSheet = true
                                            }
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            painter = painterResource(R.drawable.sort_ic),
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                openAmountFilterBottomSheet = true
                                            }
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Цена",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                openFormatPrinterFilterBottomSheet = true
                                            }
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Формат печати",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Создан",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "В работе",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Ожидает получения",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Получен",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Отказ",
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }

                            }
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(
                                    bottom = 16.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(orders.sortedByDescending { it.updatedAt }.size) { index ->
                                    val order = orders.sortedByDescending { it.updatedAt }[index]
                                    OrderCard(
                                        order = order,
                                        showOrderDetails = { showOrderDetails(order.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                is MainClientState.Loading -> {
                    CircularProgressIndicator(
                        color = AppTheme.colors.orange10,
                        modifier = Modifier.align(Alignment.Center)
                    )
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
    if (openFilterBottomSheet) {
        FilterBottomSheet(
            filterBottomSheetState,
            scope
        ) {
            openFilterBottomSheet = false
        }
    }

    if (openAmountFilterBottomSheet) {
        AmountFilterBottomSheet(
            filterAmountBottomSheetState,
            scopeAmount
        ) {
            openAmountFilterBottomSheet = false
        }
    }

    if (openFormatPrinterFilterBottomSheet) {
        FormatPrintBottomSheet(
            filterFormatPrinterBottomSheetState,
            scopeFormatPrinter
        ) {
            openFormatPrinterFilterBottomSheet = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormatPrintBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    openFormatPrinterFilterBottomSheet: () -> Unit
) {
    val checkedStates = remember { mutableStateListOf(false, false, false, false, false, false) }
    val filterOptions = listOf(
        "A1",
        "A2",
        "A3",
        "A4",
        "A5",
        "A6"
    )
    ModalBottomSheet(
        onDismissRequest = { openFormatPrinterFilterBottomSheet() },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            filterOptions.forEachIndexed { index, text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = checkedStates[index],
                            onValueChange = { checkedStates[index] = !checkedStates[index] },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedStates[index],
                        onCheckedChange = null,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppTheme.colors.orange10,
                            uncheckedColor = AppTheme.colors.gray7
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = text,
                        fontSize = 18.sp,
                        color = AppTheme.colors.gray7
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openFormatPrinterFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.orange10,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Применить",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openFormatPrinterFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.gray2,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Закрыть",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AmountFilterBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    openAmountFilterBottomSheet: () -> Unit
) {
    var inAmount by remember { mutableStateOf("0") }
    var outAmount by remember { mutableStateOf("10000") }
    ModalBottomSheet(
        onDismissRequest = { openAmountFilterBottomSheet() },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = inAmount,
                    onValueChange = {
                        inAmount = it
                    },
                    label = {
                        Text(
                            text = "От",
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
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = outAmount,
                    onValueChange = {
                        outAmount = it
                    },
                    label = {
                        Text(
                            text = "До",
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
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openAmountFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.orange10,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Применить",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openAmountFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.gray2,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Закрыть",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    openFilterBottomSheet: () -> Unit
) {
    val filterOptions = listOf(
        "По дате создания: сначала новые",
        "По дате создания: сначала старые",
        "По дате обновления: сначала новые",
        "По дате обновления: сначала старые"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(filterOptions[0]) }
    ModalBottomSheet(
        onDismissRequest = { openFilterBottomSheet() },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.selectableGroup()
            ) {
                filterOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null,
                            colors = RadioButtonColors(
                                selectedColor = AppTheme.colors.orange10,
                                unselectedColor = AppTheme.colors.gray7,
                                disabledUnselectedColor = AppTheme.colors.gray7,
                                disabledSelectedColor = AppTheme.colors.gray7
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = text,
                            fontSize = 18.sp,
                            color = AppTheme.colors.gray7
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.orange10,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Применить",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                openFilterBottomSheet()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.gray2,
                    contentColor = AppTheme.colors.black9
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Закрыть",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
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