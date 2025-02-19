package ru.moevm.printhubapp.presentation.client

import ru.moevm.printhubapp.R

enum class PrintFormatItem(val value: Int, val price: Int) {
    A1(R.string.A1, 100),
    A2(R.string.A2, 85),
    A3(R.string.A3, 70),
    A4(R.string.A4, 20),
    A5(R.string.A5, 50),
    A6(R.string.A6, 35)
}