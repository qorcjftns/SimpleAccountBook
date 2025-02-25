package com.cheulsoon.simpleaccountbook.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.ProductListVewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun CalendarView(
    year: Int,
    month: Int,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {

    val viewModel : ProductListVewModel = hiltViewModel()
    var result = viewModel.productList.value

    if(result.isLoading) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { onClickButton(true) },
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
            IconButton(
                onClick = { onClickButton(true) },
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }
            Text(
                text = Date(year, month, 1).formatTitleString(),
                style = typography.headlineMedium,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        CalendarGrid(
            date = getDateList(year, month + 1),
            onClick = { date -> onClickItem(date) },
            startFromSunday = startFromSunday,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

private fun onClickItem(date: Date) {

}

private fun onClickButton(next: Boolean) {

}

fun getDateList(year: Int, month: Int): List<Date> {
    val list = arrayListOf<Date>()
    val maxDate: Int = when(month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        else -> if(year % 4 == 0) 29 else 28
    }
    for(d in 1..maxDate) {
        list.add(Date(year, month - 1, d))
    }
    return list
}

@Composable
private fun CalendarGrid(
    date: List<Date>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().day + 1
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        repeat(if (!startFromSunday) weekdayFirstDay - 2 else weekdayFirstDay - 1) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(date = it, onClick = { onClick(it) })
        }
    }
}

fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val list = (1..7).toList()
    return (if (startFromSunday) list else list.drop(1) + list.take(1)).toList()
}

private fun Date.formatTitleString(): String = SimpleDateFormat("yyyy.MM", Locale.getDefault()).format(this)
private fun Date.formatToCalendarDay(): String = SimpleDateFormat("d", Locale.getDefault()).format(this)

@Composable
fun CalendarCell (
    date: Date,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
            .background(
                shape = RoundedCornerShape(CornerSize(8.dp)),
                color = colorScheme.secondaryContainer,
            )
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = colorScheme.onSecondaryContainer,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

@Composable
private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        Text(
            text = text.orEmpty(),
            color = colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    content: @Composable () -> Unit,
) {
    val horizontalGap = with(LocalDensity.current) {
        horizontalGapDp.roundToPx()
    }
    val verticalGap = with(LocalDensity.current) {
        verticalGapDp.roundToPx()
    }
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        val xPos: MutableList<Int> = mutableListOf()
        val yPos: MutableList<Int> = mutableListOf()
        var currentX = 0
        var currentY = 0
        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)
            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxHeight = singleWidth, maxWidth = singleWidth))
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap,
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index],
                )
            }
        }
    }
}