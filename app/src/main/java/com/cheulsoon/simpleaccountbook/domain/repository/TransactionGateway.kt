package com.cheulsoon.simpleaccountbook.domain.repository

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TransactionGateway {

    suspend fun getTransactionById(id: Int) : Flow<Transaction>

    suspend fun getTransactionsByDate(date: Long) : Flow<List<Transaction>>

    suspend fun getTransactionsByMonth(month: Int) : Flow<List<Transaction>>

}