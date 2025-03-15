package ru.moevm.printhubapp.presentation.client.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.usecases.GetClientOrdersUseCase
import ru.moevm.printhubapp.presentation.client.state.GetClientOrdersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetClientOrdersViewModel @Inject constructor(
    private val getClientOrdersUseCase: GetClientOrdersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<GetClientOrdersState>(GetClientOrdersState.Init)
    val state: StateFlow<GetClientOrdersState> get() = _state.asStateFlow()

    fun getClientOrders(clientId: String) {
        viewModelScope.launch {
            _state.value = GetClientOrdersState.Loading

            try {
                Log.d("OrderViewModel", "Getting orders for client: $clientId")
                val orders = getClientOrdersUseCase(clientId)
                Log.d("OrderViewModel", "Found ${orders.size} orders")
                _state.value = GetClientOrdersState.Success(orders)
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error getting orders: ${e.message}", e)
                _state.value = GetClientOrdersState.Error
            }
        }
    }
}
