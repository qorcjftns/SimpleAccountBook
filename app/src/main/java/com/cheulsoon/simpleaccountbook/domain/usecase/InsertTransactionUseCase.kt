package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.core.common.UiState
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.respository.RepositoryImpl
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {

    var transaction: Transaction? = null

    operator fun invoke() = CoroutineScope(IO).launch {
        transaction.let {
            transactionGatewayImpl.insertTransaction(it!!)
        }
    }

}