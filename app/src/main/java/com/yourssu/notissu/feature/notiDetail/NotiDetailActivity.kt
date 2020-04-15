package com.yourssu.notissu.feature.notiDetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yourssu.notissu.R
import com.yourssu.notissu.data.DATE_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.TITLE_INTENT_KEY
import com.yourssu.notissu.data.URL_INTENT_KEY
import com.yourssu.notissu.databinding.ActivityNotiDetailBinding
import com.yourssu.notissu.koin.repository.DatabaseRepository
import com.yourssu.notissu.model.NoticeDetail
import com.yourssu.notissu.room.entity.Notice
import com.yourssu.notissu.utils.AlertDialogUtil
import com.yourssu.notissu.utils.FileUtil
import kotlinx.android.synthetic.main.activity_noti_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class NotiDetailActivity : AppCompatActivity(), NotiDetailContract.View {
    lateinit var notiDetail: NoticeDetail
    lateinit var presenter: NotiDetailPresenter
    var url: String? = null
    lateinit var title: String
    lateinit var date: String
    private var majorNumber: Int = -1
    private var isParsed = false
    private lateinit var mBinding: ActivityNotiDetailBinding
    private val database: DatabaseRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        setPresenter()
        getIntentInfo()
        initView()
    }

    fun setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_noti_detail)
        mBinding.activity = this
        mBinding.isBookmarked = false
        url?.let { database
            .getBookmarkByUrl(it).observe(this, androidx.lifecycle.Observer { response ->
            mBinding.isBookmarked = response.isNotEmpty()
        }) }
    }

    fun setPresenter() {
        presenter = NotiDetailPresenter()
        presenter.view = this@NotiDetailActivity
        presenter.checkNetwork(application)
    }

    fun getIntentInfo() {
        title = intent.getStringExtra(TITLE_INTENT_KEY) ?: ""
        date = intent.getStringExtra(DATE_INTENT_KEY) ?: ""
        url = intent.getStringExtra(URL_INTENT_KEY)
        majorNumber = intent.getIntExtra(MAJOR_INTENT_KEY, -1)
    }

    fun initView() {
        mBinding.notiDetailTobBar.setTitle("상세보기")
        mBinding.detailTitle.text = title
        mBinding.detailDate.text = date
        mBinding.notiDetailTobBar.setBackButtonClicked {
            finish()
        }
        mBinding.notiDetailTobBar.bookmarkOffButtonClicked {
            url?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                        database
                            .insertAllBookmarks(Notice(title, date, it, majorNumber))
                    }
                }
            }
        }
        mBinding.notiDetailTobBar.bookmarkOnButtonClicked {
            url?.let { CoroutineScope(Dispatchers.Main).launch {
                withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                    database.deleteBookmark(Notice(title, date, it, majorNumber))
                }
            } }
        }
        mBinding.notiDetailTobBar.setWebButtonClicked {
            val clipboardManager: ClipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("url", url)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "클립보드로 복사되었습니다", Toast.LENGTH_LONG).show()
        }

        CoroutineScope(Dispatchers.Main).launch {
            // Show progress from UI thread
            if (mBinding.detailAnimationView != null) {
                mBinding.detailAnimationView.playAnimation()
                mBinding.detailAnimationView.visibility = View.VISIBLE
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
            if (mBinding.detailAnimationView != null) {
                mBinding.detailAnimationView.cancelAnimation()
                mBinding.detailAnimationView.visibility = View.GONE
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
            mBinding.notiMainText.settings.javaScriptEnabled = true
            mBinding.notiMainText.isHorizontalScrollBarEnabled = false
            mBinding.notiMainText.isVerticalScrollBarEnabled = false
            mBinding.notiMainText.setBackgroundColor(0)
            mBinding.notiMainText.loadDataWithBaseURL("a", notiDetail.htmlText, "text/html", "UTF-8", null)

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
                        val rxPermissions = RxPermissions(this)
                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe { isGranted ->
                                if(isGranted) {
                                    FileUtil.downloadFile(
                                        applicationContext,
                                        (url ?: ""),
                                        (name ?: "").replace(" ", "")
                                    )
                                } else {
                                    Toast.makeText(applicationContext, resources.getString(R.string.file_permission_fail), Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        FileUtil.downloadFile(
                            applicationContext,
                            (url ?: ""),
                            (name ?: "").replace(" ", "")
                        )
                    }
                } else {
                    FileUtil.downloadFile(
                        applicationContext,
                        (url ?: ""),
                        (name ?: "").replace(" ", "")
                    )
                }
                dialog.cancel()
            })
    }
}

