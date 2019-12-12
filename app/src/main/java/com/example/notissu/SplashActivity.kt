package com.example.notissu

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.notissu.utils.SharedPreferenceUtil


class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val window: Window = window

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)

        SharedPreferenceUtil.init(this)

        val handler = Handler()
        handler.postDelayed(SplashHandler(), 1000)

    }
    inner class SplashHandler : Runnable {
        override fun run() {
            val majorInfo = SharedPreferenceUtil.getInt("major")
            if (majorInfo == -1)
                startActivity(Intent(applicationContext, SelectMajorActivity::class.java))
            else
                startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }


    override fun onBackPressed() {

    }
}
