package ru.moevm.printhubapp.data.repository

import androidx.lifecycle.MutableLiveData
import ru.moevm.printhubapp.data.model.User
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    private val _users = MutableLiveData(
        listOf(
            User(
                mail = "test_client@mail.ru",
                password = "1",
                role = Role.CLIENT
            ),
            User(
                mail = "test_printhub@mail.ru",
                password = "1",
                role = Role.PRINTHUB,
                address = "Санкт-Петербург, ул. Попова 5Б",
                nameCompany = "Хамелеон"
            )
        )
    )

    override fun authorization(user: Auth): RequestResult<Unit> {
        val existingUser = _users.value?.find { it.mail == user.mail }
        if (existingUser != null) {
            if (existingUser.password == user.password) {
                return RequestResult.Success(Unit)
            } else {
                return RequestResult.Error(RequestError.InvalidPassword)
            }
        } else {
            return RequestResult.Error(RequestError.UserNotFount)
        }
    }

    override fun registration(newUser: Registration): RequestResult<Unit> {
        val userList = _users.value?.toMutableList() ?: mutableListOf()

        if (userList.any { it.mail == newUser.mail }) {
            return RequestResult.Error(RequestError.UserAlreadyExists)
        }

        val createdUser = User(
            mail = newUser.mail,
            password = newUser.password,
            role = newUser.role,
            nameCompany = newUser.nameCompany,
            address = newUser.address
        )

        userList.add(createdUser)
        _users.value = userList

        return RequestResult.Success(Unit)
    }
}