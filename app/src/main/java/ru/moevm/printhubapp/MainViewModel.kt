package ru.moevm.printhubapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.domain.usecases.CheckLoginUseCase
import ru.moevm.printhubapp.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _userRole = MutableStateFlow<Role>(Role.INIT)
    private val _userMail = MutableStateFlow<String>("")
    val userRole: StateFlow<Role> get() = _userRole.asStateFlow()
    val userMail: StateFlow<String> get() = _userMail.asStateFlow()

    init {
        viewModelScope.launch {
            if (checkLoginUseCase()) {
                getUserUseCase { user ->
                    _userRole.value = user.role
                    _userMail.value = user.mail
                }
            }
        }
    }
}