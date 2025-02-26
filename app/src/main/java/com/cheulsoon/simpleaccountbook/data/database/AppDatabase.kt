package com.cheulsoon.simpleaccountbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.netwotk.TransactionDao

@Database(
    entities = [
        Transaction::class
    ],
    version = 1,
    autoMigrations = [
    ],
    exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}