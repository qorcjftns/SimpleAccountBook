package com.cheulsoon.simpleaccountbook.domain.repository

import com.cheulsoon.simpleaccountbook.data.model.TransactionDTO
import kotlinx.coroutines.flow.Flow

interface TransactionGateway {

    suspend fun getTransactionById(id: Long) : Flow<TransactionDTO>

    suspend fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<TransactionDTO>>

    suspend fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<TransactionDTO>>

    suspend fun insertTransaction(transaction: TransactionDTO)

    suspend fun updateTransaction(transaction: TransactionDTO)

}