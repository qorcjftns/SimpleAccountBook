package com.cheulsoon.simpleaccountbook.data.di

import android.content.Context
import androidx.room.Room
import com.cheulsoon.simpleaccountbook.data.database.AppDatabase
import com.cheulsoon.simpleaccountbook.data.netwotk.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "AppDatabase",
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): TransactionDao {
        return db.transactionDao()
    }

}