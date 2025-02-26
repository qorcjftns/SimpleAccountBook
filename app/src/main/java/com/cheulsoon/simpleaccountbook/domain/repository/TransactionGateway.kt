package com.cheulsoon.simpleaccountbook.domain.repository

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionGateway {

    suspend fun getTransactionById(id: Long) : Flow<Transaction>

    suspend fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<Transaction>>

    suspend fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

}