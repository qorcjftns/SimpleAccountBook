package com.cheulsoon.simpleaccountbook.domain.repository

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TransactionGateway {

    suspend fun getTransactionById(id: Int) : Flow<Transaction>

    suspend fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<Transaction>>

    suspend fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

}