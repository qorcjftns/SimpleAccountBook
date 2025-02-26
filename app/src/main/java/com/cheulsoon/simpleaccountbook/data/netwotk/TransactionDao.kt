package com.cheulsoon.simpleaccountbook.data.netwotk

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface TransactionDao {

    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    fun getTransactionById(id: Int) : Flow<Transaction>

    @Query("SELECT * FROM `Transaction` WHERE date_day = :day AND date_month = :month AND date_year = :year")
    fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<Transaction>>

    @Query("SELECT * FROM `Transaction` WHERE date_month = :month AND date_year = :year")
    fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<Transaction>>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

}