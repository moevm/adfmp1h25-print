package ru.moevm.printhubapp.data.mapper

import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.domain.entity.User

fun UserDto.toEntity(): User =
    User(
        id = this.id,
        mail = this.mail,
        password = this.password,
        role = this.role.toRole(),
        address = this.address,
        nameCompany = this.nameCompany
    )

fun String.toRole(): Role =
    when (this) {
        "CLIENT" -> Role.CLIENT
        "PRINTHUB" -> Role.PRINTHUB
        else -> Role.CLIENT
    }

