package com.cheulsoon.simpleaccountbook.core.common

import com.cheulsoon.simpleaccountbook.data.model.ProductListDTO
import com.cheulsoon.simpleaccountbook.domain.model.ProductDetail
import com.cheulsoon.simpleaccountbook.domain.model.ProductItem
import java.util.Calendar
import java.util.Date


fun ProductListDTO.toProductList() : ProductItem{
    return ProductItem(id = this.id, image= this.image, title = this.title, description= this.description)
}

fun ProductListDTO.toProductDetail() : ProductDetail{
    return ProductDetail(
        category= this.category,
        description = this.description,
        id = this.id,
        image= this.image,
        price = this.price,
        title = this.title)
}

fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

