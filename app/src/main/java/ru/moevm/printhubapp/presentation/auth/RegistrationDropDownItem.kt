package ru.moevm.printhubapp.presentation.auth

import ru.moevm.printhubapp.R

enum class RegistrationDropDownItem(val value: Int, val role: Role) {
    CLIENT(R.string.client_role, Role.CLIENT),
    PRINTHUB(R.string.printhub_role, Role.PRINTHUB)
}