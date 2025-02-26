package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {
    var transaction: Transaction? = null

    operator fun invoke() = CoroutineScope(IO).launch {
        transaction?.let {
            transactionGatewayImpl.updateTransaction(it)
        }
    }


}