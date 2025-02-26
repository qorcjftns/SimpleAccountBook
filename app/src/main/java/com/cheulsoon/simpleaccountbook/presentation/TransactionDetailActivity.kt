package com.cheulsoon.simpleaccountbook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.presentation.screens.component.EditTransactionScreen
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTransactionActivity : ComponentActivity() {
    private lateinit var viewModel: TransactionViewModel
    private var transactionId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = hiltViewModel()
            transactionId = intent.getLongExtra("transaction_id", -1)
            if (transactionId != -1L) {
                viewModel.getTransactionById(transactionId) // Transaction 불러오기
            }
            EditTransactionScreen(viewModel)
        }
    }
}