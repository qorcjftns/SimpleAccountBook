package com.cheulsoon.simpleaccountbook.presentation.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheulsoon.simpleaccountbook.domain.model.Transaction


@Composable
fun listItem(transaction: Transaction, onItemClick : (Transaction) -> Unit) {
    Card (modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp).clickable {
            onItemClick(transaction)
        }) {
            userDescription(transaction, Modifier.weight(0.6f))
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp))
    }
}

@Composable
fun userDescription(transaction: Transaction, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(transaction.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text(transaction.amount.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text(transaction.description, fontSize = 12.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)
    }
}
