package com.cheulsoon.simpleaccountbook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheulsoon.simpleaccountbook.core.common.UiState
import com.cheulsoon.simpleaccountbook.core.common.toLocalTimeMillis
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.domain.usecase.GetTransactionByDateUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.InsertTransactionUseCase
import com.cheulsoon.simpleaccountbook.presentation.state.TransactionListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionByDateUseCase: GetTransactionByDateUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val transactionList : StateFlow<TransactionListState> get() = selectedDate.flatMapLatest { date ->
        getTransactionByDateUseCase.invoke(date).map {
            mapToTransactionListState(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, TransactionListState(true))

    private val _selectedDate = MutableStateFlow(Calendar.getInstance()) // 현재 날짜 기본값
    val selectedDate: StateFlow<Calendar> = _selectedDate.asStateFlow()

    fun insertTransaction(transaction: Transaction) {
        insertTransactionUseCase.transaction = transaction
        insertTransactionUseCase.invoke()
    }

    fun setDate(date: Calendar) {
        _selectedDate.value = date
    }

    fun addMonth(i: Int) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = selectedDate.value.timeInMillis
            add(Calendar.MONTH, i)
        }
        _selectedDate.value = calendar
    }

    fun changeDate(days: Int) {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = _selectedDate.value.toLocalTimeMillis()
            add(Calendar.DAY_OF_MONTH, days) // 날짜 변경
        }
        _selectedDate.value = calendar
    }

    private fun mapToTransactionListState(it: UiState<List<Transaction>>): TransactionListState {
        return when(it){
            is UiState.Loading->{
                TransactionListState(isLoading = true)
            }
            is UiState.Success->{
                TransactionListState(data = it.data)
            }
            is UiState.Error->{
               TransactionListState(error = it.message.toString())
            }
        }
    }

}