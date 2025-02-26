package com.cheulsoon.simpleaccountbook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.cheulsoon.simpleaccountbook.presentation.screens.AddScreen
import com.cheulsoon.simpleaccountbook.presentation.screens.CalendarScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyViewPager()
        }
    }
}

@Composable
fun MyViewPager() {
    val tabs = listOf(
        Pair("홈", Icons.Default.Home),
        Pair("추가", Icons.Default.Add)
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when(page) {
                0 -> CalendarScreen()
                1 -> AddScreen()
                else -> Text("Haha! You found a bug!")
            }
        }
        TabRow(
            selectedTabIndex = pagerState.currentPage, // 현재 선택된 페이지와 동기화
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, (title, icon) ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index) // 탭 클릭 시 페이지 변경
                        }
                    },
                    icon = {
                        Icon(imageVector = icon, contentDescription = title)
                    },
                    text = { Text(title) }
                )
            }
        }
    }
}