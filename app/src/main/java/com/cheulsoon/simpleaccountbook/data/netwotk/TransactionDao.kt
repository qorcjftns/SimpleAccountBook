package com.cheulsoon.simpleaccountbook.data.netwotk

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cheulsoon.simpleaccountbook.data.model.TransactionDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    @Query("SELECT * FROM TransactionDTO WHERE id = :id")
    fun getTransactionById(id: Long) : Flow<TransactionDTO>

    @Query("SELECT * FROM TransactionDTO WHERE date_day = :day AND date_month = :month AND date_year = :year")
    fun getTransactionsByDate(year: Int, month: Int, day: Int) : Flow<List<TransactionDTO>>

    @Query("SELECT * FROM TransactionDTO WHERE date_month = :month AND date_year = :year")
    fun getTransactionsByMonth(year: Int, month: Int) : Flow<List<TransactionDTO>>

    @Insert
    fun insertAll(vararg transaction: TransactionDTO)

    @Delete
    fun delete(transaction: TransactionDTO)

    @Update
    fun update(transaction: TransactionDTO)

}