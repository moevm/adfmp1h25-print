package ru.moevm.printhubapp.presentation.printhub

import ru.moevm.printhubapp.R

enum class OrderPrinthubStatus(val value: Int) {
    NEW(value = R.string.status_new),
    INWORK(value = R.string.status_in_work),
    AWAIT(value = R.string.status_await),
    READE(value = R.string.status_reade),
    REJECT(value = R.string.status_reject)
}