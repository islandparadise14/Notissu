package com.yourssu.notissu.feature.notiDetail

import android.content.Context
import com.yourssu.notissu.model.NoticeDetail

interface NotiDetailContract {
    interface View {
    }

    interface Presenter {
        var view: View

        fun loadItem(majorNumber:Int, url: String, complete:(NoticeDetail) ->Unit)

        fun checkNetwork(context: Context?)
    }
}