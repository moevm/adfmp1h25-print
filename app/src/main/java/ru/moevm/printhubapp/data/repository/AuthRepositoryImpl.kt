package ru.moevm.printhubapp.data.repository

import android.util.Log
import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.Auth
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.entity.result.RequestError
import ru.moevm.printhubapp.domain.entity.result.RequestResult
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    private val users = db.collection("users")

    override fun authorization(user: Auth): RequestResult<Unit> {
        auth.signInWithEmailAndPassword(user.mail, user.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val currentUserId = auth.currentUser?.uid ?: ""
                    users.document(currentUserId).get()
                        .addOnSuccessListener { document ->
                            if(document != null) {
                                val currentUser = document.toObject<UserDto>()
                                Log.d("AUTH", currentUser.toString())
                            } else {
                                Log.d("AUTH", "ERROR")
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.d("AUTH", e.message.toString())
                        }

                }
            }
        return RequestResult.Success(Unit)
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