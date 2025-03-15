package ru.moevm.printhubapp.domain.repository

import ru.moevm.printhubapp.domain.entity.Order

interface OrdersRepository {
    suspend fun getClientOrders() : List<Order>
    suspend fun getOrder(id: String) : Order
}