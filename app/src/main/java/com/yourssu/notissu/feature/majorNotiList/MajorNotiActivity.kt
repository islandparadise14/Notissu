package com.yourssu.notissu.feature.majorNotiList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import kotlinx.android.synthetic.main.activity_major_noti.*

class MajorNotiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major_noti)

        initSetting()
    }

    fun initSetting() {
        val majorNumber = intent.getIntExtra(MAJOR_INTENT_KEY, -1)

        notissuTopbar.setBackButtonClicked { finish() }

        if (majorNumber != -1) {
            supportFragmentManager.beginTransaction().replace(
                R.id.majorNotiMain,
                SelectNotiListFragment.getInstance(majorNumber, null)).commit()
            notissuTopbar.setTitle(MajorData.getInstance().getMajorByIndex(majorNumber).name)
        }
        else
            Toast.makeText(applicationContext, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
    }
}
