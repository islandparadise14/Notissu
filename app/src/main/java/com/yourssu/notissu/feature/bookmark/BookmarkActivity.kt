package com.yourssu.notissu.feature.bookmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourssu.notissu.R
import com.yourssu.notissu.koin.repository.DatabaseRepository
import com.yourssu.notissu.room.entity.Notice
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class BookmarkActivity : AppCompatActivity(), BookmarkContract.View {
    private lateinit var mAdapter: BookmarkAdapter
    private val database: DatabaseRepository by inject()
    private lateinit var data: List<Notice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        initView()
    }

    private fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                data = database.getAllBookmarks()
            }
            mAdapter = BookmarkAdapter(data, clicked)
            bookmarkRecycler.apply {
                layoutManager = LinearLayoutManager(this@BookmarkActivity)
                adapter = mAdapter
            }
        }
    }

    private val clicked = {title: String?, url: String? ->

    }
}
