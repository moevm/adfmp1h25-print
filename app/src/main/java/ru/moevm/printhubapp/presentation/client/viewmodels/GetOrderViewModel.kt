package ru.moevm.printhubapp.presentation.client.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.usecases.GetClientOrdersUseCase
import ru.moevm.printhubapp.domain.usecases.GetOrderUseCase
import ru.moevm.printhubapp.presentation.client.state.GetClientOrdersState
import ru.moevm.printhubapp.presentation.client.state.GetOrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetOrderViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<GetOrderState>(GetOrderState.Init)
    val state: StateFlow<GetOrderState> get() = _state.asStateFlow()

    fun getOrder(orderId: String) {
        viewModelScope.launch {
            _state.value = GetOrderState.Loading

            try {
                val order = getOrderUseCase(orderId)
                _state.value = GetOrderState.Success(order)
            } catch (e: Exception) {
                _state.value = GetOrderState.Error
            }
        }
    }
}