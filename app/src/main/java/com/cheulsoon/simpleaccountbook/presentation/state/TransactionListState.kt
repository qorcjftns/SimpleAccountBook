package com.cheulsoon.simpleaccountbook.presentation.state

import com.cheulsoon.simpleaccountbook.domain.model.Transaction

data class TransactionListState (
    val isLoading: Boolean = false,
    val data: List<Transaction>? = null,
    var error: String = ""
)