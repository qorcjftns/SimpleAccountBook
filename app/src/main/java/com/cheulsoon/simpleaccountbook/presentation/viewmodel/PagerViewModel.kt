package com.cheulsoon.simpleaccountbook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor() : ViewModel() {
    private val _items = MutableStateFlow(listOf("Page 1", "Page 2", "Page 3"))
    val items: StateFlow<List<String>> = _items.asStateFlow()
}