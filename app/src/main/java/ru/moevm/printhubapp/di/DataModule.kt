package ru.moevm.printhubapp.di

import ru.moevm.printhubapp.data.repository.AuthRepositoryImpl
import ru.moevm.printhubapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(auth, db)
    }
}