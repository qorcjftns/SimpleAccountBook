package com.cheulsoon.simpleaccountbook.data.database

import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cheulsoon.simpleaccountbook.data.model.Transaction
import com.cheulsoon.simpleaccountbook.data.netwotk.TransactionDao
import java.util.Date

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

class Callback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.beginTransaction()
        try {
            db.execSQL(
                "INSERT INTO Transaction('id', 'title', 'amount', 'date', 'description') VALUES (?, ?, ?, ?, ?)",
                arrayOf(1, "Test Transaction", 100, Date().time, "Description Test")
            )
        } catch (e: Exception) {
            db.endTransaction()
        }
        db.endTransaction()
    }

}