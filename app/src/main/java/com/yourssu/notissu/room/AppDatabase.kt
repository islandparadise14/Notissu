package com.yourssu.notissu.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourssu.notissu.room.dao.NoticeDao
import com.yourssu.notissu.room.entity.Notice

@Database(entities = [Notice::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}