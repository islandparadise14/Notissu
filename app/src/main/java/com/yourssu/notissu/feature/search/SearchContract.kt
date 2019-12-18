package com.yourssu.notissu.feature.search

import com.yourssu.notissu.data.MajorData
import android.view.View

interface SearchContract {
    interface View {
        fun update(mView: android.view.View, item: List<String>)
    }

    interface Presenter {
        var view: View
        var data: MajorData

        fun loadItem(mView: android.view.View)
    }
}