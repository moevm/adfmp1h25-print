package ru.moevm.printhubapp.data.repository

import ru.moevm.printhubapp.data.mapper.toEntity
import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.User
import ru.moevm.printhubapp.domain.repository.PrinthubsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PrinthubsRepositoryImpl (
    private val db: FirebaseFirestore
) : PrinthubsRepository {

    private val users = db.collection("users")

    override suspend fun getPrinthubs(): List<User> {
        val querySnapshot = users.whereEqualTo("role", "printhub").get().await()
        val printhubs = querySnapshot.documents.mapNotNull { it.toObject(UserDto::class.java)?.toEntity() }
        if (printhubs.isNotEmpty()) {
            return printhubs
        } else {
            throw Exception("Printhub not found")
        }
    }
}