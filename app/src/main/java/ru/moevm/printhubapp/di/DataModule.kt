package ru.moevm.printhubapp.di

import android.content.Context
import android.content.SharedPreferences
import ru.moevm.printhubapp.data.repository.AuthRepositoryImpl
import ru.moevm.printhubapp.data.repository.OrdersRepositoryImpl
import ru.moevm.printhubapp.domain.repository.AuthRepository
import ru.moevm.printhubapp.domain.repository.OrdersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore,
        sharedPreferences: SharedPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(auth, db, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(
        db: FirebaseFirestore
    ): OrdersRepository {
        return OrdersRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user_uid", Context.MODE_PRIVATE)
    }
}