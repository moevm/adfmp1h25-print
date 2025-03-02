package ru.moevm.printhubapp.data.model

import ru.moevm.printhubapp.domain.entity.Role

data class User(
    val mail: String,
    val password: String,
    val role: Role,
    val nameCompany: String = "",
    val address: String = ""
)