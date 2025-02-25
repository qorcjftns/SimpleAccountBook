package com.cheulsoon.simpleaccountbook.data.respository

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.netwotk.TransactionDao
import com.cheulsoon.simpleaccountbook.domain.repository.TransactionGateway
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class TransactionGatewayImpl @Inject constructor(private val transactionDao: TransactionDao) : TransactionGateway {
    override suspend fun getTransactionById(id: Int): Flow<Transaction> {
        return transactionDao.getTransactionById(id)
    }

    override suspend fun getTransactionsByDate(year: Int, month: Int, day: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDate(year, month, day)
    }

    override suspend fun getTransactionsByMonth(year: Int, month: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByMonth(year, month)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertAll(transaction)
    }
}