package com.yourssu.notissu.feature.selectNotiList

import android.view.View
import com.yourssu.notissu.model.Notice

interface SelectNotiListContract {
    interface View {
    }

    interface Presenter {
        var view: View

        fun loadItem(majorNumber: Int, page: Int, keyword: String?, complete: (ArrayList<Notice>) -> Unit)
    }
}