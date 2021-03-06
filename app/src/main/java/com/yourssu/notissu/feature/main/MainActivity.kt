package com.yourssu.notissu.feature.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.majorList.MajorListFragment
import com.yourssu.notissu.feature.majorNotiList.MajorNotiActivity
import com.yourssu.notissu.feature.myInfo.MyInfoFragment
import com.yourssu.notissu.feature.search.SearchFragment
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import com.yourssu.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("CAST_NEVER_SUCCEEDS")
class MainActivity : AppCompatActivity() {
    val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    private lateinit var majorName: String
    private var fragments :ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        majorName = MajorData.getInstance().getMajorByIndex(SharedPreferenceUtil.getInt(MAJOR_KEY)).name
        mainTopBar.setTitle(majorName)

        bottomNavigationSetting()

        fragmentInit()
    }

    private fun bottomNavigationSetting() {
        bottomNavigationView.setOnNavigationItemSelectedListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.my_notification -> {
                    mainTopBar.setTitle(majorName)
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[0]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.major_list -> {
                    mainTopBar.setTitle("전공목록")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[1]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.school_notification -> {
                    mainTopBar.setTitle("SSU:Catch")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[2]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.major_search -> {
                    mainTopBar.setTitle("검색")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[3]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    mainTopBar.setTitle("더보기")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[4]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {return@setOnNavigationItemSelectedListener false}
            }
        }
    }

    private fun fragmentHide() {
        for (fragment in fragments)
            supportFragmentManager.beginTransaction().hide(fragment).commit()
    }

    fun intentActivity(position: Int) {
        val intent = Intent(this, MajorNotiActivity::class.java)
        intent.putExtra(MAJOR_INTENT_KEY, position)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 1) {
            refreshFragment()
        }
    }

    fun refreshFragment(){
        for (fragment in fragments)
            supportFragmentManager.beginTransaction().detach(fragment).commit()
        majorName = MajorData.getInstance().getMajorByIndex(SharedPreferenceUtil.getInt(MAJOR_KEY)).name
        fragments.clear()
        fragmentInit()

        mainTopBar.setTitle(majorName)
        bottomNavigationView.selectedItemId = R.id.my_notification
    }

    private fun fragmentInit() {
        fragments.add(SelectNotiListFragment.getInstance(SharedPreferenceUtil.getInt(MAJOR_KEY), null))
        fragments.add(MajorListFragment.getInstance())
        fragments.add(SelectNotiListFragment.getInstance(-1, null))
        fragments.add(SearchFragment.getInstance())
        fragments.add(MyInfoFragment.getInstance())

        for (fragment in fragments)
            supportFragmentManager.beginTransaction().add(R.id.main_container, fragment).commit()

        fragmentHide()
        supportFragmentManager.beginTransaction().show(fragments[0]).commit()
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        }
        else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, resources.getString(R.string.back_click), Toast.LENGTH_SHORT).show()
        }
    }
}
