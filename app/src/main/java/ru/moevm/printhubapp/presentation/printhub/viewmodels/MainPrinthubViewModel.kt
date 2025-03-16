package ru.moevm.printhubapp.presentation.printhub.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.moevm.printhubapp.domain.entity.Order
import ru.moevm.printhubapp.domain.usecases.GetPrinthubOrdersUseCase
import ru.moevm.printhubapp.presentation.printhub.state.MainPrinthubState
import javax.inject.Inject

@HiltViewModel
class MainPrinthubViewModel @Inject constructor(
    private val getPrinthubOrdersUseCase: GetPrinthubOrdersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainPrinthubState>(MainPrinthubState.Init)
    val state: StateFlow<MainPrinthubState> get() = _state.asStateFlow()

    private var allOrders: List<Order> = emptyList()

    init {
        getPrinthubOrders()
    }

    fun getPrinthubOrders() {
        viewModelScope.launch {
            _state.value = MainPrinthubState.Loading
            try {
                val orders = getPrinthubOrdersUseCase()
                allOrders = orders.sortedByDescending { it.updatedAt }
                Log.d("OrderViewModel", "Found ${orders.size} orders")
                _state.value = MainPrinthubState.Success(allOrders)
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error getting orders: ${e.message}", e)
                _state.value = MainPrinthubState.Error
            }
        }
    }

    fun searchOrders(query: String) {
        viewModelScope.launch {
            try {
                val filteredOrders = allOrders.filter { order ->
                    (order.format.startsWith("A", ignoreCase = true) && order.format.contains(query, ignoreCase = true)) ||
                            order.number.toString().contains(query) ||
                            order.paperCount.toString().contains(query) ||
                            order.totalPrice.toString().contains(query)
                }
                _state.value = MainPrinthubState.Success(filteredOrders)
            } catch (e: Exception) {
                _state.value = MainPrinthubState.Error
            }
        }
    }
}