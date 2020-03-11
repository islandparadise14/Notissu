package com.yourssu.notissu.koin.module

import androidx.room.Room
import com.yourssu.notissu.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "bookmark"
        ).build()
    }
}