package com.yourssu.notissu.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yourssu.notissu.room.entity.Notice

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notice")
    fun getAllBookmarks(): ArrayList<Notice>

    @Insert
    fun insertAllBookmarks(vararg notices: Notice)

    @Delete
    fun deleteBookmark(notice: Notice)
}