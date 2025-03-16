package ru.moevm.printhubapp.data.mapper

import ru.moevm.printhubapp.data.model.OrderDto
import ru.moevm.printhubapp.data.model.StatisticDto
import ru.moevm.printhubapp.data.model.UserDto
import ru.moevm.printhubapp.domain.entity.Order
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.domain.entity.Statistic
import ru.moevm.printhubapp.domain.entity.User

fun UserDto.toEntity(): User =
    User(
        id = this.id,
        mail = this.mail,
        password = this.password,
        role = this.role.toRole(),
        address = this.address,
        nameCompany = this.nameCompany,
        statisticId = this.statistic_id
    )

fun String.toRole(): Role =
    when (this) {
        "CLIENT" -> Role.CLIENT
        "PRINTHUB" -> Role.PRINTHUB
        else -> Role.CLIENT
    }

fun OrderDto.toEntity(): Order =
    Order(
        id = this.id,
        clientId = this.client_id,
        companyId = this.company_id,
        number = this.number,
        format = this.format,
        paperCount = this.paper_count,
        files = this.files,
        totalPrice = this.total_price,
        comment = this.comment,
        status = this.status,
        rejectReason = this.reject_reason,
        createdAt = this.created_at,
        updatedAt = this.updated_at
    )

fun StatisticDto.toEntity(): Statistic =
    Statistic(
        companyId = this.company_id,
        formatsCount = this.formats_count,
        profit = this.profit,
        totalPaperCount = this.total_paper_count
    )