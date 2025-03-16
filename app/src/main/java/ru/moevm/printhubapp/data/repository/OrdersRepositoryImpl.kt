package ru.moevm.printhubapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import ru.moevm.printhubapp.data.mapper.toDto
import ru.moevm.printhubapp.data.mapper.toEntity
import ru.moevm.printhubapp.data.model.OrderDto
import ru.moevm.printhubapp.domain.entity.Order
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.OrdersRepository
import ru.moevm.printhubapp.utils.Constants.UID_STRING
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrdersRepositoryImpl(
    private val db: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) : OrdersRepository {

    private val orders = db.collection("orders")
    private val users = db.collection("users")

    private val userUid = sharedPreferences.getString(UID_STRING, "") ?: ""

    override suspend fun getClientOrders(): List<Order> {
        val ordersList = mutableListOf<Order>()

        try {
            val querySnapshot = orders.whereEqualTo("client_id", userUid).get().await()
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

    override fun createOrder(newOrder: Order, callback: (RequestResult<Unit>) -> Unit) {
        val updatedOrder = newOrder.copy(clientId = userUid)
        val orderDto = updatedOrder.toDto()
        orders.add(orderDto).addOnSuccessListener {
            callback(RequestResult.Success(Unit))
        }.addOnFailureListener { e ->
            callback(RequestResult.Error(RequestError.Server("Ошибка сохранения данных: ${e.message}")))
        }
    }
}