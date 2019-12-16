package com.yourssu.notissu.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.feature.majorList.MajorListFragment
import com.yourssu.notissu.feature.myInfo.MyInfoFragment
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import com.yourssu.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var majorName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        majorName = MajorData.getInstance().getMajorByIndex(SharedPreferenceUtil.getInt(MAJOR_KEY)).name
        mainTopBar.setTitle(majorName)

        supportFragmentManager.beginTransaction().replace(R.id.main_container, SelectNotiListFragment.getInstance()).commit()

        bottomNavigationSetting()
    }

    private fun bottomNavigationSetting() {
        bottomNavigationView.setOnNavigationItemSelectedListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.my_notification -> {
                    mainTopBar.setTitle(majorName)
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, SelectNotiListFragment.getInstance()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.major_list -> {
                    mainTopBar.setTitle("전공목록")
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, MajorListFragment.getInstance()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    mainTopBar.setTitle("더보기")
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, MyInfoFragment.getInstance()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {return@setOnNavigationItemSelectedListener false}
            }
        }
    }
}
