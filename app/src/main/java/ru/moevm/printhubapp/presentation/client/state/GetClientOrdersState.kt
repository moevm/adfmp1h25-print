package ru.moevm.printhubapp.presentation.client.state

import ru.moevm.printhubapp.domain.entity.Order

sealed class GetClientOrdersState {
    object Init : GetClientOrdersState()
    object Loading : GetClientOrdersState()
    data class Success(val orders: List<Order>) : GetClientOrdersState()
    object Error: GetClientOrdersState()
}