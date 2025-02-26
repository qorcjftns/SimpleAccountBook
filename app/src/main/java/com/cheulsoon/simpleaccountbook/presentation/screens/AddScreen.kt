package com.cheulsoon.simpleaccountbook.presentation.screens

import android.app.TimePickerDialog
import android.telephony.PhoneNumberUtils.formatNumber
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheulsoon.simpleaccountbook.core.common.toCalendar
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.presentation.viewmodel.TransactionViewModel
import com.cheulsoon.simpleaccountbook.ui.theme.SimpleABTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddScreen() {
    val viewModel: TransactionViewModel = hiltViewModel()
    val selectedDate = viewModel.selectedDate.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() } // 🔹 Snackbar 상태를 최상단에서 관리

    SimpleABTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🔹 날짜 표시 + 좌우 화살표 버튼
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // 이전 날짜 버튼
                    IconButton(onClick = { viewModel.changeDate(-1) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "이전 날짜")
                    }

                    // 날짜 표시
                    Text(
                        text = formatDate(selectedDate),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // 다음 날짜 버튼
                    IconButton(onClick = { viewModel.changeDate(1) }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "다음 날짜")
                    }
                }

                // 🔹 기존 입력 폼
                InputForm(snackbarHostState)

                // 🔹 Snackbar 표시
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}
fun formatDate(calendar: Calendar): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return sdf.format(calendar.time)
}

@Composable
fun InputForm(snackbarHostState: SnackbarHostState) {
    val viewModel: TransactionViewModel = hiltViewModel()
    val selectedDate = viewModel.selectedDate.collectAsState().value

    var time by remember { mutableStateOf(getCurrentTime()) }
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") } // 숫자 입력을 위해 String으로 관리
    var description by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // 각 TextField 간격
    ) {

        TimePickerField(time = time, onTimeChange = { time = it })

        // 🔹 제목 입력 필드
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = false // 입력 시 오류 해제
            },
            label = { Text("제목") },
            isError = titleError,
            supportingText = { if (titleError) Text("제목을 입력하세요.", color = Color.Red) },
            modifier = Modifier.fillMaxWidth()
        )

        // 🔹 숫자 입력 필드 (Amount)
        OutlinedTextField(
            value = formatNumber(amount),
            onValueChange = { newValue ->
                val cleanValue = newValue.replace(",", "") // 콤마 제거 후 숫자만 저장
                if (cleanValue.all { it.isDigit() }) {
                    amount = cleanValue
                    amountError = false // 입력 시 오류 해제
                }
            },
            label = { Text("금액") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = amountError,
            supportingText = { if (amountError) Text("금액을 입력하세요.", color = Color.Red) },
            modifier = Modifier.fillMaxWidth()
        )


        // 🔹 설명 입력 필드
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("설명") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 150.dp) // 최소 높이 2줄, 최대 높이 5줄
                .verticalScroll(scrollState),
            maxLines = 5,
            minLines = 2
        )

        // 🔹 저장 버튼
        Button(
            onClick = {
                // 필수 입력값 검증
                if (title.isBlank()) {
                    titleError = true
                }
                if (amount.isBlank() || amount.toIntOrNull() == null) {
                    amountError = true
                }

                if (!titleError && !amountError) {
                    val amountInt = amount.toIntOrNull() ?: 0
                    val currentTime = getCurrentTime()
                    selectedDate.apply {
                        val parsed =  parseTime(time)
                        set(Calendar.HOUR_OF_DAY, parsed.first)
                        set(Calendar.MINUTE, parsed.second)
                    }

                    // 데이터 저장
                    viewModel.insertTransaction(
                        Transaction(
                            title = title,
                            amount = amountInt,
                            description = description,
                            date = selectedDate.timeInMillis,
                        )
                    )

                    keyboardController?.hide()

                    // Snackbar 표시
                    showSnackbar = true

                    // 입력값 초기화 (time은 현재 시간으로 설정)
                    title = ""
                    amount = ""
                    description = ""
                    time = currentTime
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장")
        }
    }
    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("저장 완료!")
            showSnackbar = false // 다시 초기화하여 한 번만 실행되도록 설정
        }
    }
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun TimePickerField(
    time: String,
    onTimeChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                onTimeChange(String.format("%02d:%02d", selectedHour, selectedMinute)) // HH:mm 포맷
            },
            hour,
            minute,
            true // 24시간 형식
        )
    }

    OutlinedTextField(
        value = time,
        onValueChange = { onTimeChange(it) },
        label = { Text("시간 입력 (24시간)") },
        readOnly = true, // 직접 입력 방지
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { timePickerDialog.show() }) {
                Icon(imageVector = Icons.Default.Schedule, contentDescription = "시간 선택")
            }
        }
    )
}

fun parseTime(time: String): Pair<Int, Int> {
    val parts = time.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    return Pair(hour, minute)
}