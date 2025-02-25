package com.cheulsoon.simpleaccountbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Transaction(

    @PrimaryKey
    val id: Int,

    val title: String,
    val amount: Int,
    val date: Long,

    )