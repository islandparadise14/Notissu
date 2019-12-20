package com.yourssu.notissu.feature.myInfo


import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MAJOR_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.openSource.OpenSourceActivity
import com.yourssu.notissu.utils.AlertDialogUtil
import com.yourssu.notissu.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_my_info.view.*


/**
 * A simple [Fragment] subclass.
 */
class MyInfoFragment : Fragment() {

    companion object {
        @JvmStatic
        fun getInstance() = MyInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_info, container, false)

        initView(view)
        setListener(view)

        return view
    }

    private fun initView(view: View) {
        view.majorInfo.text = MajorData.getInstance().getMajorByIndex(
            SharedPreferenceUtil.getInt(
                MAJOR_KEY
            )).name
    }

    private fun setListener(view: View) {
        view.openSourceText.setOnClickListener {
            startActivity(Intent(activity, OpenSourceActivity::class.java))
        }
        view.developerText.setOnClickListener {
            context?.let { AlertDialogUtil.createDialogWithCancelButton("개발자 정보", resources.getString(R.string.developer_info), it, "취소", "확인",
                DialogInterface.OnClickListener { dialog, _ ->
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    // email setting 배열로 해놔서 복수 발송 가능
                    val address = arrayOf("nasamk3@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[Notissu] 문의합니다")
                    startActivity(email)
                    dialog.cancel()
                }) }
        }
        view.githubText.setOnClickListener {
            context?.let { AlertDialogUtil.createDialogWithCancelButton("개발자 GitHub", resources.getString(R.string.github_info), it, "취소", "확인",
                DialogInterface.OnClickListener { dialog, _ ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/islandparadise14"))
                    startActivity(intent)
                    dialog.cancel()
                }) }
        }
        view.recommendText.setOnClickListener {
            context?.let { AlertDialogUtil.createDialogWithCancelButton("그라운드 설치", resources.getString(R.string.recommend_info), it, "취소", "확인",
                DialogInterface.OnClickListener { dialog, _ ->
                    val appPackageName = "com.yourssu.ground"
                    try {
                        startActivity(Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")))
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    }
                    dialog.cancel()
                }) }
        }
    }
}
