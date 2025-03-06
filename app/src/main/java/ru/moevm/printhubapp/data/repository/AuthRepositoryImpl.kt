package ru.moevm.printhubapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

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

    companion object {
        private const val UID_STRING = "uid_current_user"
    }
}