package com.yourssu.notissu.koin.repository

import androidx.lifecycle.LiveData
import com.yourssu.notissu.room.entity.Notice

interface DatabaseRepository {
    fun getAllBookmarks(): List<Notice>

    fun getBookmarkByUrl(urlString: String): LiveData<List<Notice>>

    fun insertAllBookmarks(notice: Notice)

    fun deleteBookmark(notice: Notice)
}