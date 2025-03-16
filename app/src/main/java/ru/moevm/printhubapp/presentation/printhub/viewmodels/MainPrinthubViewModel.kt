package ru.moevm.printhubapp.presentation.printhub.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.usecases.GetClientOrdersUseCase
import ru.moevm.printhubapp.domain.usecases.GetPrinthubOrdersUseCase
import ru.moevm.printhubapp.presentation.client.state.MainClientState
import ru.moevm.printhubapp.presentation.printhub.state.MainPrinthubState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPrinthubViewModel @Inject constructor(
    private val getPrinthubOrdersUseCase: GetPrinthubOrdersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainPrinthubState>(MainPrinthubState.Init)
    val state: StateFlow<MainPrinthubState> get() = _state.asStateFlow()

    init {
        getPrinthubOrders()
    }

    fun getPrinthubOrders() {
        viewModelScope.launch {
            _state.value = MainPrinthubState.Loading
            try {
                val orders = getPrinthubOrdersUseCase()
                Log.d("OrderViewModel", "Found ${orders.size} orders")
                _state.value = MainPrinthubState.Success(orders)
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error getting orders: ${e.message}", e)
                _state.value = MainPrinthubState.Error
            }
        }
    }
}