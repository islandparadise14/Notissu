package com.yourssu.notissu.feature.selectNotiList


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yourssu.notissu.R
import com.yourssu.notissu.data.DATE_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.TITLE_INTENT_KEY
import com.yourssu.notissu.data.URL_INTENT_KEY
import com.yourssu.notissu.feature.notiDetail.NotiDetailActivity
import com.yourssu.notissu.model.Notice
import com.yourssu.notissu.utils.NetworkCheck
import kotlinx.android.synthetic.main.fragment_select_noti_list.*
import kotlinx.android.synthetic.main.fragment_select_noti_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class SelectNotiListFragment(private val majorNumber: Int, private val keyword: String?) : Fragment(), SelectNotiListContract.View {
    companion object {
        fun getInstance(majorNumber: Int, keyword: String?) = SelectNotiListFragment(majorNumber, keyword)
    }

    private lateinit var presenter: SelectNotiListPresenter
    private var notiList: ArrayList<Notice> = ArrayList()
    private var result: ArrayList<Notice> = ArrayList()
    private lateinit var mAdapter: NoticeAdapter
    private var nextPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_select_noti_list, container, false)

        presenter = SelectNotiListPresenter()
        presenter.view = this@SelectNotiListFragment

        presenter.checkNetwork(context)

        addScrollListener(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNotice()
        mAdapter = NoticeAdapter(mArticleDiffCallback, notiList, clicked = clicked)
        val layoutManager = LinearLayoutManager(activity)
        notiListRecycler.adapter = mAdapter
        notiListRecycler.layoutManager = layoutManager
    }

    private fun addScrollListener(view: View) {
        view.refresh.setOnRefreshListener {
            nextPage = 1
            result.clear()
            notiList.clear()
            getNotice()
        }

        view.notiListRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && notiList.size > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == notiList.size - 1) {
                        getNotice()
                    }
                }
            }
        })
    }

    fun getNotice() {
        CoroutineScope(Dispatchers.Main).launch {
            // Show progress from UI thread
            if (animationView != null) {
                animationView.playAnimation()
                animationView.visibility = View.VISIBLE
            }

            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                // background thread
                presenter.loadItem(majorNumber= majorNumber, page = nextPage ,keyword = keyword, complete = complete)
            }
            // UI data update from UI thread
            // Hide Progress from UI thread
            nextPage += 1
            update()
            refresh.isRefreshing = false
            if (animationView != null) {
                animationView.cancelAnimation()
                animationView.visibility = View.GONE
            }
        }
    }

    private fun update() {
        notiList.addAll(result)
        mAdapter.submitList(notiList)
        mAdapter.notifyDataSetChanged()
    }

    private val mArticleDiffCallback = object : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return (oldItem.title == newItem.title) && (oldItem.url == newItem.url)
        }
    }

    private val complete = { noticeList: ArrayList<Notice> ->
        result = noticeList
    }

    private val clicked = { title: String?, date: String?, url: String? ->
        val intent = Intent(activity, NotiDetailActivity::class.java)
        intent.putExtra(TITLE_INTENT_KEY, title)
        intent.putExtra(DATE_INTENT_KEY, date)
        intent.putExtra(URL_INTENT_KEY, url)
        intent.putExtra(MAJOR_INTENT_KEY, majorNumber)
        startActivity(intent)
    }
}
