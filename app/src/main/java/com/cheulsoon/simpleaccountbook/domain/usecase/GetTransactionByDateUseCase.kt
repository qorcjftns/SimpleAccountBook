package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.respository.RepositoryImpl
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class GetTransactionByDateUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {

    val date: Long = Date().time

    operator fun invoke(): Flow<List<Transaction>> {
        // Fetch all users from the repository and return them as a Flow
        return flow {
            transactionGatewayImpl.getTransactionsByDate(date).collect { transaction ->
                emit(transaction)
            }
        }
    }

}