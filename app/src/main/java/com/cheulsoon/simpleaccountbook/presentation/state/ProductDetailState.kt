package com.cheulsoon.simpleaccountbook.presentation.state

import com.cheulsoon.simpleaccountbook.domain.model.ProductDetail

data class ProductDetailState(val isLoading : Boolean = false,
                              val data : ProductDetail? = null,
                              var error : String ="")
