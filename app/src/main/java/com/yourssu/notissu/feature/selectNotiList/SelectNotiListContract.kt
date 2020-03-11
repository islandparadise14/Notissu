package com.yourssu.notissu.feature.selectNotiList

import android.content.Context
import com.yourssu.notissu.model.NoticeItem

interface SelectNotiListContract {
    interface View {
    }

    interface Presenter {
        var view: View

        fun loadItem(majorNumber: Int, page: Int, keyword: String?, complete: (ArrayList<NoticeItem>) -> Unit)

        fun checkNetwork(context: Context?): Boolean
    }
}