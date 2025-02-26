package com.cheulsoon.simpleaccountbook.presentation.screens.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel

@Composable
fun EditTransactionScreen(viewModel: TransactionViewModel = hiltViewModel()) {
    val transaction by viewModel.transaction.observeAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf(transaction?.title ?: "") }
    var amount by remember { mutableStateOf(transaction?.amount?.toString() ?: "") }
    var description by remember { mutableStateOf(transaction?.description ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { (context as? Activity)?.finish() }) { //  현재 Activity 종료
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
            }
            Text(
                text = "거래 수정",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    amount = newValue
                }
            },
            label = { Text("금액") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val updatedTransaction = transaction?.copy(
                    title = title,
                    amount = amount.toIntOrNull() ?: 0,
                    description = description
                )
                if (updatedTransaction != null) {
                    viewModel.updateTransaction(updatedTransaction)
                    Toast.makeText(context, "수정 완료!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장")
        }
    }
}