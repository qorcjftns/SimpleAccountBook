package com.cheulsoon.simpleaccountbook.data.respository

import com.cheulsoon.simpleaccountbook.core.common.toProductDetail
import com.cheulsoon.simpleaccountbook.core.common.toProductList
import com.cheulsoon.simpleaccountbook.data.netwotk.ApiService
import com.cheulsoon.simpleaccountbook.domain.model.ProductDetail
import com.cheulsoon.simpleaccountbook.domain.model.ProductItem
import com.cheulsoon.simpleaccountbook.domain.repository.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun getProductList(): List<ProductItem> {
       return apiService.getAllProductListAPI().map { it.toProductList() }
    }

    override suspend fun getProductDetail(id: String): ProductDetail {
        return apiService.getProductDetailsAPI(id).toProductDetail()
    }

}