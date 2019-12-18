package com.yourssu.notissu.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yourssu.notissu.R
import com.yourssu.notissu.data.FragmentData
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.feature.majorList.MajorListFragment
import com.yourssu.notissu.feature.majorNotiList.MajorNotiActivity
import com.yourssu.notissu.feature.myInfo.MyInfoFragment
import com.yourssu.notissu.feature.search.SearchFragment
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import com.yourssu.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("CAST_NEVER_SUCCEEDS")
class MainActivity : AppCompatActivity() {
    private lateinit var majorName: String
    private var fragments :ArrayList<Fragment> = ArrayList()
    private lateinit var fragmentMyMajor: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        majorName = MajorData.getInstance().getMajorByIndex(SharedPreferenceUtil.getInt(MAJOR_KEY)).name
        mainTopBar.setTitle(majorName)

        bottomNavigationSetting()

        fragments.add(MajorListFragment.getInstance())
        fragments.add(SearchFragment.getInstance(application))
        fragments.add(MyInfoFragment.getInstance())

        for (fragment in fragments)
            supportFragmentManager.beginTransaction().add(R.id.main_container, fragment).commit()

        fragmentMyMajor = SelectNotiListFragment.getInstance(SharedPreferenceUtil.getInt(MAJOR_KEY), null)
        supportFragmentManager.beginTransaction().add(R.id.main_container ,fragmentMyMajor).commit()
        fragmentHide()
        supportFragmentManager.beginTransaction().show(fragmentMyMajor).commit()
    }

    private fun bottomNavigationSetting() {
        bottomNavigationView.setOnNavigationItemSelectedListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.my_notification -> {
                    mainTopBar.setTitle(majorName)
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragmentMyMajor).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.major_list -> {
                    mainTopBar.setTitle("전공목록")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[0]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.major_search -> {
                    mainTopBar.setTitle("검색")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[1]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    mainTopBar.setTitle("더보기")
                    fragmentHide()
                    supportFragmentManager.beginTransaction().show(fragments[2]).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {return@setOnNavigationItemSelectedListener false}
            }
        }
    }

    private fun fragmentHide() {
        supportFragmentManager.beginTransaction().hide(fragmentMyMajor).commit()
        for (fragment in fragments)
            supportFragmentManager.beginTransaction().hide(fragment).commit()
    }
}
