package com.yourssu.notissu.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourssu.notissu.room.dao.NoticeDao
import com.yourssu.notissu.room.entity.Notice


@Database(entities = [Notice::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}