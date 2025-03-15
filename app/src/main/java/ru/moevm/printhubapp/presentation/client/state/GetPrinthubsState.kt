package ru.moevm.printhubapp.presentation.client.state

import ru.moevm.printhubapp.domain.entity.User

sealed class GetPrinthubsState {
    object Init : GetPrinthubsState()
    object Loading : GetPrinthubsState()
    class Success(val printhubs: List<User>) : GetPrinthubsState()
    object Error : GetPrinthubsState()
}