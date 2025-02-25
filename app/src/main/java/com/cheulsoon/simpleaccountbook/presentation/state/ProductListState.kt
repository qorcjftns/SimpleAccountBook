package com.cheulsoon.simpleaccountbook.presentation.state

import com.cheulsoon.simpleaccountbook.domain.model.ProductItem

data class ProductListState(
    val isLoading: Boolean = false,
    val data: List<ProductItem>? = null,
    var error: String = ""
)
