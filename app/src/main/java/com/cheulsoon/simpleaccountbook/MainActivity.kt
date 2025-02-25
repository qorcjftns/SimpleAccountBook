package com.cheulsoon.simpleaccountbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cheulsoon.simpleaccountbook.presentation.screens.CalendarView
import com.cheulsoon.simpleaccountbook.screens.listingScreen
import com.cheulsoon.simpleaccountbook.ui.theme.SimpleABTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

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
                    CalendarView(
                        year = date.year,
                        month = date.month,
                        displayNext = true,
                        displayPrev = true,
                        onClick = {},
                        onClickNext = {},
                        onClickPrev = {},
                        startFromSunday = true,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
