package com.cheulsoon.simpleaccountbook.domain.mapper

import com.cheulsoon.simpleaccountbook.data.model.TransactionDTO
import com.cheulsoon.simpleaccountbook.domain.model.Transaction

fun Transaction.toTransactionDTO(): TransactionDTO {
    return TransactionDTO(
        id = this.id,
        title = this.title,
        amount = this.amount,
        date_year = this.date_year,
        date_month = this.date_month,
        date_day = this.date_day,
        date_hour = this.date_hour,
        date_min = this.date_min,
        description = this.description
    )
}

fun TransactionDTO.toTransaction(): Transaction {
    return Transaction(
        id = this.id,
        title = this.title,
        amount = this.amount,
        date_year = this.date_year,
        date_month = this.date_month,
        date_day = this.date_day,
        date_hour = this.date_hour,
        date_min = this.date_min,
        description = this.description
    )
}