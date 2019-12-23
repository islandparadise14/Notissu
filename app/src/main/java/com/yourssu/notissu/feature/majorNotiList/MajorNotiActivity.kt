package com.yourssu.notissu.feature.majorNotiList

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yourssu.notissu.R
import com.yourssu.notissu.data.KEYWORD_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.selectNotiList.SelectNotiListFragment
import com.yourssu.notissu.utils.AlertDialogUtil
import com.yourssu.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_major_noti.*

class MajorNotiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major_noti)

        initSetting()
    }

    fun initSetting() {
        val majorNumber = intent.getIntExtra(MAJOR_INTENT_KEY, -2)
        val keyword = intent.getStringExtra(KEYWORD_INTENT_KEY)
        notissuTopbar.setBackButtonClicked { finish() }
        notissuTopbar.setPlusButtonClicked {
            AlertDialogUtil.createDialogWithCancelButton("메인 전공 등록", resources.getString(R.string.major_change), this, "취소", "확인",
            DialogInterface.OnClickListener { dialog, _ ->
                SharedPreferenceUtil.setIntValue(MAJOR_KEY, majorNumber)
                dialog.cancel()
                setResult(1)
                finish()
            }) }

        when {
            majorNumber > -1 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.majorNotiMain,
                    SelectNotiListFragment.getInstance(majorNumber, keyword)).commit()
                notissuTopbar.setTitle(MajorData.getInstance().getMajorByIndex(majorNumber).name)
            }
            majorNumber == -1 -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.majorNotiMain,
                    SelectNotiListFragment.getInstance(majorNumber, keyword)).commit()
                notissuTopbar.setTitle("SSU:Catch")
            }
            else -> Toast.makeText(applicationContext, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}
