package ru.moevm.printhubapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import ru.moevm.printhubapp.data.mapper.toEntity
import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.User
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    private val users = db.collection("users")

    override fun authorization(user: Auth, callback: (RequestResult<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(user.mail, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUserId = auth.currentUser?.uid ?: ""

                    sharedPreferences.edit {
                        putString(UID_STRING, currentUserId)
                    }

                    users.document(currentUserId).get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                callback(RequestResult.Success(Unit))
                            } else {
                                callback(RequestResult.Error(RequestError.UserNotFound))
                            }
                        }
                        .addOnFailureListener { e ->
                            callback(RequestResult.Error(RequestError.UserNotFound))
                        }
                } else {
                    val error = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> RequestError.UserNotFound
                        is FirebaseAuthInvalidCredentialsException -> RequestError.InvalidPassword
                        else -> RequestError.Server(task.exception?.message ?: "Unknown error")
                    }
                    callback(RequestResult.Error(error))
                }
            }
    }

    override fun registration(newUser: Registration, callback: (RequestResult<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(newUser.mail, newUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUserId = auth.currentUser?.uid ?: ""

                    sharedPreferences.edit {
                        putString(UID_STRING, currentUserId)
                    }

                    val user = mapOf(
                        "id" to currentUserId,
                        "password" to newUser.password,
                        "mail" to newUser.mail,
                        "role" to newUser.role,
                        "address" to newUser.address,
                        "nameCompany" to newUser.nameCompany
                    )

                    users.document(currentUserId).set(user)
                        .addOnSuccessListener {
                            callback(RequestResult.Success(Unit))
                        }
                        .addOnFailureListener { e ->
                            callback(RequestResult.Error(RequestError.Server("Ошибка сохранения данных: ${e.message}")))
                        }
                } else {
                    val error = when (task.exception) {
                        is FirebaseAuthUserCollisionException -> RequestError.UserAlreadyExists
                        is FirebaseAuthWeakPasswordException -> RequestError.WeakPassword
                        is FirebaseAuthInvalidCredentialsException -> RequestError.InvalidEmail
                        else -> RequestError.Server(task.exception?.message ?: "Unknown error")
                    }
                    callback(RequestResult.Error(error))
                }
            }
    }

    override fun checkLogin(): Boolean {
        return sharedPreferences.contains(UID_STRING)
    }

    override fun getUser(callback: (User) -> Unit) {
        val userUid = sharedPreferences.getString(UID_STRING, "") ?: ""
        Log.d("TAG", userUid)
        users.document(userUid).get().addOnSuccessListener { data ->
            callback(
                data.toObject<UserDto>()?.toEntity() ?: throw RuntimeException("user not found")
            )
        }
    }

    companion object {
        private const val UID_STRING = "uid_current_user"
    }
}