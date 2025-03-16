package ru.moevm.printhubapp.presentation.client.components

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.navigation.Screen
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

    LaunchedEffect(searchQuery) {
        searchQuery
            .debounce(500)
            .collectLatest { query ->
                viewModel.searchOrders(query)
            }
    }

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

    var priceFilterApplied by rememberSaveable { mutableStateOf(false) }
    var minPrice by rememberSaveable { mutableStateOf("0") }
    var maxPrice by rememberSaveable { mutableStateOf("10000") }

    var formatFilterApplied by rememberSaveable { mutableStateOf(false) }
    var selectedFormats by rememberSaveable { mutableStateOf(setOf<String>()) }

    var selectedStatuses by rememberSaveable { mutableStateOf(setOf<String>()) }

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
                    Log.d("orders", (state as MainClientState.Success).orders.toString())
                    val orders = (state as MainClientState.Success).orders
                    if (orders.isEmpty() && selectedStatuses.isEmpty() && !priceFilterApplied && selectedFormats.isEmpty()) {
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
                                                AppTheme.colors.orange10,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            painter = painterResource(R.drawable.sort_ic),
                                            contentDescription = null,
                                            tint = AppTheme.colors.black9
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
                                                if (priceFilterApplied) AppTheme.colors.orange10 else AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Цена",
                                            color = if (priceFilterApplied) AppTheme.colors.black9 else Color.White
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
                                                if (formatFilterApplied) AppTheme.colors.orange10 else AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = "Формат печати",
                                            color = if (formatFilterApplied) AppTheme.colors.black9 else Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                val statuses = listOf(
                                    "Создан" to "Создан",
                                    "В работе" to "В работе",
                                    "Ожидает получения" to "Готов к получению",
                                    "Выполнен" to "Получен",
                                    "Отказ" to "Отказ"
                                )
                                items(statuses.size) { index ->
                                    val (statusCode, displayText) = statuses[index]
                                    val isSelected = selectedStatuses.contains(statusCode)

                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                selectedStatuses = if (isSelected) {
                                                    selectedStatuses - statusCode
                                                } else {
                                                    selectedStatuses + statusCode
                                                }
                                                viewModel.filterByStatuses(selectedStatuses)
                                            }
                                            .background(
                                                if (isSelected) AppTheme.colors.orange10 else AppTheme.colors.gray7,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = displayText,
                                            color = if (isSelected) AppTheme.colors.black9 else Color.White
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
                                items(orders.size) { index ->
                                    val order = orders[index]
                                    OrderCard(
                                        order = order,
                                        showOrderDetails = { orderId ->
                                            showOrderDetails(orderId)
                                        }
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

    if (openFormatPrinterFilterBottomSheet) {
        FormatPrintBottomSheet(
            sheetState = filterFormatPrinterBottomSheetState,
            scope = scopeFormatPrinter,
            openFormatPrinterFilterBottomSheet = { openFormatPrinterFilterBottomSheet = false },
            selectedFormats = selectedFormats,
            onFormatSelectionChanged = { newFormats, isActive ->
                selectedFormats = newFormats
                formatFilterApplied = isActive
            }
        )
    }

    if (openAmountFilterBottomSheet) {
        AmountFilterBottomSheet(
            sheetState = filterAmountBottomSheetState,
            scope = scopeAmount,
            openAmountFilterBottomSheet = { openAmountFilterBottomSheet = false },
            minPrice = minPrice,
            maxPrice = maxPrice,
            onPriceRangeChanged = { min, max, isActive ->
                minPrice = min
                maxPrice = max
                priceFilterApplied = isActive
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormatPrintBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    openFormatPrinterFilterBottomSheet: () -> Unit,
    selectedFormats: Set<String>,
    onFormatSelectionChanged: (Set<String>, Boolean) -> Unit
) {
    val filterOptions = listOf(
        "A1",
        "A2",
        "A3",
        "A4",
        "A5",
        "A6"
    )
    val checkedStates = remember {
        mutableStateListOf<Boolean>().apply {
            addAll(filterOptions.map { selectedFormats.contains(it) })
        }
    }
    val viewModel: MainClientViewModel = hiltViewModel()

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
                    val newSelectedFormats = filterOptions
                        .filterIndexed { index, _ -> checkedStates[index] }
                        .toSet()

                    onFormatSelectionChanged(newSelectedFormats, newSelectedFormats.isNotEmpty())
                    viewModel.filterByFormats(newSelectedFormats)

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
    openAmountFilterBottomSheet: () -> Unit,
    minPrice: String,
    maxPrice: String,
    onPriceRangeChanged: (String, String, Boolean) -> Unit
) {
    var inAmount by remember { mutableStateOf(minPrice) }
    var outAmount by remember { mutableStateOf(maxPrice) }
    val viewModel: MainClientViewModel = hiltViewModel()
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
                    val min = inAmount.toIntOrNull() ?: 0
                    val max = outAmount.toIntOrNull() ?: 10000
                    val isFilterActive = (min != 0 || max != 10000)

                    onPriceRangeChanged(inAmount, outAmount, isFilterActive)
                    viewModel.filterByPriceRange(min, max)

                    viewModel.filterByPriceRange(min, max)
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
    val viewModel: MainClientViewModel = hiltViewModel()
    val currentSortOption = remember { mutableStateOf(viewModel.getCurrentSortOption()) }

    val filterOptions = listOf(
        "По дате создания: сначала новые" to MainClientViewModel.SortOption.CREATED_NEWEST_FIRST,
        "По дате создания: сначала старые" to MainClientViewModel.SortOption.CREATED_OLDEST_FIRST,
        "По дате обновления: сначала новые" to MainClientViewModel.SortOption.UPDATED_NEWEST_FIRST,
        "По дате обновления: сначала старые" to MainClientViewModel.SortOption.UPDATED_OLDEST_FIRST
    )

    val initialSelectedText = filterOptions.first { it.second == currentSortOption.value }.first
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(initialSelectedText) }

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
                filterOptions.forEach { (text, _) ->
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
                    val sortOption = filterOptions.first { it.first == selectedOption }.second
                    viewModel.sortOrders(sortOption)

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