package com.yourssu.notissu.feature.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yourssu.notissu.feature.main.MainActivity
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.feature.majorList.MajorListFragment
import com.yourssu.notissu.feature.myInfo.MyInfoFragment
import com.yourssu.notissu.feature.selectMajor.SelectMajorActivity
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import com.yourssu.notissu.utils.SharedPreferenceUtil


class SplashActivity : AppCompatActivity() {
    private val fragments :ArrayList<Fragment> = ArrayList()
    var majorInfo: Int = -1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val window: Window = window

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext,
            R.color.colorPrimaryDark
        )

        SharedPreferenceUtil.init(this)

        SharedPreferenceUtil.init(this)

        majorInfo = SharedPreferenceUtil.getInt(MAJOR_KEY)
        val handler = Handler()
        if (majorInfo == -1)
            handler.postDelayed(SplashHandler(), 1000)
        else
            handler.postDelayed(SplashHandler(), 300)

    }
    inner class SplashHandler : Runnable {
        override fun run() {
            if (majorInfo == -1)
                startActivity(Intent(applicationContext, SelectMajorActivity::class.java))
            else
                startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
            finish()
        }
    }


    override fun onBackPressed() {

    }
}
