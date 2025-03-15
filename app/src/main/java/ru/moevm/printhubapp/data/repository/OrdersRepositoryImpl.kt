package ru.moevm.printhubapp.data.repository

import android.util.Log
import ru.moevm.printhubapp.data.mapper.toEntity
import ru.moevm.printhubapp.data.model.OrderDto
import ru.moevm.printhubapp.domain.entity.Order
import ru.moevm.printhubapp.domain.repository.OrdersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrdersRepositoryImpl(
    private val db: FirebaseFirestore
) : OrdersRepository {

    private val orders = db.collection("orders")
    private val users = db.collection("users")

    override suspend fun getClientOrders(id: String): List<Order> {
        val ordersList = mutableListOf<Order>()

        try {
            val querySnapshot = orders.whereEqualTo("client_id", id).get().await()
            Log.d("OrderListScreen", "Found ${querySnapshot.documents.size} raw documents")

            for (document in querySnapshot.documents) {
                val orderDto = document.toObject(OrderDto::class.java)
                if (orderDto != null) {
                    val order = orderDto.toEntity()

                    if (order.companyId.isNotEmpty()) {
                        val companySnapshot = users.document(order.companyId).get().await()
                        order.nameCompany = companySnapshot.getString("nameCompany") ?: "Неизвестно"
                        order.address = companySnapshot.getString("address") ?: "Неизвестно"
                    } else {
                        Log.w("OrderListScreen", "Order has empty companyId: ${order.id}")
                        order.nameCompany = "Не указано"
                        order.address = "Адрес не указан"
                    }

                    ordersList.add(order)
                }
            }
        } catch (e: Exception) {
            Log.e("OrderListScreen", "Ошибка загрузки заказов: ${e.message}")
        }

        return ordersList
    }

    override suspend fun getOrder(id: String): Order {
        val orderDto = orders.document(id).get().await().toObject(OrderDto::class.java)
        val order = orderDto?.toEntity()
        if (order != null) {
            if (order.companyId.isNotEmpty()) {
                val companySnapshot = users.document(order.companyId).get().await()
                order.nameCompany = companySnapshot.getString("nameCompany") ?: "Неизвестно"
                order.address = companySnapshot.getString("address") ?: "Неизвестно"
            } else {
                Log.w("OrderListScreen", "Order has empty companyId: ${order.id}")
                order.nameCompany = "Не указано"
                order.address = "Адрес не указан"
            }
        }
        return order ?: throw Exception("Order not found")
    }
}