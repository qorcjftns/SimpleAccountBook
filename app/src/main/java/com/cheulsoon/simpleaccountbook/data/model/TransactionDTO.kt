package com.cheulsoon.simpleaccountbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionDTO(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String = "",
    val amount: Int = 0,
    val date_year: Int = 0,
    val date_month: Int = 0,
    val date_day: Int = 0,
    val date_hour: Int = 0,
    val date_min: Int = 0,
    val description: String = "",

)