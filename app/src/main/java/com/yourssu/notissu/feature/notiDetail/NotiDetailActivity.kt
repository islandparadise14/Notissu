package com.yourssu.notissu.feature.notiDetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.data.DATE_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.TITLE_INTENT_KEY
import com.yourssu.notissu.data.URL_INTENT_KEY
import com.yourssu.notissu.model.NoticeDetail
import com.yourssu.notissu.utils.AlertDialogUtil
import com.yourssu.notissu.utils.FileUtil
import kotlinx.android.synthetic.main.activity_noti_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NotiDetailActivity : AppCompatActivity(), NotiDetailContract.View {
    lateinit var notiDetail: NoticeDetail
    lateinit var presenter: NotiDetailPresenter
    var url: String? = null
    lateinit var title: String
    lateinit var date: String
    private var majorNumber: Int = -1
    private var isParsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti_detail)

        presenter = NotiDetailPresenter()
        presenter.view = this@NotiDetailActivity
        presenter.checkNetwork(application)

        title = intent.getStringExtra(TITLE_INTENT_KEY) ?: ""
        date = intent.getStringExtra(DATE_INTENT_KEY) ?: ""
        url = intent.getStringExtra(URL_INTENT_KEY)
        majorNumber = intent.getIntExtra(MAJOR_INTENT_KEY, -1)

        initView()
    }

    fun initView() {
        notiDetailTobBar.setTitle("상세보기")
        detailTitle.text = title
        detailDate.text = date
        notiDetailTobBar.setBackButtonClicked {
            finish()
        }
        notiDetailTobBar.setWebButtonClicked {
            AlertDialogUtil.createDialogWithCancelButton("브라우저로 이동", resources.getString(R.string.noti_browser), this, "취소", "확인",
                DialogInterface.OnClickListener { dialog, _ ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    dialog.cancel()
                })
        }

        CoroutineScope(Dispatchers.Main).launch {
            // Show progress from UI thread
            if (detailAnimationView != null) {
                detailAnimationView.playAnimation()
                detailAnimationView.visibility = View.VISIBLE
            }
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                // background thread
                url?.let {
                    presenter.loadItem(majorNumber, it, complete)

                }
            }
            // UI data update from UI thread
            // Hide Progress from UI thread
            updateUI()
            if (detailAnimationView != null) {
                detailAnimationView.cancelAnimation()
                detailAnimationView.visibility = View.GONE
            }
        }
    }

    private val complete = { noticeDetail: NoticeDetail ->
        notiDetail = noticeDetail
        isParsed = true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun updateUI() {
        if(isParsed) {
            notiMainText.settings.javaScriptEnabled = true
            notiMainText.isHorizontalScrollBarEnabled = false
            notiMainText.isVerticalScrollBarEnabled = false
            notiMainText.setBackgroundColor(0)
            notiMainText.loadDataWithBaseURL("a", notiDetail.htmlText, "text/html", "UTF-8", null)

            if (notiDetail.fileList.size > 0) {
                fileList.visibility = View.VISIBLE
                fileRecycler.adapter = NotiDetailAdapter(notiDetail.fileList, clicked)
                fileRecycler.layoutManager =
                    LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            } else {
                fileList.visibility = View.GONE
            }
        }
    }

    private val clicked = { url: String?, name: String? ->
        AlertDialogUtil.createDialogWithCancelButton("파일 다운로드", resources.getString(R.string.download_info), this, "아니요", "예",
            DialogInterface.OnClickListener { dialog, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                    } else {
                        FileUtil.downloadFile(
                            applicationContext,
                            (url ?: ""),
                            (name ?: "").replace(" ", "")
                        )
                    }
                }
                dialog.cancel()
            })
    }
}

