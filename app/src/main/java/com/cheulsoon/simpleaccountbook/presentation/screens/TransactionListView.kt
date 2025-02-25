package com.cheulsoon.simpleaccountbook.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.presentation.screens.component.listItem
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionListView(){

    val viewModel : TransactionViewModel = hiltViewModel()
    val result = viewModel.transactionList.value

    if(result.isLoading) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }

    result.data?.let {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(it) { item ->
                listItem(item) { transaction ->

                }
            }
        }
    }

}