package com.yourssu.notissu.feature.bookmark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourssu.notissu.R
import com.yourssu.notissu.data.DATE_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.TITLE_INTENT_KEY
import com.yourssu.notissu.data.URL_INTENT_KEY
import com.yourssu.notissu.databinding.ActivityBookmarkBinding
import com.yourssu.notissu.feature.notiDetail.NotiDetailActivity
import com.yourssu.notissu.koin.repository.DatabaseRepository
import com.yourssu.notissu.room.entity.Notice
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_noti_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class BookmarkActivity : AppCompatActivity(), BookmarkContract.View {
    private lateinit var mBinding: ActivityBookmarkBinding
    private lateinit var mAdapter: BookmarkAdapter
    private val database: DatabaseRepository by inject()
    private lateinit var data: List<Notice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        mBinding.notiDetailTobBar.setTitle("북마크")
        CoroutineScope(Dispatchers.Main).launch {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                data = database.getAllBookmarks()
            }
            mBinding.noContent.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
            mAdapter = BookmarkAdapter(data, clicked)
            bookmarkRecycler.apply {
                layoutManager = LinearLayoutManager(this@BookmarkActivity)
                adapter = mAdapter
            }
        }
    }

    private val clicked = { url: String, title: String, date: String, majorNumber: Int  ->
        startActivity(Intent(this, NotiDetailActivity::class.java).apply {
            putExtra(TITLE_INTENT_KEY, title)
            putExtra(DATE_INTENT_KEY, date)
            putExtra(URL_INTENT_KEY, url)
            putExtra(MAJOR_INTENT_KEY, majorNumber)
        })
    }
}
