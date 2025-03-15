package ru.moevm.printhubapp.domain.repository

import ru.moevm.printhubapp.domain.entity.User

interface PrinthubsRepository {
    suspend fun getPrinthubs() : List<User>
}