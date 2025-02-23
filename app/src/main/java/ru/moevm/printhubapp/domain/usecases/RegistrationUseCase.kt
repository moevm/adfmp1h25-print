package ru.moevm.printhubapp.domain.usecases

import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.repository.AuthRepository

class RegistrationUseCase (private val authRepository: AuthRepository) {
    operator fun invoke(newUser: Registration) = authRepository.registration(newUser)
}
