package com.cheulsoon.simpleaccountbook.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheulsoon.simpleaccountbook.core.common.UiState
import com.cheulsoon.simpleaccountbook.domain.usecase.GetTransactionByDateUseCase
import com.cheulsoon.simpleaccountbook.presentation.state.ProductListState
import com.cheulsoon.simpleaccountbook.presentation.state.TransactionListState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val getTransactionByDateUseCase: GetTransactionByDateUseCase
) : ViewModel() {

    private val _transactionList = mutableStateOf(TransactionListState())
    val transactionList : State<TransactionListState> get() = _transactionList

    init {
        getTransactionByDateUseCase.invoke().onEach {
            when(it){
                is UiState.Loading->{
                    _transactionList.value = TransactionListState(isLoading = true)
                }
                is UiState.Success->{
                    _transactionList.value = TransactionListState(data = it.data)
                }
                is UiState.Error->{
                    _transactionList.value = TransactionListState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}