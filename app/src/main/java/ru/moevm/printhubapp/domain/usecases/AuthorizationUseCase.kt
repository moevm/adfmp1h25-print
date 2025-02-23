package ru.moevm.printhubapp.domain.usecases

import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.repository.AuthRepository

class AuthorizationUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(user: Auth) = authRepository.authorization(user)
}
