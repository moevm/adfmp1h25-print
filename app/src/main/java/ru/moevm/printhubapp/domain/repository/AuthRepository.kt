package ru.moevm.printhubapp.domain.repository

import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.result.RequestResult

interface AuthRepository {
    fun authorization(user: Auth) : RequestResult<Unit>
    fun registration(newUser: Registration) : RequestResult<Unit>
}
