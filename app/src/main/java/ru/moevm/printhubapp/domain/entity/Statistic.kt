package ru.moevm.printhubapp.domain.entity

data class Statistic(
    val companyId: String = "",
    val formatsCount: Map<String, Int> = mapOf<String, Int>(),
    val profit: Int = 0,
    val totalPaperCount: Int = 0
)