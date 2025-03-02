package ru.moevm.printhubapp.domain.entity.result

sealed class RequestError {
    object UserNotFound : RequestError()
    object UserAlreadyExists : RequestError()
    object InvalidPassword : RequestError()

    data class Server(val errorMessage: String) : RequestError()
}