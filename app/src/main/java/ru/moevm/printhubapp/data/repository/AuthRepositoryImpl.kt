package ru.moevm.printhubapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    private val users = db.collection("users")
    private val _users = MutableLiveData(
        listOf(
            UserDto(
                mail = "test_client@mail.ru",
                password = "1",
                role = Role.CLIENT
            ),
            UserDto(
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

        val user = mapOf(
            "mail" to newUser.mail,
            "password" to newUser.password,
            "role" to newUser.role,
            "address" to newUser.address,
            "nameCompany" to newUser.nameCompany
        )

        auth.createUserWithEmailAndPassword(newUser.mail, newUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AUTH", "createUserWithEmail:success::${task.result.user?.uid}")
                    users.document(task.result.user?.uid.toString()).set(user)
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        Log.d("AUTH", "auth")
                    } else {
                        Log.d("AUTH", "not auth")
                    }
                } else {
                    Log.w("AUTH", "createUserWithEmail:failure", task.exception)
                }
            }
        return RequestResult.Error(RequestError.UserNotFount)
    }
}