package com.yourssu.notissu.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yourssu.notissu.room.entity.Notice

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notice")
    fun getAllBookmarks(): List<Notice>

    @Query("SELECT * From notice WHERE url IN (:urlString)")
    fun getBookmarkByUrl(urlString: String): LiveData<List<Notice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBookmarks(vararg notices: Notice)

    @Delete
    fun deleteBookmark(notice: Notice)
}