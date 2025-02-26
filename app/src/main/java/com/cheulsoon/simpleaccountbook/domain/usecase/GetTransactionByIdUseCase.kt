package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {

    operator fun invoke(id: Long): Flow<Transaction> {
        // Fetch all users from the repository and return them as a Flow
        return flow {
            transactionGatewayImpl.getTransactionById(id)
                .collect { transaction ->
                emit(transaction)
            }
        }
    }

}