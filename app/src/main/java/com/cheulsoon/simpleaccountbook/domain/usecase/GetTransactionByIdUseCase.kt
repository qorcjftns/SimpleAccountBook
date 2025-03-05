package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import com.cheulsoon.simpleaccountbook.domain.mapper.toTransaction
import com.cheulsoon.simpleaccountbook.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {

    operator fun invoke(id: Long): Flow<Transaction> {
        // Fetch all users from the repository and return them as a Flow
        return flow {
            transactionGatewayImpl.getTransactionById(id)
                .collect { transaction ->
                emit(transaction.toTransaction())
            }
        }
    }

}