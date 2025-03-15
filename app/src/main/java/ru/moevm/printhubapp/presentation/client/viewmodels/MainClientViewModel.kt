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
import javax.inject.Inject

@HiltViewModel
class MainClientViewModel @Inject constructor(
    private val getClientOrdersUseCase: GetClientOrdersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainClientState>(MainClientState.Init)
    val state: StateFlow<MainClientState> get() = _state.asStateFlow()

    init {
        getClientOrders()
    }

    fun getClientOrders() {
        viewModelScope.launch {
            _state.value = MainClientState.Loading
            try {
                val orders = getClientOrdersUseCase()
                Log.d("OrderViewModel", "Found ${orders.size} orders")
                _state.value = MainClientState.Success(orders)
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error getting orders: ${e.message}", e)
                _state.value = MainClientState.Error
            }
        }
    }
}
