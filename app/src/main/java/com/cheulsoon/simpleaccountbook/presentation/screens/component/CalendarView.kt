package com.cheulsoon.simpleaccountbook.presentation.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.core.common.isSameDay
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun CalendarView(
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {

    val viewModel : TransactionViewModel = hiltViewModel()
    val date = viewModel.selectedDate.collectAsState().value

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { viewModel.addMonth(-1) },
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
                }
                IconButton(
                    onClick = { viewModel.addMonth(1) },
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                }
                Text(
                    text = date.formatTitleString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        CalendarGrid(
            date = getDateList(date.get(Calendar.YEAR), date.get(Calendar.MONTH)),
            onClick = { date -> onClickItem(viewModel, date) },
            startFromSunday = startFromSunday,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        TransactionListView()
    }
}

private fun onClickItem(viewModel: TransactionViewModel, date: Calendar) {
    viewModel.setDate(date)
}

private fun onClickButton(viewModel: TransactionViewModel, next: Boolean) {
    viewModel.addMonth(if(next) 1 else -1)
}

fun getDateList(year: Int, month: Int): List<Calendar> {
    val list = arrayListOf<Calendar>()
    val maxDate: Int = when(month) {
        0, 2, 4, 6, 7, 9, 11 -> 31
        3, 5, 8, 10 -> 30
        else -> if(year % 4 == 0) 29 else 28
    }
    for(d in 1..maxDate) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, d)
        list.add(cal)
    }
    return list
}

@Composable
private fun CalendarGrid(
    date: List<Calendar>,
    onClick: (Calendar) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val viewModel: TransactionViewModel = hiltViewModel()
    val selectedDate = viewModel.selectedDate.collectAsState().value
    val firstDay = date.first()
    val weekdayFirstDay = firstDay.get(Calendar.DAY_OF_WEEK)
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
            CalendarCell(
                date = it, onClick = { onClick(it) },
                isSelected = it.isSameDay(selectedDate))
        }
    }
}

fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val list = (1..7).toList()
    return (if (startFromSunday) list else list.drop(1) + list.take(1)).toList()
}

private fun Calendar.formatTitleString(): String = SimpleDateFormat("yyyy.MM", Locale.getDefault()).format(this.time)
private fun Calendar.formatToCalendarDay(): String = SimpleDateFormat("d", Locale.getDefault()).format(this.time)

@Composable
fun CalendarCell (
    date: Calendar,
    onClick: () -> Unit,
    isSelected: Boolean = false,
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
                color = if(isSelected) colorScheme.primaryContainer else colorScheme.secondaryContainer,
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