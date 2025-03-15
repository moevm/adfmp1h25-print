package ru.moevm.printhubapp.presentation.client.state

import ru.moevm.printhubapp.domain.entity.Order

sealed class GetOrderState {
    object Init : GetOrderState()
    object Loading : GetOrderState()
    data class Success(val order: Order) : GetOrderState()
    object Error: GetOrderState()
}