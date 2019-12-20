package com.yourssu.notissu.feature.openSource

import com.yourssu.notissu.data.OpenSourceData
import com.yourssu.notissu.model.OpenSource

interface OpenSourceContract {
    interface View {
        fun update(list: ArrayList<OpenSource>)
    }

    interface Presenter {
        var view: View
        var data: OpenSourceData

        fun loadItem()
    }
}