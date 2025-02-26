package com.cheulsoon.simpleaccountbook.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cheulsoon.simpleaccountbook.presentation.screens.component.CalendarView
import com.cheulsoon.simpleaccountbook.ui.theme.SimpleABTheme

@Composable
fun CalendarScreen() {
    SimpleABTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CalendarView(
                startFromSunday = true,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}