package ru.moevm.printhubapp.domain.entity

import ru.moevm.printhubapp.presentation.auth.Role

data class Registration(
    val mail: String,
    val password: String,
    val role: Role,
    val nameCompany: String = "",
    val address: String = ""
)

