package com.cheulsoon.simpleaccountbook.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.core.common.toCalendar
import com.cheulsoon.simpleaccountbook.presentation.screens.component.listItem
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel
import java.util.Calendar

@Composable
fun TransactionListView(){

    val viewModel : TransactionViewModel = hiltViewModel()
    val selectedDate = viewModel.selectedDate.collectAsState().value
    val result = viewModel.transactionList.collectAsState().value

    if(result.isLoading) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }

    result.data?.let {
        val selectedCalendar = selectedDate.toCalendar()
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("${selectedCalendar.get(Calendar.YEAR)} / ${selectedCalendar.get(Calendar.MONTH) + 1} / ${selectedCalendar.get(Calendar.DAY_OF_MONTH)}")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(it) { item ->
                listItem(item) { transaction ->

                }
            }
        }
    }

    if(result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text("Error: ${result.error}")
        }
    }
}