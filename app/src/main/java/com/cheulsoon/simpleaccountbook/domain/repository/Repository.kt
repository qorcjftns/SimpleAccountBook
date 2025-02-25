package com.cheulsoon.simpleaccountbook.domain.repository

import com.cheulsoon.simpleaccountbook.domain.model.ProductDetail
import com.cheulsoon.simpleaccountbook.domain.model.ProductItem

interface Repository {

    suspend fun getProductList() : List<ProductItem>

    suspend fun getProductDetail(id : String) : ProductDetail

}