package ru.moevm.printhubapp.presentation.auth.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
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
        val validationError = validateCredentials(user)
        if (validationError != null) {
            Log.e("AUTH", validationError) // Логируем ошибку
            return
        }

        authorizationUseCase(user) { result ->
            when (result) {
                is RequestResult.Success -> {
                    Log.d("AUTH", "Авторизация успешна!")
                }
                is RequestResult.Error -> {
                    when (result.error) {
                        is RequestError.UserNotFound -> Log.e("AUTH", "Пользователь не найден")
                        is RequestError.InvalidPassword -> Log.e("AUTH", "Неверный пароль")
                        is RequestError.Server -> Log.e("AUTH", "Ошибка сервера: ${result.error.errorMessage}")
                        else -> Log.e("AUTH", "Неизвестная ошибка")
                    }
                }
            }
        }
    }

    private fun validateCredentials(user: Auth): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(user.mail).matches()) {
            return "Некорректный формат почты"
        }
        if (user.password.length < 6) {
            return "Пароль должен содержать не менее 6 символов"
        }
        return null
    }
}