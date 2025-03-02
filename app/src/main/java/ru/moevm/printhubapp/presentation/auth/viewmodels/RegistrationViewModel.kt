package ru.moevm.printhubapp.presentation.auth.viewmodels

import androidx.lifecycle.ViewModel
import ru.moevm.printhubapp.domain.entity.Registration
import ru.moevm.printhubapp.domain.usecases.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    fun registration(newUser: Registration) {
        registrationUseCase(newUser)
    }
}