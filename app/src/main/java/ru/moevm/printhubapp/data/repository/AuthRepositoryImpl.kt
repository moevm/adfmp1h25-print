package ru.moevm.printhubapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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
        return RequestResult.Error(RequestError.UserNotFound)
    }

    companion object {
        private const val UID_STRING = "uid_current_user"
    }
}