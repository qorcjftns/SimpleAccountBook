package com.cheulsoon.simpleaccountbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheulsoon.simpleaccountbook.data.model.TransactionDTO
import com.cheulsoon.simpleaccountbook.data.netwotk.TransactionDao

@Database(
    entities = [
        TransactionDTO::class
    ],
    version = 1,
    autoMigrations = [
    ],
    exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}