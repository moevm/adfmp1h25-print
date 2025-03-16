package ru.moevm.printhubapp.data.model

data class StatisticDto (
    val company_id: String = "",
    val formats_count: Map<String, Int> = mapOf<String, Int>(),
    val profit: Int = 0,
    val total_paper_count: Int = 0
)