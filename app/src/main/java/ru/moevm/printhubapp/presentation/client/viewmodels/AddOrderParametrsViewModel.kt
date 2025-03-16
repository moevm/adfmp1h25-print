package ru.moevm.printhubapp.presentation.client.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.entity.Order
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.usecases.CreateOrderUseCase
import ru.moevm.printhubapp.presentation.client.state.AddOrderParametersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrderParametersViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow<AddOrderParametersState>(AddOrderParametersState.Init)
    val state: StateFlow<AddOrderParametersState> get() = _state.asStateFlow()

    fun createOrder(order: Order, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.value = AddOrderParametersState.Loading
            createOrderUseCase(order) { result ->
                when (result) {
                    is RequestResult.Success -> {
                        _state.value = AddOrderParametersState.Success
                        onSuccess()
                    }
                    is RequestResult.Error -> {
                        when (result.error) {
                            is RequestError.Server -> _state.value = AddOrderParametersState.ServerError
                            else -> _state.value = AddOrderParametersState.ServerError
                        }
                    }
                }
            }
        }
    }
}