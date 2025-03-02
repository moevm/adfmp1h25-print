package ru.moevm.printhubapp.presentation.auth.viewmodels

import androidx.lifecycle.ViewModel
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.usecases.AuthorizationUseCase
import ru.moevm.printhubapp.domain.usecases.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val authorizationUseCase: AuthorizationUseCase
) : ViewModel() {

    fun registration(newUser: Registration) {
        registrationUseCase(newUser)
    }
    fun authorization(user: Auth) {
        authorizationUseCase(user)
    }
}