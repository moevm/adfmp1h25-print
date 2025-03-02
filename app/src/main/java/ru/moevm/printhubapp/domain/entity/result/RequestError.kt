package ru.moevm.printhubapp.domain.entity.result

sealed class RequestError<out T> {
    object UserNotFount : RequestError<Nothing>()
    object UserAlreadyExists : RequestError<Nothing>()
    object InvalidPassword : RequestError<Nothing>()
}
