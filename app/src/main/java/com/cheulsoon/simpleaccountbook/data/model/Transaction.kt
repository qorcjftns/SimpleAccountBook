package com.cheulsoon.simpleaccountbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String = "",
    val amount: Int = 0,
    val date: Long = 0,
    val description: String = "",

)