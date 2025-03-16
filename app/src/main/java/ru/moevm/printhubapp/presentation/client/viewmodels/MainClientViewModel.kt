package ru.moevm.printhubapp.presentation.client.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.usecases.GetClientOrdersUseCase
import ru.moevm.printhubapp.presentation.client.state.MainClientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.moevm.printhubapp.domain.entity.Order
import javax.inject.Inject

@HiltViewModel
class MainClientViewModel @Inject constructor(
    private val getClientOrdersUseCase: GetClientOrdersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainClientState>(MainClientState.Init)
    val state: StateFlow<MainClientState> get() = _state.asStateFlow()

    private var initialLoadComplete = false

    private var allOrders: List<Order> = emptyList()
    private var currentStatusFilters: Set<String> = emptySet()
    private var currentMinPrice: Int? = null
    private var currentMaxPrice: Int? = null
    private var currentFormatFilters: Set<String> = emptySet()

    init {
        getClientOrders()
    }

    enum class SortOption {
        CREATED_NEWEST_FIRST,
        CREATED_OLDEST_FIRST,
        UPDATED_NEWEST_FIRST,
        UPDATED_OLDEST_FIRST
    }

    private var currentSortOption = SortOption.UPDATED_NEWEST_FIRST

    fun sortOrders(sortOption: SortOption) {
        viewModelScope.launch {
            currentSortOption = sortOption
            applyAllFilters()
        }
    }

    private fun getClientOrders() {
        viewModelScope.launch {
            _state.value = MainClientState.Loading
            try {
                val orders = getClientOrdersUseCase()
                allOrders = orders.sortedByDescending { it.updatedAt }

                if (!initialLoadComplete) {
                    _state.value = MainClientState.Success(allOrders)
                    initialLoadComplete = true
                } else {
                    Log.d("FilterDebug", "Returning to screen - preserving filters")
                }
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error getting orders: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }

    fun searchOrders(query: String) {
        viewModelScope.launch {
            try {
                if (query.isEmpty()) {
                    _state.value = MainClientState.Success(allOrders)
                    return@launch
                }

                val filteredOrders = allOrders.filter { order ->
                    order.number.toString().contains(query)
                }

                _state.value = MainClientState.Success(filteredOrders)
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error searching orders: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }

    fun filterByStatuses(statuses: Set<String>) {
        viewModelScope.launch {
            try {
                currentStatusFilters = statuses

                if (currentStatusFilters.isEmpty()) {
                    _state.value = MainClientState.Success(allOrders)
                    return@launch
                }
                applyAllFilters()
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error filtering orders: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }

    fun filterByPriceRange(min: Int?, max: Int?) {
        viewModelScope.launch {
            try {
                currentMinPrice = min
                currentMaxPrice = max
                applyAllFilters()
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error filtering by price: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }

    fun filterByFormats(formats: Set<String>) {
        viewModelScope.launch {
            try {
                currentFormatFilters = formats
                applyAllFilters()
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error filtering by format: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }

    fun getCurrentSortOption(): SortOption {
        return currentSortOption
    }

    private fun applyAllFilters() {
        viewModelScope.launch {
            var filteredOrders = allOrders
            if (currentStatusFilters.isNotEmpty()) {
                filteredOrders = filteredOrders.filter { order ->
                    currentStatusFilters.contains(order.status)
                }
            }

            if (currentMinPrice != null || currentMaxPrice != null) {
                filteredOrders = filteredOrders.filter { order ->
                    val minMatch = currentMinPrice?.let { order.totalPrice >= it } ?: true
                    val maxMatch = currentMaxPrice?.let { order.totalPrice <= it } ?: true
                    minMatch && maxMatch
                }
            }

            if (currentFormatFilters.isNotEmpty()) {
                filteredOrders = filteredOrders.filter { order ->
                    currentFormatFilters.contains(order.format)
                }
            }

            filteredOrders = when (currentSortOption) {
                SortOption.CREATED_NEWEST_FIRST -> filteredOrders.sortedByDescending { it.createdAt }
                SortOption.CREATED_OLDEST_FIRST -> filteredOrders.sortedBy { it.createdAt }
                SortOption.UPDATED_NEWEST_FIRST -> filteredOrders.sortedByDescending { it.updatedAt }
                SortOption.UPDATED_OLDEST_FIRST -> filteredOrders.sortedBy { it.updatedAt }
            }
            allOrders = filteredOrders
            _state.value = MainClientState.Success(allOrders)
        }
    }
}
