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
         val validationError = validateCredentials(newUser.mail, newUser.password)
        if (validationError != null) {
            Log.e("AUTH", validationError)
            return
        }

        registrationUseCase(newUser) { result ->
            when (result) {
                is RequestResult.Success -> {
                    Log.d("AUTH", "Регистрация успешна!")
                }
                is RequestResult.Error -> {
                    when (result.error) {
                        is RequestError.UserAlreadyExists -> Log.e("AUTH", "Пользователь уже зарегистрирован")
                        is RequestError.InvalidEmail -> Log.e("AUTH", "Некорректный email")
                        is RequestError.WeakPassword -> Log.e("AUTH", "Пароль слишком слабый")
                        is RequestError.InvalidCredentials -> Log.e("AUTH", "Ошибка: ${result.error.reason}")
                        is RequestError.Server -> Log.e("AUTH", "Ошибка сервера: ${result.error.errorMessage}")
                        else -> Log.e("AUTH", "Неизвестная ошибка")
                    }
                }
            }
        }
    }
    fun authorization(user: Auth) {
        val validationError = validateCredentials(user.mail, user.password)
        if (validationError != null) {
            Log.e("AUTH", validationError)
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

    private fun validateCredentials(mail: String, password: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            return "Некорректный формат почты"
        }
        if (password.length < 6) {
            return "Пароль должен содержать не менее 6 символов"
        }
        return null
    }
}