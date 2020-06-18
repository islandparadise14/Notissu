package com.yourssu.notissu.koin.repositoryimpl

import android.content.Context
import androidx.lifecycle.LiveData
import com.yourssu.notissu.koin.repository.DatabaseRepository
import com.yourssu.notissu.room.DatabaseHelper
import com.yourssu.notissu.room.entity.Notice

class DatabaseRepositoryImpl(context: Context): DatabaseRepository {
    private val instance = DatabaseHelper.getDatabase(context)

    override fun getAllBookmarks(): List<Notice> = instance.appDatabase().noticeDao().getAllBookmarks()

    override fun getBookmarkByUrl(urlString: String): LiveData<List<Notice>> = instance.appDatabase().noticeDao().getBookmarkByUrl(urlString)

    override fun insertAllBookmarks(notice: Notice) = instance.appDatabase().noticeDao().insertAllBookmarks(notice)

    override fun deleteBookmark(notice: Notice) = instance.appDatabase().noticeDao().deleteBookmark(notice)
}