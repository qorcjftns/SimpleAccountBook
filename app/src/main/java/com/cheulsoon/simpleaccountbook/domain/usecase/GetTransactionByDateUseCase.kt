package com.cheulsoon.simpleaccountbook.domain.usecase

import com.cheulsoon.simpleaccountbook.core.common.UiState
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.respository.RepositoryImpl
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class GetTransactionByDateUseCase @Inject constructor(private val transactionGatewayImpl: TransactionGatewayImpl) {

    operator fun invoke(date: Calendar): Flow<UiState<List<Transaction>>> {
        // Fetch all users from the repository and return them as a Flow
        return flow {
            emit(UiState.Loading())
            transactionGatewayImpl.getTransactionsByDate(
                date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH))
                .collect { transaction ->
                emit(UiState.Success(data = transaction))
            }
        }.catch { e ->
            emit(UiState.Error(message = e.message))
        }
    }

}