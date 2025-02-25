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

    @Query("SELECT * FROM `Transaction` WHERE CAST(strftime('%d',date / 1000,'unixepoch') AS INTEGER) = :day AND CAST(strftime('%m',date / 1000,'unixepoch') AS INTEGER) = :month AND CAST(strftime('%y',date / 1000,'unixepoch') AS INTEGER) = :year")
    fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<Transaction>>

    @Query("SELECT * FROM `Transaction` WHERE CAST(strftime('%m',date / 1000,'unixepoch') AS INTEGER) = :month AND CAST(strftime('%y',date / 1000,'unixepoch') AS INTEGER) = :year")
    fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<Transaction>>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

}