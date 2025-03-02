package ru.moevm.printhubapp.data.model

data class UserDto(
    val mail: String = "",
    val password: String = "",
    val role: String = "",
    val nameCompany: String = "",
    val address: String = ""
)