package com.example.notissu.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notissu.R
import com.example.notissu.data.NoticeBusiness
import com.example.notissu.model.Notice
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var result: ArrayList<Notice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //supportFragmentManager.beginTransaction().replace(R.id.main_container, MyNotiFragment.newInstance()).commit()

        val complete = { noticeList: ArrayList<Notice> ->
            result = noticeList
        }

        CoroutineScope(Dispatchers.Main).launch {
            // Show progress from UI thread

            CoroutineScope(Dispatchers.Default).async {
                // background thread
                NoticeBusiness.parseListBiz(0, null, complete)
            }.await()
            // UI data update from UI thread
            // Hide Progress from UI thread
            updateUi(result)
        }
    }

    fun updateUi(list: ArrayList<Notice>) {
        var text = ""
        for (i in list) {
            text += i.title
        }
        textView3.text = text
    }
}
