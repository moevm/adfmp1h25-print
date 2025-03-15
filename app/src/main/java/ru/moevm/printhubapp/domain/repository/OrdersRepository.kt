package ru.moevm.printhubapp.domain.repository

import ru.moevm.printhubapp.domain.entity.Order

interface OrdersRepository {
    suspend fun getClientOrders(id: String) : List<Order>
    suspend fun getOrder(id: String) : Order
}