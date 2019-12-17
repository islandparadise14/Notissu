package com.yourssu.notissu.feature.selectNotiList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yourssu.notissu.R
import com.yourssu.notissu.model.Notice

/**
 * A simple [Fragment] subclass.
 */
class SelectNotiListFragment : Fragment() {
    lateinit var result: ArrayList<Notice>

    companion object {
        @JvmStatic
        fun getInstance() = SelectNotiListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val complete = { noticeList: ArrayList<Notice> ->
//            result = noticeList
//        }
//
//        CoroutineScope(Dispatchers.Main).launch {
//            // Show progress from UI thread
//
//            CoroutineScope(Dispatchers.Default).async {
//                // background thread
//                NoticeBusiness.parseListBiz(0, null, complete)
//            }.await()
//            // UI data update from UI thread
//            // Hide Progress from UI thread
//
//            // --- updateUi(result)
//        }
        return inflater.inflate(R.layout.fragment_select_noti_list, container, false)
    }


}
