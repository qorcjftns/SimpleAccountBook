package com.cheulsoon.simpleaccountbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cheulsoon.simpleaccountbook.presentation.screens.CalendarView
import com.cheulsoon.simpleaccountbook.ui.theme.SimpleABTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get Initial Date
        val date = Calendar.getInstance().time
        setContent {
            SimpleABTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    CalendarView(
                        year = calendar.get(Calendar.YEAR),
                        month = calendar.get(Calendar.MONTH),
                        startFromSunday = true,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
