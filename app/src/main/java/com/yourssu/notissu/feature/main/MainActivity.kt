package com.yourssu.notissu.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yourssu.notissu.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainTopBar.setTitle("전자정보공학부")
        mainTopBar.setBackButtonClicked {
            Toast.makeText(applicationContext, "back", Toast.LENGTH_SHORT).show()
        }
        mainTopBar.setPlusButtonClicked {
            Toast.makeText(applicationContext, "plus", Toast.LENGTH_SHORT).show()
        }

        //supportFragmentManager.beginTransaction().replace(R.id.main_container, MyNotiFragment.newInstance()).commit()


    }
}
